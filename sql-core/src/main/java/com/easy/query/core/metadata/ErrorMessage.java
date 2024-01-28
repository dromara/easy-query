package com.easy.query.core.metadata;

/**
 * create time 2024/1/28 22:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class ErrorMessage {
    private final String firstNotNull;
    private final String singleNotNull;
    private final String singleMoreThan;

    public ErrorMessage(String firstNotNull, String singleNotNull, String singleMoreThan){

        this.firstNotNull = firstNotNull;
        this.singleNotNull = singleNotNull;
        this.singleMoreThan = singleMoreThan;
    }

    public String getFirstNotNull() {
        return firstNotNull;
    }

    public String getSingleNotNull() {
        return singleNotNull;
    }

    public String getSingleMoreThan() {
        return singleMoreThan;
    }
}
