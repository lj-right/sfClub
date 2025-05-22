package com.jingdiansuifeng.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.jingdiansuifeng.subject.common.enums.CategoryTypeEnum;
import com.jingdiansuifeng.subject.common.enums.IsDeletedFlagEnum;
import com.jingdiansuifeng.subject.domain.convert.SubjectLabelConverter;
import com.jingdiansuifeng.subject.domain.entity.SubjectCategory;
import com.jingdiansuifeng.subject.domain.entity.SubjectLabelBO;
import com.jingdiansuifeng.subject.domain.entity.SubjectMapping;
import com.jingdiansuifeng.subject.domain.service.SubjectCategoryService;
import com.jingdiansuifeng.subject.domain.service.SubjectMappingService;
import com.jingdiansuifeng.subject.domain.entity.SubjectLabel;
import com.jingdiansuifeng.subject.domain.service.SubjectLabelDomainService;
import com.jingdiansuifeng.subject.domain.service.SubjectLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectCategoryService subjectCategoryService;


    public Boolean add(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.add.subjectLabelBO:{}"
                    , JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE
                .convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        int count = subjectLabelService.insert(subjectLabel);
        return count > 0;
    }

    public Boolean update(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.update.subjectLabelBO:{}"
                    , JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE
                .convertBoToLabel(subjectLabelBO);
        int count = subjectLabelService.update(subjectLabel);
        return count > 0;
    }

    public Boolean delete(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.update.subjectLabelBO:{}"
                    , JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE
                .convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        int count = subjectLabelService.update(subjectLabel);
        return count > 0;
    }

    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        //判断分类级别 1,2
        SubjectCategory subjectCategory = subjectCategoryService.queryById(subjectLabelBO.getCategoryId());
        if (CategoryTypeEnum.PRIMARY.getCode() == subjectCategory.getCategoryType()){
            SubjectLabel subjectLabel = new SubjectLabel();
            subjectLabel.setCategoryId(subjectLabelBO.getCategoryId());
            subjectLabel.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            List<SubjectLabel> labelList = subjectLabelService.queryByCondition(subjectLabel);
            return SubjectLabelConverter.INSTANCE.convertLabelListToBoList(labelList);
        }

        Long categoryId = subjectLabelBO.getCategoryId();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(categoryId);
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectMapping> subjectMappingList = subjectMappingService.queryLabelId(subjectMapping);
        if (CollectionUtils.isEmpty(subjectMappingList)) {
            return Collections.emptyList();
        }
        List<Long> LabelIdList = subjectMappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabelList = subjectLabelService.batchQueryById(LabelIdList);
        LinkedList<SubjectLabelBO> boList = new LinkedList<>();
        subjectLabelList.forEach(label -> {
            SubjectLabelBO bo = new SubjectLabelBO();
            bo.setId(label.getId());
            bo.setCategoryId(categoryId);
            bo.setLabelName(label.getLabelName());
            bo.setSortNum(label.getSortNum());
            boList.add(bo);
        });
        return boList;
    }
}
