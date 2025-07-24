package com.suifeng.circle.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.circle.api.req.GetShareCommentReq;
import com.suifeng.circle.api.req.RemoveShareCommentReq;
import com.suifeng.circle.api.req.SaveShareCommentReplyReq;
import com.suifeng.circle.api.vo.ShareCommentReplyVO;
import com.suifeng.circle.server.entity.po.ShareCommentReply;

import java.util.List;

/**
 * <p>
 * 评论及回复信息 服务类
 * </p>
 */
public interface ShareCommentReplyService extends IService<ShareCommentReply> {

    Boolean saveComment(SaveShareCommentReplyReq req);

    Boolean removeComment(RemoveShareCommentReq req);

    List<ShareCommentReplyVO> listComment(GetShareCommentReq req);

}
