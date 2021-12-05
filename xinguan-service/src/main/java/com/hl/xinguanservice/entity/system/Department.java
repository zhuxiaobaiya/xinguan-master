package com.hl.xinguanservice.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_department")
@Excel("department")
@ApiModel(value="Department对象", description="")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelField(value = "部门编号", width = 50)
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ExcelField(value = "部门名称", width = 100)
    @ApiModelProperty(value = "部门名称")
    private String name;

    @ExcelField(value = "联系电话", width = 120)
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ExcelField(value = "部门地址", width = 150)
    @ApiModelProperty(value = "部门地址")
    private String address;

    @ExcelField(value = "创建时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ExcelField(value = "修改时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

}
