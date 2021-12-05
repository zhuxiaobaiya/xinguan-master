package com.hl.common.vo.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author huangliang
 * @Date 2021/11/27 14:55
 * @Version 1.0
 * @Description
 */
@Data
public class ProductVO {
    private String id;

    private String pNum;

    @NotBlank
    private String name;

    @NotBlank
    private String model;

    @NotBlank
    private String unit;

    @NotBlank
    private String remark;

    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date modifiedTime;

    private String imageUrl;


    @NotNull(message = "分类不能为空")
    private String[] categoryKeys;

    private String oneCategoryId;

    private String twoCategoryId;

    private String threeCategoryId;

    private Integer status;//是否已经进入回收站:1:逻辑删除,0:正常数据,2:添加待审核

}
