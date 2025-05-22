package com.jingdiansuifeng.subject.application.convert;

import com.jingdiansuifeng.subject.application.dto.SubjectAnswerDTO;
import com.jingdiansuifeng.subject.domain.entity.SubjectAnswerBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectAnswerDTOConverter {
    SubjectAnswerDTOConverter INSTANCE = Mappers.getMapper(SubjectAnswerDTOConverter.class);

    SubjectAnswerBO convertDtoToAnswerBo(SubjectAnswerDTO answerDTO);

    List<SubjectAnswerBO> convertDtoListToAnswerBoList(List<SubjectAnswerDTO> answerDTOList);

    List<SubjectAnswerDTO> convertBoListToAnswerDTOList(List<SubjectAnswerBO> subjectAnswerBOList);
}
