package com.jingdiansuifeng.subject.domain.entity;

import com.jingdiansuifeng.subject.common.entity.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目DTO
 */
@Data
public class SubjectOptionBO implements Serializable {

    /**
     * 题目答案
     */
    private String subjectAnswer;


    /**
     * 题目答案集合
     */
    private List<SubjectAnswerBO> optionList;


}

