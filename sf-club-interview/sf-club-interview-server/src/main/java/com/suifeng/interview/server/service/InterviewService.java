package com.suifeng.interview.server.service;

import com.suifeng.interview.api.req.InterviewReq;
import com.suifeng.interview.api.req.InterviewSubmitReq;
import com.suifeng.interview.api.req.StartReq;
import com.suifeng.interview.api.vo.InterviewQuestionVO;
import com.suifeng.interview.api.vo.InterviewResultVO;
import com.suifeng.interview.api.vo.InterviewVO;

public interface InterviewService {

    InterviewVO analyse(InterviewReq req);

    InterviewQuestionVO start(StartReq req);

    InterviewResultVO submit(InterviewSubmitReq req);
}
