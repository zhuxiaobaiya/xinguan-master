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
 * 菜单表
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_menu")
@Excel(value = "菜单表格")
@ApiModel(value="Menu对象", description="菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelField(value = "菜单/按钮编号", width = 150)
    @ApiModelProperty(value = "菜单/按钮ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ExcelField(value = "上级菜单ID", width = 150)
    @ApiModelProperty(value = "上级菜单ID")
    private Long parentId;

    @ExcelField(value = "菜单/按钮名称", width = 150)
    @ApiModelProperty(value = "菜单/按钮名称")
    private String menuName;

    @ExcelField(value = "菜单URL", width = 150)
    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ExcelField(value = "权限编码", width = 180)
    @ApiModelProperty(value = "权限编码")
    private String perms;

    @ExcelField(value = "菜单图标", width = 80)
    @ApiModelProperty(value = "菜单图标")
    private String icon;


    @ExcelField(
            value = "菜单类型",
            readConverterExp = "按钮=1,菜单=0",
            writeConverterExp = "1=按钮,0=菜单"
            ,width = 100
    )
    @ApiModelProperty(value = "类型 0菜单 1按钮")
    private Integer type;

    @ExcelField(value = "排序", width = 90)
    @ApiModelProperty(value = "排序")
    private Long orderNum;

    @ExcelField(value = "创建时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ExcelField(value = "修改时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ExcelField(
            value = "是否可用",
            readConverterExp = "可用=1,不可用=0",
            writeConverterExp = "1=可用,0=不可用"
            ,width = 100
    )
    @ApiModelProperty(value = "0：，1：可用")
    private Integer available;

    @ExcelField(
            value = "是否展开",
            readConverterExp = "展开=1,不展开=0",
            writeConverterExp = "1=展开,0=不展开"
            ,width = 100
    )
    @ApiModelProperty(value = "0:不展开，1：展开")
    private Integer open;


}
