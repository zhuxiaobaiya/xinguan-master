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
 * 用户表
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
@Excel("user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelField(value = "编号", width = 50)
    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ExcelField(value = "用户名", width = 100)
    @ApiModelProperty(value = "用户名")
    private String username;

    @ExcelField(value = "昵称", width = 100)
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ExcelField(value = "邮箱", width = 150)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ExcelField(value = "头像url", width = 200)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ExcelField(value = "联系电话", width = 100)
    @ApiModelProperty(value = "联系电话")
    private String phoneNumber;

    @ExcelField(//
            value = "状态",
            readConverterExp = "有效=1,锁定=0",
            writeConverterExp = "1=有效,0=锁定"
            ,width = 50
    )
    @ApiModelProperty(value = "状态 0锁定 1有效")
    private Integer status;

    @ExcelField(value = "创建时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ExcelField(value = "修改时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss",width = 180)
    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;


    @ExcelField(//
            value = "性别",
            readConverterExp = "男=1,女=0",
            writeConverterExp = "1=男,0=女"
            ,width = 50
    )
    @ApiModelProperty(value = "性别 0男 1女 2保密")
    private Integer sex;


    @ExcelField(value = "密码盐值", width = 100)
    @ApiModelProperty(value = "盐")
    private String salt;

    @ExcelField(//
            value = "用户类型",
            readConverterExp = "超级管理员=0,普通用户=1",
            writeConverterExp = "0=超级管理员,1=普通用户"
            ,width = 80
    )
    @ApiModelProperty(value = "0:超级管理员，1：系统用户")
    private Integer type;

    @ExcelField(value = "用户密码", width = 100)
    @ApiModelProperty(value = "密码")
    private String password;

    @ExcelField(value = "出生日期", dateFormat = "yyyy/MM/dd",width = 100)
    @ApiModelProperty(value = "出生日期")
    private Date birth;

    @ExcelField(value = "部门编号", width = 100)
    @ApiModelProperty(value = "部门id")
    private String departmentId;


}
