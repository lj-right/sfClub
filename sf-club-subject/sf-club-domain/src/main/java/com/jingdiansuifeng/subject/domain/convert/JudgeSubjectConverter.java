package com.jingdiansuifeng.subject.domain.convert;

import com.jingdiansuifeng.subject.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface JudgeSubjectConverter {
    JudgeSubjectConverter INSTANCE = Mappers.getMapper(JudgeSubjectConverter.class);

    SubjectJudge convertBoToJudgeEntity(SubjectAnswerBO subjectAnswerBO);

    List<SubjectAnswerBO> convertJudgeListToAnswerBo(List<SubjectJudge> subjectJudgeList);

}
