package com.hl.common.error;

/**
 * @Author huangliang
 * @Date 2021/11/12 20:24
 * @Version 1.0
 * @Description 自定义的错误描述枚举类需实现该接口
 */


public interface BaseError {

    /**
     * 获取错误码
     * @return
     */
    int getErrorCode();

    /**
     * 获取错误信息
     * @return
     */
    String getErrorMsg();


    /**
     * 设置错误信息
     * @param message
     * @return
     */
    BaseError setErrorMsg(String message);
}
