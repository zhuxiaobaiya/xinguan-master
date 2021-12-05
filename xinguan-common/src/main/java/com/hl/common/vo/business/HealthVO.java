package com.hl.common.vo.business;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author huangliang
 * @Date 2021/11/28 14:19
 * @Version 1.0
 * @Description
 */
@Data
public class HealthVO {

    private Long id;

    @NotBlank(message="地址不能为空")
    private String address;

    private String userId;

    @NotNull(message = "当前情况不能为空")
    private Integer situation;

    @NotNull(message = "是否接触不能为空")
    private Integer touch;

    @NotNull(message = "是否路过不能为空")
    private Integer passby;

    @NotNull(message = "是否招待不能为空")
    private Integer reception;

    private Date createTime;
}
