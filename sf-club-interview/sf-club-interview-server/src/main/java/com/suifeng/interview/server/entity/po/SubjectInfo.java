package com.suifeng.interview.server.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目信息表(SubjectInfo)实体类
 *
 */
@Data
public class SubjectInfo implements Serializable {
    private static final long serialVersionUID = -71318372165220898L;

    private String subjectName;

    private String subjectAnswer;

    private String labelName;

    private String categoryName;

}

