package com.jingdiansuifeng.subject.domain.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 判断题信息表(SubjectJudge)实体类
 *
 */
@Data
public class SubjectJudge implements Serializable {
    private static final long serialVersionUID = 920375331410429746L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 是否正确
     */
    private Integer isCorrect;
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

