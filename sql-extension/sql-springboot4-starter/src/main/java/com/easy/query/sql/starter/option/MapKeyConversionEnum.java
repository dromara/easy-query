package com.easy.query.sql.starter.option;

/**
 * create time 2023/5/5 11:38
 * 文件说明
 *
 * @author xuejiaming
 */
public enum MapKeyConversionEnum {
    /**
     * 默认列名
     */
    DEFAULT,
    /**
     * 全小写 不保留下划线
     */
    LOWER,
    /**
     * 全大写 不保留下划线
     */
    UPPER,
    /**
     * 全小写 保留下划线
     */
    LOWER_UNDERLINED,
    /**
     * 全大写 保留下划线
     */
    UPPER_UNDERLINED
}
