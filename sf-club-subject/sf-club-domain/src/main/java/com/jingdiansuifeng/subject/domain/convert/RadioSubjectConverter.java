package com.jingdiansuifeng.subject.domain.convert;

import com.jingdiansuifeng.subject.domain.entity.SubjectAnswerBO;
import com.jingdiansuifeng.subject.domain.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface RadioSubjectConverter {
    RadioSubjectConverter INSTANCE = Mappers.getMapper(RadioSubjectConverter.class);

    SubjectRadio convertBoToRadioEntity(SubjectAnswerBO subjectAnswerBO);

    List<SubjectAnswerBO> convertRadioListToAnswerBo(List<SubjectRadio> subjectRadioList);
}
