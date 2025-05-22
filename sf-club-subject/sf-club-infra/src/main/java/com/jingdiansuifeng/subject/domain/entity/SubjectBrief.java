package com.jingdiansuifeng.subject.domain.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 简答题信息表(SubjectBrief)实体类
 *
 */
@Data
public class SubjectBrief implements Serializable {
    private static final long serialVersionUID = -50036571053412258L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 题目答案
     */
    private String subjectAnswer;
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
    
    private Integer isDeleted;


}

