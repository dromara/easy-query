package org.jdqc.core.enums;

/**
 * 用于表示查询时候的表所处定位是什么
 * @FileName: SelectTableInfoTypeEnum.java
 * @Description: 文件说明
 * @Date: 2023/2/7 11:40
 * @Created by xuejiaming
 */
public enum SelectTableInfoTypeEnum {
    FROM(1),
    LEFT_JOIN(2),
    INNER_JOIN(3),
    RIGHT_JOIN(4);

    private final Integer code;

    SelectTableInfoTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
