package com.jingdiansuifeng.subject.domain.service;

import com.jingdiansuifeng.subject.domain.entity.SubjectLabelBO;

import java.util.List;


/**
 * 题目标签领域服务
 */
public interface SubjectLabelDomainService {


    /**
     * 新增标签
     * @param subjectLabelBO
     */
    Boolean add(SubjectLabelBO subjectLabelBO);

    /**
     * 更新标签
     * @param subjectLabelBO
     */
    Boolean update(SubjectLabelBO subjectLabelBO);

    /**
     * 删除标签
     * @param subjectLabelBO
     * @return
     */
    Boolean delete(SubjectLabelBO subjectLabelBO);

    /**
     * 根据分类id查询标签
     * @param subjectLabelBO
     * @return
     */
    List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO);
}
