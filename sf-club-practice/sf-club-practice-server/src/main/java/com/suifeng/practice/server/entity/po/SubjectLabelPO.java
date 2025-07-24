package com.suifeng.practice.server.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目标签表(SubjectLabel)实体类
 *
 */
@Data
public class SubjectLabelPO implements Serializable {
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

