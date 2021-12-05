package com.hl.common.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author huangliang
 * @Date 2021/11/24 12:34
 * @Version 1.0
 * @Description
 */
@Data
public class RoleVO {

    private String id;

    @NotBlank(message = "角色名必填")
    private String roleName;

    @NotBlank(message = "角色描述信息必填")
    private String remark;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    private Date modifiedTime;

    private Boolean status;
}

