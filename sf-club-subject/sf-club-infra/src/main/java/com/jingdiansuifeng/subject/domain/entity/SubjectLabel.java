package com.jingdiansuifeng.subject.domain.entity;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * 题目标签表(SubjectLabel)实体类
 *
 */
@Data
public class SubjectLabel implements Serializable {
    private static final long serialVersionUID = -92612181683405663L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 标签名称
     */
    private String labelName;

    /**
     *  标签分类id
     */
    private Long categoryId;

    /**
     * 排序
     */
    private Integer sortNum;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDeleted;

}

