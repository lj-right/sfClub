package com.jingdiansuifeng.subject.domain.service;

import com.jingdiansuifeng.subject.domain.entity.SubjectJudge;

import java.util.List;

/**
 * 判断题信息表(SubjectJudge)表服务接口
 *
 */
public interface SubjectJudgeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectJudge queryById(Long id);


    /**
     * 新增数据
     *
     * @param subjectJudge 实例对象
     * @return 实例对象
     */
    SubjectJudge insert(SubjectJudge subjectJudge);

    /**
     * 修改数据
     *
     * @param subjectJudge 实例对象
     * @return 实例对象
     */
    SubjectJudge update(SubjectJudge subjectJudge);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 批量插入数据
     * @param subjectJudgeList
     */
    void batchInsert(List<SubjectJudge> subjectJudgeList);

    List<SubjectJudge> queryByCondition(SubjectJudge subjectJudge);
}
