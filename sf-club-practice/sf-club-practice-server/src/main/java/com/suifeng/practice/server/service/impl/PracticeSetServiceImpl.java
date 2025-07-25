package com.suifeng.practice.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.suifeng.practice.api.common.PageInfo;
import com.suifeng.practice.api.common.PageResult;
import com.suifeng.practice.api.enums.CompleteStatusEnum;
import com.suifeng.practice.api.enums.IsDeletedFlagEnum;
import com.suifeng.practice.api.enums.SubjectInfoTypeEnum;
import com.suifeng.practice.api.req.GetPracticeSubjectsReq;
import com.suifeng.practice.api.req.GetUnCompletePracticeReq;
import com.suifeng.practice.api.vo.*;
import com.suifeng.practice.server.dao.*;
import com.suifeng.practice.server.entity.dto.CategoryDTO;
import com.suifeng.practice.server.entity.dto.PracticeSetDTO;
import com.suifeng.practice.server.entity.dto.PracticeSubjectDTO;
import com.suifeng.practice.server.entity.po.*;
import com.suifeng.practice.server.service.PracticeSetService;
import com.suifeng.practice.server.util.DateUtils;
import com.suifeng.practice.server.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PracticeSetServiceImpl implements PracticeSetService {

    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    @Resource
    private SubjectMappingDao subjectMappingDao;

    @Resource
    private SubjectLabelDao subjectLabelDao;

    @Resource
    private PracticeSetDetailDao practiceSetDetailDao;

    @Resource
    private PracticeSetDao practiceSetDao;

    @Resource
    private SubjectDao subjectDao;

    @Resource
    private PracticeDetailDao practiceDetailDao;

    @Resource
    private PracticeDao practiceDao;

    @Resource
    private SubjectRadioDao subjectRadioDao;

    @Resource
    private SubjectMultipleDao subjectMultipleDao;

    @Override
    public List<SpecialPractiveVO> getSpecialPracticeContent() {
        List<SpecialPractiveVO> specialPractiveVOList = new LinkedList<>();
        List<Integer> subjectTypeList = new LinkedList<>();
        subjectTypeList.add(SubjectInfoTypeEnum.RADIO.getCode());
        subjectTypeList.add(SubjectInfoTypeEnum.MULTIPLE.getCode());
        subjectTypeList.add(SubjectInfoTypeEnum.JUDGE.getCode());

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setSubjectTypeList(subjectTypeList);

        List<PrimaryCategoryPO> poList = subjectCategoryDao.getPrimaryCategory(categoryDTO);
        //上面有效的只有parentId
        if (CollectionUtils.isEmpty(poList)) {
            return specialPractiveVOList;
        }
        poList.forEach(primaryCategoryPO -> {
            SpecialPractiveVO specialPractiveVO = new SpecialPractiveVO();

            //拿到大类集合
            List<PrimaryCategoryPO> categoryPOList = subjectCategoryDao.selectByParentId(primaryCategoryPO.getParentId());

            categoryPOList.forEach(categoryPO -> {
                specialPractiveVO.setPrimaryCategoryId(categoryPO.getId());
                specialPractiveVO.setPrimaryCategoryName(categoryPO.getCategoryName());
                CategoryDTO categoryDTOTemp = new CategoryDTO();
                categoryDTOTemp.setCategoryType(2);
                categoryDTOTemp.setParentId(primaryCategoryPO.getParentId()+1);
                //拿到子分类
                List<CategoryPO> smallPoList = subjectCategoryDao.selectList(categoryDTOTemp);
                if (CollectionUtils.isEmpty(smallPoList)) {
                    return;
                }

                List<SpecialPractiveCategoryVO> categoryVOList = new LinkedList<>();
                smallPoList.forEach(smallPo -> {
                    List<SpecialPracticeLabelVO> labelVOList = getLabelVOList(smallPo.getId(), subjectTypeList);
                    //同理上面也只能拿到labelId
                    if (CollectionUtils.isEmpty(labelVOList)) {
                        return;
                    }
                    SpecialPractiveCategoryVO specialPractiveCategoryVO = new SpecialPractiveCategoryVO();
                    specialPractiveCategoryVO.setCategoryId(smallPo.getId());
                    specialPractiveCategoryVO.setCategoryName(smallPo.getCategoryName());

                    List<SpecialPracticeLabelVO> labelList = new LinkedList<>();
                    labelVOList.forEach(labelVO -> {
                        SpecialPracticeLabelVO specialPracticeLabelVO = new SpecialPracticeLabelVO();
                        specialPracticeLabelVO.setId(labelVO.getId());
                        specialPracticeLabelVO.setAssembleId(labelVO.getAssembleId());
                        specialPracticeLabelVO.setLabelName(labelVO.getLabelName());
                        labelList.add(specialPracticeLabelVO);
                    });
                    specialPractiveCategoryVO.setLabelList(labelList);
                    categoryVOList.add(specialPractiveCategoryVO);
                });
                specialPractiveVO.setCategoryList(categoryVOList);

            });

            specialPractiveVOList.add(specialPractiveVO);
        });
        return specialPractiveVOList;
    }

    private List<SpecialPracticeLabelVO> getLabelVOList(Long categoryId, List<Integer> subjectTypeList) {

        List<LabelCountPO> countPOList = subjectMappingDao.getLabelSubjectCount(categoryId, subjectTypeList);
        if (CollectionUtils.isEmpty(countPOList)) {
            return Collections.emptyList();
        }
        List<SpecialPracticeLabelVO> voList = new LinkedList<>();
        countPOList.forEach(countPo -> {
            SpecialPracticeLabelVO vo = new SpecialPracticeLabelVO();
            vo.setId(countPo.getLabelId());
            vo.setAssembleId(categoryId+"-"+countPo.getLabelId());
            SubjectLabelPO subjectLabelPO = subjectLabelDao.queryById(countPo.getLabelId());
            vo.setLabelName(subjectLabelPO.getLabelName());
            voList.add(vo);

        });
        return voList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PracticeSetVO addPractice(PracticeSubjectDTO dto) {
        PracticeSetVO setVO = new PracticeSetVO();
        List<PracticeSubjectDetailVO> practiceList = getPracticeList(dto);
        if (CollectionUtils.isEmpty(practiceList)) {
            return setVO;
        }
        PracticeSetPO practiceSetPO = new PracticeSetPO();
        practiceSetPO.setSetType(1);

        List<String> assembleIds = dto.getAssembleIds();
        Set<Long> categoryIdSet = new HashSet<>();
        assembleIds.forEach(assembleId -> {
            Long categoryId = Long.valueOf(assembleId.split("-")[0]);
            categoryIdSet.add(categoryId);
        });
        StringBuffer setName = new StringBuffer();
        int i = 1;
        for (Long categoryId : categoryIdSet) {
            if (i > 2) {
                break;
            }
            CategoryPO categoryPO = subjectCategoryDao.selectById(categoryId);
            setName.append(categoryPO.getCategoryName());
            setName.append("、");
            i = i + 1;
        }
        setName.deleteCharAt(setName.length() - 1);
        if (i == 2) {
            setName.append("专项练习");
        } else {
            setName.append("等专项练习");
        }
        practiceSetPO.setSetName(setName.toString());

        String labelId = assembleIds.get(0).split("-")[1];
        SubjectLabelPO labelPO = subjectLabelDao.queryById(Long.valueOf(labelId));
        practiceSetPO.setPrimaryCategoryId(labelPO.getCategoryId());
        practiceSetPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        practiceSetPO.setCreatedBy(LoginUtil.getLoginId());
        practiceSetPO.setCreatedTime(new Date());
        practiceSetDao.add(practiceSetPO);
        Long practiceSetId = practiceSetPO.getId();

        //思考，这里哪里不符合规范，配合听视频的延伸
        practiceList.forEach(e -> {
            PracticeSetDetailPO detailPO = new PracticeSetDetailPO();
            detailPO.setSetId(practiceSetId);
            detailPO.setSubjectId(e.getSubjectId());
            detailPO.setSubjectType(e.getSubjectType());
            detailPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            detailPO.setCreatedBy(LoginUtil.getLoginId());
            detailPO.setCreatedTime(new Date());
            practiceSetDetailDao.add(detailPO);
        });
        setVO.setSetId(practiceSetId);
        return setVO;
    }

    /**
     * 获取套卷题目信息
     */
    private List<PracticeSubjectDetailVO> getPracticeList(PracticeSubjectDTO dto) {
        List<PracticeSubjectDetailVO> practiceSubjectListVOS = new LinkedList<>();
        //避免重复
        List<Long> excludeSubjectIds = new LinkedList<>();

        //设置题目数量，之后优化到nacos动态配置
        Integer radioSubjectCount = 10;
        Integer multipleSubjectCount = 6;
        Integer judgeSubjectCount = 4;
        Integer totalSubjectCount = 20;
        //查询单选
        dto.setSubjectCount(radioSubjectCount);
        dto.setSubjectType(SubjectInfoTypeEnum.RADIO.getCode());
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        //查询多选
        dto.setSubjectCount(multipleSubjectCount);
        dto.setSubjectType(SubjectInfoTypeEnum.MULTIPLE.getCode());
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        //查询判断
        dto.setSubjectCount(judgeSubjectCount);
        dto.setSubjectType(SubjectInfoTypeEnum.JUDGE.getCode());
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        //补充题目
        if (practiceSubjectListVOS.size() == totalSubjectCount) {
            return practiceSubjectListVOS;
        }
        Integer remainCount = totalSubjectCount - practiceSubjectListVOS.size();
        dto.setSubjectCount(remainCount);
        dto.setSubjectType(1);
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        return practiceSubjectListVOS;
    }

    private List<PracticeSubjectDetailVO> assembleList(PracticeSubjectDTO dto, List<PracticeSubjectDetailVO> list, List<Long> excludeSubjectIds) {
        dto.setExcludeSubjectIds(excludeSubjectIds);
        List<SubjectPO> subjectPOList = subjectDao.getPracticeSubject(dto);
        if (CollectionUtils.isEmpty(subjectPOList)) {
            return list;
        }
        subjectPOList.forEach(e -> {
            PracticeSubjectDetailVO vo = new PracticeSubjectDetailVO();
            vo.setSubjectId(e.getId());
            vo.setSubjectType(e.getSubjectType());
            excludeSubjectIds.add(e.getId());
            list.add(vo);
        });
        return list;
    }

    @Override
    public PracticeSubjectListVO getSubjects(GetPracticeSubjectsReq req) {
        Long setId = req.getSetId();
        PracticeSubjectListVO vo = new PracticeSubjectListVO();
        List<PracticeSubjectDetailVO> practiceSubjectListVOS = new LinkedList<>();
        List<PracticeSetDetailPO> practiceSetDetailPOS = practiceSetDetailDao.selectBySetId(setId);
        if (CollectionUtils.isEmpty(practiceSetDetailPOS)) {
            return vo;
        }
        String loginId = LoginUtil.getLoginId();
        Long practiceId = req.getPracticeId();
        practiceSetDetailPOS.forEach(e -> {
            PracticeSubjectDetailVO practiceSubjectListVO = new PracticeSubjectDetailVO();
            practiceSubjectListVO.setSubjectId(e.getSubjectId());
            practiceSubjectListVO.setSubjectType(e.getSubjectType());
            if (Objects.nonNull(practiceId)) {
                PracticeDetailPO practiceDetailPO = practiceDetailDao.selectDetail(practiceId, e.getSubjectId(), loginId);
                if (Objects.nonNull(practiceDetailPO) && StringUtils.isNotBlank(practiceDetailPO.getAnswerContent())) {
                    practiceSubjectListVO.setIsAnswer(1);
                } else {
                    practiceSubjectListVO.setIsAnswer(0);
                }
            }
            practiceSubjectListVOS.add(practiceSubjectListVO);
        });
        vo.setSubjectList(practiceSubjectListVOS);
        PracticeSetPO practiceSetPO = practiceSetDao.selectById(setId);
        vo.setTitle(practiceSetPO.getSetName());
        if (Objects.isNull(practiceId)) {
            Long newPracticeId = insertUnCompletePractice(setId);
            vo.setPracticeId(newPracticeId);
        } else {
            updateUnCompletePractice(practiceId);
            PracticePO practicePO = practiceDao.selectById(practiceId);
            vo.setTimeUse(practicePO.getTimeUse());
            vo.setPracticeId(practiceId);
        }
        return vo;
    }

    private Long insertUnCompletePractice(Long practiceSetId) {
        PracticePO practicePO = new PracticePO();
        practicePO.setSetId(practiceSetId);
        practicePO.setCompleteStatus(CompleteStatusEnum.NO_COMPLETE.getCode());
        practicePO.setTimeUse("00:00:00");
        practicePO.setSubmitTime(new Date());
        practicePO.setCorrectRate(new BigDecimal("0.00"));
        practicePO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        practicePO.setCreatedBy(LoginUtil.getLoginId());
        practicePO.setCreatedTime(new Date());
        practiceDao.insert(practicePO);
        return practicePO.getId();
    }

    private void updateUnCompletePractice(Long practiceId) {
        PracticePO practicePO = new PracticePO();
        practicePO.setId(practiceId);
        practicePO.setSubmitTime(new Date());
        practiceDao.update(practicePO);
    }

    @Override
    public PracticeSubjectVO getPracticeSubject(PracticeSubjectDTO dto) {
        PracticeSubjectVO practiceSubjectVO = new PracticeSubjectVO();
        SubjectPO subjectPO = subjectDao.selectById(dto.getSubjectId());
        practiceSubjectVO.setSubjectName(subjectPO.getSubjectName());
        practiceSubjectVO.setSubjectType(subjectPO.getSubjectType());
        if (dto.getSubjectType() == SubjectInfoTypeEnum.RADIO.getCode()) {
            List<PracticeSubjectOptionVO> optionList = new LinkedList<>();
            List<SubjectRadioPO> radioSubjectPOS = subjectRadioDao.selectBySubjectId(subjectPO.getId());
            radioSubjectPOS.forEach(e -> {
                PracticeSubjectOptionVO practiceSubjectOptionVO = new PracticeSubjectOptionVO();
                practiceSubjectOptionVO.setOptionContent(e.getOptionContent());
                practiceSubjectOptionVO.setOptionType(e.getOptionType());
                optionList.add(practiceSubjectOptionVO);
            });
            practiceSubjectVO.setOptionList(optionList);
        }
        if (dto.getSubjectType() == SubjectInfoTypeEnum.MULTIPLE.getCode()) {
            List<PracticeSubjectOptionVO> optionList = new LinkedList<>();
            List<SubjectMultiplePO> multipleSubjectPOS = subjectMultipleDao.selectBySubjectId(subjectPO.getId());
            multipleSubjectPOS.forEach(e -> {
                PracticeSubjectOptionVO practiceSubjectOptionVO = new PracticeSubjectOptionVO();
                practiceSubjectOptionVO.setOptionContent(e.getOptionContent());
                practiceSubjectOptionVO.setOptionType(e.getOptionType());
                optionList.add(practiceSubjectOptionVO);
            });
            practiceSubjectVO.setOptionList(optionList);
        }
        return practiceSubjectVO;
    }

    @Override
    public PageResult<PracticeSetVO> getPreSetContent(PracticeSetDTO dto) {
        PageResult<PracticeSetVO> pageResult = new PageResult<>();
        PageInfo pageInfo = dto.getPageInfo();
        pageResult.setPageNo(pageInfo.getPageNo());
        pageResult.setPageSize(pageInfo.getPageSize());
        int start = (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
        Integer count = practiceSetDao.getListCount(dto);
        if (count == 0) {
            return pageResult;
        }
        List<PracticeSetPO> setPOList = practiceSetDao.getSetList(dto, start, dto.getPageInfo().getPageSize());
        if (log.isInfoEnabled()) {
            log.info("获取的模拟考卷列表{}", JSON.toJSONString(setPOList));
        }
        List<PracticeSetVO> list = new LinkedList<>();
        setPOList.forEach(e -> {
            PracticeSetVO vo = new PracticeSetVO();
            vo.setSetId(e.getId());
            vo.setSetName(e.getSetName());
            vo.setSetHeat(e.getSetHeat());
            vo.setSetDesc(e.getSetDesc());
            list.add(vo);
        });
        pageResult.setRecords(list);
        pageResult.setTotal(count);
        return pageResult;
    }

    @Override
    public PageResult<UnCompletePracticeSetVO> getUnCompletePractice(GetUnCompletePracticeReq req) {
        PageResult<UnCompletePracticeSetVO> pageResult = new PageResult<>();
        PageInfo pageInfo = req.getPageInfo();
        pageResult.setPageNo(pageInfo.getPageNo());
        pageResult.setPageSize(pageInfo.getPageSize());
        int start = (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
        String loginId = LoginUtil.getLoginId();
        Integer count = practiceDao.getUnCompleteCount(loginId);
        if (count == 0) {
            return pageResult;
        }
        List<PracticePO> poList = practiceDao.getUnCompleteList(loginId, start, req.getPageInfo().getPageSize());
        if (log.isInfoEnabled()) {
            log.info("获取未完成的考卷列表{}", JSON.toJSONString(poList));
        }
        List<UnCompletePracticeSetVO> list = new LinkedList<>();
        poList.forEach(e -> {
            UnCompletePracticeSetVO vo = new UnCompletePracticeSetVO();
            vo.setSetId(e.getSetId());
            vo.setPracticeId(e.getId());
            vo.setPracticeTime(DateUtils.format(e.getSubmitTime(), "yyyy-MM-dd"));
            PracticeSetPO practiceSetPO = practiceSetDao.selectById(e.getSetId());
            vo.setTitle(practiceSetPO.getSetName());
            list.add(vo);
        });
        pageResult.setRecords(list);
        pageResult.setTotal(count);
        return pageResult;
    }

}
