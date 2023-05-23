package com.easy.query.test.enums;

/**
 * create time 2023/5/22 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public enum TopicType1Enum {
    STUDENT(1),

    TEACHER(3),

    CLASSER(9);
    private final Integer code;

    TopicType1Enum(Integer code){

        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}
