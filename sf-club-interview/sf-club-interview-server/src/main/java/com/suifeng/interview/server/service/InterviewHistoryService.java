package com.suifeng.interview.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.interview.api.common.PageResult;
import com.suifeng.interview.api.req.InterviewHistoryReq;
import com.suifeng.interview.api.req.InterviewSubmitReq;
import com.suifeng.interview.api.vo.InterviewHistoryVO;
import com.suifeng.interview.api.vo.InterviewResultVO;
import com.suifeng.interview.server.entity.po.InterviewHistory;

/**
 * 面试汇总记录表(InterviewHistory)表服务接口
 *
 */
public interface InterviewHistoryService extends IService<InterviewHistory> {

    void logInterview(InterviewSubmitReq req, InterviewResultVO submit);


    PageResult<InterviewHistoryVO> getHistory(InterviewHistoryReq req);

}
