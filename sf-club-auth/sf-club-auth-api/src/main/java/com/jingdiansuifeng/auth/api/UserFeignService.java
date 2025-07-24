package com.jingdiansuifeng.auth.api;

import com.jingdiansuifeng.auth.entity.AuthUserDTO;
import com.jingdiansuifeng.auth.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户服务feign
 */
@FeignClient(name = "sf-club-auth")
public interface UserFeignService {

    @PostMapping("/user/getUserInfo")
    Result<AuthUserDTO> getUserInfo(@RequestBody AuthUserDTO authUserDTO);

    @PostMapping("/user/listByIds")
    Result<List<AuthUserDTO>> listUserInfoByIds(@RequestBody List<String> userNameList);

}
