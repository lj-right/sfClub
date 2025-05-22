package com.jingdiansuifeng.subject.application.convert;

import com.jingdiansuifeng.subject.application.dto.SubjectLabelDTO;
import com.jingdiansuifeng.subject.domain.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 标签dto的转换
 */
@Mapper
public interface SubjectLabelDTOConverter {
    SubjectLabelDTOConverter INSTANCE = Mappers.getMapper(SubjectLabelDTOConverter.class);

    SubjectLabelBO convertDtoToLabelBo(SubjectLabelDTO subjectLabelDTO);

    List<SubjectLabelDTO> convertBoListToLabelDTOList(List<SubjectLabelBO> subjectLabelBOList);
}
