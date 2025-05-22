package com.jingdiansuifeng.club.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (AuthRole)实体类
 */
@Data
public class AuthRole implements Serializable {

    private static final long serialVersionUID = 422256240999600735L;
    
    private Long id;
    
    private String roleName;
    
    private String roleKey;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;

    private Integer isDeleted;

}

