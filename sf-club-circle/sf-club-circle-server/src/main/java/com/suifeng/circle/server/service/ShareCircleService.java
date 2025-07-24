package com.suifeng.circle.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.circle.api.req.RemoveShareCircleReq;
import com.suifeng.circle.api.req.SaveShareCircleReq;
import com.suifeng.circle.api.req.UpdateShareCircleReq;
import com.suifeng.circle.api.vo.ShareCircleVO;
import com.suifeng.circle.server.entity.po.ShareCircle;

import java.util.List;

/**
 * <p>
 * 圈子信息 服务类
 * </p>
 */
public interface ShareCircleService extends IService<ShareCircle> {

    List<ShareCircleVO> listResult();

    Boolean saveCircle(SaveShareCircleReq req);

    Boolean updateCircle(UpdateShareCircleReq req);

    Boolean removeCircle(RemoveShareCircleReq req);
}
