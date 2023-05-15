package com.easy.query.core.enums;

/**
 * create time 2023/5/14 00:25
 * 文件说明
 *
 * @author xuejiaming
 */
public enum MergeBehaviorEnum {
    DEFAULT(1),
    ORDER(1<<1),
    PAGINATION(1<<2),
    ALL(1<<3),
    ANY(1<<4),
    COUNT(1<<5),
    GROUP(1<<6),
    STREAM_GROUP(1<<7),
    SEQUENCE_PAGINATION(1<<8),
    REVERSE_PAGINATION(1<<9),
    SEQUENCE_COUNT(1<<10);
    private final int code;

    MergeBehaviorEnum(int code){

        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
