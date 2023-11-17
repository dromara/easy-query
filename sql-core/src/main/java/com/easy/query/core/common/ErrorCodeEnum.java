package com.easy.query.core.common;

/**
 * create time 2023/11/16 23:18
 * 文件说明
 *
 * @author xuejiaming
 */
public enum ErrorCodeEnum implements ErrorCode{
    E1001("1001"),
    E1002("1002");

    private final String code;

    ErrorCodeEnum(String code){

        this.code = code;
    }
    @Override
    public String errorMsg(String msg) {
        return String.format("[%s]%s",code,msg);
    }
}
