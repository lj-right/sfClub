package com.jingdiansuifeng.subject.domain.convert;

import com.jingdiansuifeng.subject.domain.entity.SubjectInfo;
import com.jingdiansuifeng.subject.domain.entity.SubjectInfoBO;
import com.jingdiansuifeng.subject.domain.entity.SubjectOptionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoConverter {
    SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);

    SubjectInfo convertBoToInfo(SubjectInfoBO subjectInfoBO);

    SubjectInfoBO convertInfoToBo(SubjectInfo subjectInfo);

    List<SubjectInfoBO> convertInfoListToBoList(List<SubjectInfo> subjectInfoList);

    SubjectInfoBO convertOptionToBo(SubjectOptionBO subjectOptionBO);

    SubjectInfoBO convertOptionAndInfoToBo(SubjectOptionBO subjectOptionBO,SubjectInfo subjectInfo);
}
