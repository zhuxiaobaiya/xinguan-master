package com.hl.xinguanservice.entity.business;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("biz_product")
@ApiModel(value="Product对象", description="")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "商品编号")
    private String pNum;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "图片")
    private String imageUrl;

    @ApiModelProperty(value = "规格型号")
    private String model;

    @ApiModelProperty(value = "计算单位")
    private String unit;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ApiModelProperty(value = "1级分类")
    private String oneCategoryId;

    @ApiModelProperty(value = "2级分类")
    private String twoCategoryId;

    @ApiModelProperty(value = "3级分类")
    private String threeCategoryId;

    @ApiModelProperty(value = "是否删除:1物资正常,0:物资回收,2:物资审核中")
    private Integer status;


}
