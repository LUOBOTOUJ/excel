package com.vitality.material.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("用户管理")
public class UserRequest implements Serializable {

    @ApiModelProperty("用户id，自增")
    private Integer id;

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty("角色 ROLE_USER")
    @NotNull(message = "角色不能为空")
    private String role;

}
