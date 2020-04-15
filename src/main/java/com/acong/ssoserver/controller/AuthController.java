package com.acong.ssoserver.controller;


import com.acong.ssoserver.entity.User;
import com.acong.ssoserver.entity.UserRequest;
import com.acong.ssoserver.service.UserService;
import com.acong.ssoserver.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(tags = "用户权限管理")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    @ApiOperation("创建用户")
    public ResultUtil registerUser(@RequestBody @Validated UserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        userService.save(user);
        return ResultUtil.success(user);
    }

}
