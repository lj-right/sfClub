package com.suifeng.practice.server.dao;


import com.suifeng.practice.server.entity.dto.CategoryDTO;
import com.suifeng.practice.server.entity.po.CategoryPO;
import com.suifeng.practice.server.entity.po.PrimaryCategoryPO;

import java.util.List;

/**
 * 题目分类表(SubjectCategory)表数据库访问层
 *
 */
public interface SubjectCategoryDao {


    List<PrimaryCategoryPO> getPrimaryCategory(CategoryDTO categoryDTO);

    List<PrimaryCategoryPO> selectByParentId(Long id);

    List<CategoryPO> selectList(CategoryDTO categoryDTOTemp);


    CategoryPO selectById(Long categoryId);
}

