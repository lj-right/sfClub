package com.jingdiansuifeng.subject.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目标签bo
 */
@Data
public class SubjectLabelBO implements Serializable {
    private static final long serialVersionUID = -92612181683405663L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 排序
     */
    private Integer sortNum;

}

