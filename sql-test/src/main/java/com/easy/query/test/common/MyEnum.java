package com.easy.query.test.common;

import com.easy.query.test.annotation.EnumValue;
import lombok.Getter;

/**
 * create time 2023/10/17 08:34
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
public enum MyEnum {
    ZJ(1,"浙江"),
    JS(2,"江苏"),
    SH(3,"上海"),
    BJ(4,"北京");


    @EnumValue
    private final Integer code;
    private final String name;

    MyEnum(Integer code, String name){

        this.code = code;
        this.name = name;
    }
}
