package com.easy.query.core.metadata;

/**
 * create time 2024/1/28 22:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class ErrorMessage {

    private final String notNull;

    public ErrorMessage(String notNull){
        this.notNull = notNull;
    }

    public String getNotNull() {
        return notNull;
    }
}
