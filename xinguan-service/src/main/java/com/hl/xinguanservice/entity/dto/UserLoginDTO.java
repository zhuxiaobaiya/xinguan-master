package com.hl.xinguanservice.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author huangliang
 * @Date 2021/11/15 10:57
 * @Version 1.0
 * @Description
 */
@Data
@ApiModel(value = "用户登入表单")
public class UserLoginDTO {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;
}
