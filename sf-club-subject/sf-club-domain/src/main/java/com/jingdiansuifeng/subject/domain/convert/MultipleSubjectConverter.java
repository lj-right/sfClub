package com.jingdiansuifeng.subject.domain.convert;

import com.jingdiansuifeng.subject.domain.entity.SubjectAnswerBO;
import com.jingdiansuifeng.subject.domain.entity.SubjectMultiple;
import com.jingdiansuifeng.subject.domain.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface MultipleSubjectConverter {
    MultipleSubjectConverter INSTANCE = Mappers.getMapper(MultipleSubjectConverter.class);

    SubjectMultiple convertBoToMultipleEntity(SubjectAnswerBO subjectAnswerBO);

    List<SubjectAnswerBO> convertMultipleListToAnswerBo(List<SubjectMultiple> subjectMultipleList);
}
