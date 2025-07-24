package com.suifeng.interview.server.service;

import com.suifeng.interview.api.enums.EngineEnum;
import com.suifeng.interview.api.req.InterviewSubmitReq;
import com.suifeng.interview.api.req.StartReq;
import com.suifeng.interview.api.vo.InterviewQuestionVO;
import com.suifeng.interview.api.vo.InterviewResultVO;
import com.suifeng.interview.api.vo.InterviewVO;

import java.util.List;

/**
 * <p>
 * 面试引擎
 * </p>
 */
public interface InterviewEngine {

    /**
     * 引擎类型
     */
    EngineEnum engineType();

    /**
     * 通过简历关键字获取面试关键字
     */
    InterviewVO analyse(List<String> KeyWords);

    /**
     * 通过面试关键字获取面试题
     */
    InterviewQuestionVO start(StartReq req);

    /**
     * 提交面试题
     */
    InterviewResultVO submit(InterviewSubmitReq req);

}
