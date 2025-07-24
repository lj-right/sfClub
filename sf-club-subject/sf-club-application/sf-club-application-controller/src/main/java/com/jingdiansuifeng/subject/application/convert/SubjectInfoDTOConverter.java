package com.jingdiansuifeng.subject.application.convert;

import com.jingdiansuifeng.subject.application.dto.SubjectInfoDTO;
import com.jingdiansuifeng.subject.common.entity.PageResult;
import com.jingdiansuifeng.subject.domain.entity.SubjectInfoBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoDTOConverter {
    SubjectInfoDTOConverter INSTANCE = Mappers.getMapper(SubjectInfoDTOConverter.class);

    SubjectInfoBO convertDtoToInfoBo(SubjectInfoDTO subjectInfoDTO);

    SubjectInfoDTO convertBoToInfoDTO(SubjectInfoBO subjectInfoBO);

    List<SubjectInfoDTO> convertBoListToInfoDTOList(List<SubjectInfoBO> subjectInfoBOList);

    PageResult<SubjectInfoDTO> convertBoPageToInfoDTOPage(PageResult<SubjectInfoBO> boPageResult);
}
