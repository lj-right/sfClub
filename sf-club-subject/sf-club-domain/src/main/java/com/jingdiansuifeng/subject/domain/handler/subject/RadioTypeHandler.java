package com.jingdiansuifeng.subject.domain.handler.subject;

import com.google.common.base.Preconditions;
import com.jingdiansuifeng.subject.common.enums.IsDeletedFlagEnum;
import com.jingdiansuifeng.subject.common.enums.SubjectInfoTypeEnum;
import com.jingdiansuifeng.subject.domain.convert.JudgeSubjectConverter;
import com.jingdiansuifeng.subject.domain.convert.RadioSubjectConverter;
import com.jingdiansuifeng.subject.domain.entity.*;
import com.jingdiansuifeng.subject.domain.service.SubjectRadioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 单选的题目的策略类
 */
@Component
public class RadioTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;

    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        Preconditions.checkNotNull(subjectInfoBO.getId(), "题目id不能为空");
        Preconditions.checkArgument(!StringUtils.isBlank(subjectInfoBO.getSubjectName()), "题目名称不能为空");
        //单选题目的插入
        List<SubjectRadio> subjectRadioList = new LinkedList<>();
        subjectInfoBO.getOptionList().forEach(option ->{
            SubjectRadio subjectRadio = RadioSubjectConverter.INSTANCE.convertBoToRadioEntity(option);
            subjectRadio.setSubjectId(subjectInfoBO.getId());
            subjectRadio.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            subjectRadioList.add(subjectRadio);
        });
        subjectRadioService.batchInsert(subjectRadioList);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        SubjectRadio subjectRadio = new SubjectRadio();
        subjectRadio.setSubjectId(Long.valueOf(subjectId));
        List<SubjectRadio> result = subjectRadioService.queryByCondition(subjectRadio);
        List<SubjectAnswerBO> subjectAnswerBOList = RadioSubjectConverter.INSTANCE.convertRadioListToAnswerBo(result);
        SubjectOptionBO optionBO = new SubjectOptionBO();
        optionBO.setOptionList(subjectAnswerBOList);
        return optionBO;
    }
}
