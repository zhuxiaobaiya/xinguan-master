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
 * 角色表
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_role")
@Excel(value = "角色表格")
@ApiModel(value="Role对象", description="角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelField(value = "角色编号", width = 100)
    @ApiModelProperty(value = "角色ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ExcelField(value = "角色名称", width = 100)
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ExcelField(value = "角色描述", width = 180)
    @ApiModelProperty(value = "角色描述")
    private String remark;

    @ExcelField(value = "创建时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ExcelField(value = "修改时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ExcelField(
            value = "禁用状态",
            readConverterExp = "有效=1,锁定=0",
            writeConverterExp = "1=有效,0=锁定"
            ,width = 100
    )
    @ApiModelProperty(value = "是否可用,0:不可用，1：可用")
    private Integer status;


}
