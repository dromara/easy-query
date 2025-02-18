package com.easy.query.core.configuration.nameconversion;

/**
 * create time 2025/2/18 14:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapKeyNameConversion {
    /**
     * 属性名或者表名转成列名
     * @param name 属性名或者表名
     * @return 转换后的列名
     */
    String convert(String name);
}
