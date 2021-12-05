package com.hl.common.vo.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author huangliang
 * @Date 2021/11/26 14:26
 * @Version 1.0
 * @Description
 */
@Data
public class ProductCategoryVO {
    private String id;

    @NotBlank(message = "类目名称不能为空")
    private String name;

    @NotBlank(message = "类目备注不能为空")
    private String remark;

    @NotNull(message = "排序号不能为空")
    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modifiedTime;

    /** 父级分类id*/
    @NotNull(message = "父级菜单不能为空")
    private String pid;
}
