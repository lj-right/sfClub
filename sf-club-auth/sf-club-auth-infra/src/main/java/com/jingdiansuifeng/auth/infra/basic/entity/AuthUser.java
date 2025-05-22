package com.jingdiansuifeng.auth.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (AuthUser)实体类
 */
@Data
public class AuthUser implements Serializable {

    private static final long serialVersionUID = -50435275569670090L;
    
    private Long id;
    
    private String userName;
    
    private String nickName;
    
    private String email;
    
    private String phone;
    
    private String password;
    
    private Integer sex;
    
    private String avatar;
    
    private Integer status;
    
    private String introduce;
    
    private String extJson;
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

