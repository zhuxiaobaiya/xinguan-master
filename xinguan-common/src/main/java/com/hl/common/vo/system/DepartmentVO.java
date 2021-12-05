package com.hl.common.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author huangliang
 * @Date 2021/11/23 14:23
 * @Version 1.0
 * @Description
 */
@Data
public class DepartmentVO {
    private String id;

    @NotBlank(message = "院系名称不能为空")
    private String name;

    @NotBlank(message = "办公电话不能为空")
    private String phone;

    @NotBlank(message = "办公地址不能为空")
    private String address;


    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modifiedTime;


    /** 部门内人数**/
    private int total;
}
