package com.jingdiansuifeng.subject.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目答案DTO
 */
@Data
public class SubjectAnswerBO implements Serializable {
    private static final long serialVersionUID = -92805262683575195L;
    /**
     * 答案选项标识
     */
    private Integer optionType;
    /**
     * 答案内容
     */
    private String optionContent;
    /**
     * 是否正确
     */
    private Integer isCorrect;

}

