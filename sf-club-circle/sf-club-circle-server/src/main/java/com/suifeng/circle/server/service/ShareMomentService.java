package com.suifeng.circle.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.circle.api.common.PageResult;
import com.suifeng.circle.api.req.GetShareMomentReq;
import com.suifeng.circle.api.req.RemoveShareMomentReq;
import com.suifeng.circle.api.req.SaveMomentCircleReq;
import com.suifeng.circle.api.vo.ShareMomentVO;
import com.suifeng.circle.server.entity.po.ShareMoment;

/**
 * <p>
 * 动态信息 服务类
 * </p>
 */
public interface ShareMomentService extends IService<ShareMoment> {

    Boolean saveMoment(SaveMomentCircleReq req);

    PageResult<ShareMomentVO> getMoments(GetShareMomentReq req);

    Boolean removeMoment(RemoveShareMomentReq req);

    void incrReplyCount(Long id, int count);

}
