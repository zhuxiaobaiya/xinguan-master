package com.hl.xinguanservice.entity.business;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("biz_consumer")
@ApiModel(value="Consumer对象", description="")
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "物资消费方")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    private Date createTime;

    private Date modifiedTime;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    private Integer sort;

    @ApiModelProperty(value = "联系人姓名")
    private String contact;


}
