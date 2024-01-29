package com.easy.query.core.enums;

/**
 * create time 2023/5/8 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public enum ExecuteMethodEnum {
    UNKNOWN(1),
    FIRST(1<<1),
    LIST(1<<2),
    COUNT(1<<3),
    COUNT_DISTINCT(1<<4),
    ANY(1<<5),
    ALL(1<<6),
    MAX(1<<7),
    MIN(1<<8),
    SUM(1<<9),
    AVG(1<<10),
    LEN(1<<11),
    INSERT(1<<12),
    UPDATE(1<<13),
    DELETE(1<<14),
    StreamResult(1<<15),
    SINGLE(1<<16),
    FIND(1<<17);
    private final int code;

    ExecuteMethodEnum(int code){

        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
