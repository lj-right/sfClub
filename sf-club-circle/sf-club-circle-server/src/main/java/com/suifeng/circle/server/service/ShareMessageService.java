package com.suifeng.circle.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.circle.api.common.PageResult;
import com.suifeng.circle.api.req.GetShareMessageReq;
import com.suifeng.circle.api.vo.ShareMessageVO;
import com.suifeng.circle.server.entity.po.ShareMessage;

/**
 * <p>
 * 消息表 服务类
 * </p>
 */
public interface ShareMessageService extends IService<ShareMessage> {

    PageResult<ShareMessageVO> getMessages(GetShareMessageReq req);

    void comment(String fromId, String toId, Long targetId);

    void reply(String fromId, String toId, Long targetId);

    Boolean unRead();

}
