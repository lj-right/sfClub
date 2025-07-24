package com.suifeng.circle.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 圈子信息
 */
@Getter
@Setter
public class RemoveShareCircleReq implements Serializable {

    private Long id;

}
