package com.hl.common.enums.system;

/**
 * @Author huangliang
 * @Date 2021/11/15 22:16
 * @Version 1.0
 * @Description
 */
public enum UserTypeEnum {

    SYSTEM_ADMIN(0),//系统管理员admin

    SYSTEM_USER(1);//系统的普通用户

    private int typeCode;

    UserTypeEnum(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
}