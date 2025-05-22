package com.jingdiansuifeng.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.jingdiansuifeng.subject.common.enums.IsDeletedFlagEnum;
import com.jingdiansuifeng.subject.domain.convert.SubjectLabelConverter;
import com.jingdiansuifeng.subject.domain.entity.*;
import com.jingdiansuifeng.subject.domain.service.SubjectCategoryDomainService;
import com.jingdiansuifeng.subject.domain.convert.SubjectCategoryConverter;
import com.jingdiansuifeng.subject.domain.service.SubjectCategoryService;
import com.jingdiansuifeng.subject.domain.service.SubjectLabelService;
import com.jingdiansuifeng.subject.domain.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;


    public void add(SubjectCategoryBO subjectCategoryBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.add.subjectCategoryBO:{}"
                    , JSON.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE
                .convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectCategoryService.insert(subjectCategory);
    }

    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
//        SubjectCategory subjectCategory = new SubjectCategory();
//        subjectCategory.setParentId(0L);
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE
                .convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> boList = SubjectCategoryConverter.INSTANCE
                .convertCategoryListToBoList(subjectCategoryList);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryCategory.boList:{}"
                    , JSON.toJSONString(boList));
        }
        boList.forEach(bo ->{
            Integer subjectCount = subjectCategoryService.querySubjectCount(bo.getId());
            bo.setCount(subjectCount);
        });
        return boList;
    }

    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        int count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }

    public Boolean delete(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        int count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }

    @Override
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory.setId(subjectCategoryBO.getId());
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryCategoryAndLabel.subjectCategoryList:{}"
                    , JSON.toJSONString(subjectCategoryList));
        }
        List<SubjectCategoryBO> subjectCategoryBOList = SubjectCategoryConverter
                .INSTANCE.convertCategoryListToBoList(subjectCategoryList);
        subjectCategoryBOList.forEach(categoryBO -> {
            SubjectMapping subjectMapping = new SubjectMapping();
            subjectMapping.setCategoryId(categoryBO.getId());
            List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);

            if (CollectionUtils.isEmpty(mappingList)) {
                return;
            }
            List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
            List<SubjectLabel> subjectLabelList = subjectLabelService.batchQueryById(labelIdList);
            List<SubjectLabelBO> labelBOList = new LinkedList<>();
            subjectLabelList.forEach(label -> {
                SubjectLabelBO labelBO = new SubjectLabelBO();
                labelBO.setId(label.getId());
                labelBO.setLabelName(label.getLabelName());
                labelBO.setCategoryId(label.getCategoryId());
                labelBO.setSortNum(label.getSortNum());
                labelBOList.add(labelBO);
            });
            categoryBO.setSubjectLabelBOList(labelBOList);
        });
        return subjectCategoryBOList;
    }
}
