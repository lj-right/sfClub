package com.jingdiansuifeng.subject.domain.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 题目分类表(SubjectCategory)实体类
 *
 */
@Data
public class SubjectCategory implements Serializable {
    private static final long serialVersionUID = 218416565212661681L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类的类型
     */
    private Integer categoryType;

    /**
     * 图标连接
     */
    private String imageUrl;

    /**
     * 父级id
     */
    private Long parentId;

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
     * 删除标志 0 未删除 1 已删除
     */
    private Integer isDeleted;

}

