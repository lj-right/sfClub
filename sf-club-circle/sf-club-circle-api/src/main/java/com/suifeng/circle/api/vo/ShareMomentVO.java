package com.suifeng.circle.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 动态信息
 *
 */
@Getter
@Setter
public class ShareMomentVO implements Serializable {


    /**
     * 动态ID
     */
    private Long id;

    /**
     * 圈子ID
     */
    private Long circleId;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 动态图片内容
     */
    private List<String> picUrlList;

    /**
     * 回复数
     */
    private Integer replyCount;

    /**
     * 创建时间
     */
    private Long createdTime;

    private String userName;

    private String userAvatar;

}
