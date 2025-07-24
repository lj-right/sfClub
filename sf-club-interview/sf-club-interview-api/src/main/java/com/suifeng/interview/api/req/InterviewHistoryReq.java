package com.suifeng.interview.api.req;

import com.suifeng.interview.api.common.PageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class InterviewHistoryReq implements Serializable {


    /**
     * 分页信息
     */
    private PageInfo pageInfo;

}