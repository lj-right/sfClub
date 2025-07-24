package com.suifeng.practice.server.dao;


import com.suifeng.practice.server.entity.po.LabelCountPO;
import com.suifeng.practice.server.entity.po.SubjectMappingPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目分类关系表(SubjectMapping)表数据库访问层
 *
 */
public interface SubjectMappingDao {

    List<LabelCountPO> getLabelSubjectCount(@Param("categoryId") Long categoryId,
                                            @Param("subjectTypeList") List<Integer> subjectTypeList);

    List<SubjectMappingPO> getLabelIdsBySubjectId(@Param("subjectId") Long subjectId);
}

