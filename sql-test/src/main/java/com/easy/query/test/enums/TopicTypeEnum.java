package com.easy.query.test.enums;

import com.easy.query.test.annotation.EnumValue;
import com.easy.query.test.conversion.IEnum;

/**
 * create time 2023/5/22 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public enum TopicTypeEnum implements IEnum<TopicTypeEnum> {
    STUDENT(1),

    TEACHER(3),

    CLASSER(9);
    @EnumValue
    private final Integer code;

    TopicTypeEnum(Integer code){

        this.code = code;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public TopicTypeEnum valueOf(Integer enumValue) {
        switch (enumValue){
            case 1:return TopicTypeEnum.STUDENT;
            case 3:return TopicTypeEnum.TEACHER;
            case 9:return TopicTypeEnum.CLASSER;
        }
        throw new UnsupportedOperationException();
    }
}
