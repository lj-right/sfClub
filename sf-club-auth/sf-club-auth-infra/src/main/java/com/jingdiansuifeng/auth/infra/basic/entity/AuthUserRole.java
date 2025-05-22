package com.jingdiansuifeng.auth.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (AuthUserRole)实体类
 */
@Data
public class AuthUserRole implements Serializable {

    private static final long serialVersionUID = -34579360091831908L;
    
    private Long id;
    
    private Long userId;
    
    private Long roleId;
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

