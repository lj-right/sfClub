package com.jingdiansuifeng.subject.domain.service;

import com.jingdiansuifeng.subject.domain.entity.SubjectMultiple;

import java.util.List;

/**
 * 多选题信息表(SubjectMultiple)表服务接口
 *
 */
public interface SubjectMultipleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectMultiple queryById(Long id);



    /**
     * 新增数据
     *
     * @param subjectMultiple 实例对象
     * @return 实例对象
     */
    SubjectMultiple insert(SubjectMultiple subjectMultiple);

    /**
     * 修改数据
     *
     * @param subjectMultiple 实例对象
     * @return 实例对象
     */
    SubjectMultiple update(SubjectMultiple subjectMultiple);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    void batchInsert(List<SubjectMultiple> subjectMultipleList);

    List<SubjectMultiple> queryByCondition(SubjectMultiple subjectMultiple);
}
