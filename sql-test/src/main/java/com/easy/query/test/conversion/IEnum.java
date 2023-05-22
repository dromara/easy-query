package com.easy.query.test.conversion;

/**
 * create time 2023/5/22 14:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IEnum<TEnum extends IEnum<TEnum>> {
    Integer getCode();
    TEnum valueOf(Integer enumValue);
}
