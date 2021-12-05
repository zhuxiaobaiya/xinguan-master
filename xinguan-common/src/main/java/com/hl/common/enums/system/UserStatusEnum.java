package com.hl.common.enums.system;

/**
 * @Author huangliang
 * @Date 2021/11/15 22:16
 * @Version 1.0
 * @Description
 */
public enum  UserStatusEnum {

    DISABLE(0),
    AVAILABLE(1);

    private int statusCode;

    UserStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
