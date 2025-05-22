package com.jingdiansuifeng.subject.application.convert;

import com.jingdiansuifeng.subject.application.dto.SubjectCategoryDTO;
import com.jingdiansuifeng.subject.domain.entity.SubjectCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryDTOConverter {
    SubjectCategoryDTOConverter INSTANCE = Mappers.getMapper(SubjectCategoryDTOConverter.class);

    SubjectCategoryBO convertDtoToCategoryBo(SubjectCategoryDTO subjectCategoryDTO);

    List<SubjectCategoryDTO> convertBoListToCategoryDTOList(List<SubjectCategoryBO> subjectCategoryBOList);

    SubjectCategoryDTO convertBoToDto(SubjectCategoryBO bo);
}
