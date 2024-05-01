package com.easy.query.test.entity.blogtest;

import com.easy.query.test.annotation.EnumValue;
import com.easy.query.test.conversion.IEnum;
import lombok.Getter;

/**
 * create time 2024/4/30 17:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
public enum CertStatusEnum implements IEnum<CertStatusEnum> {
    NORMAL(1,"正常"),

    WILL_INVALID(2,"临期"),

    INVALID(3,"过期");
    @EnumValue
    private final Integer code;
    private final String name;

    CertStatusEnum(Integer code,String name){

        this.code = code;
        this.name = name;
    }
    @Override
    public CertStatusEnum valueOf(Integer enumValue) {
        switch (enumValue){
            case 1:return NORMAL;
            case 2:return WILL_INVALID;
            case 3:return INVALID;
        }
        throw new UnsupportedOperationException();
    }
}
