package com.easy.query.core.configuration.nameconversion.impl;

import com.easy.query.core.configuration.nameconversion.MapKeyNameConversion;

/**
 * create time 2025/2/18 14:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMapKeyNameConversion implements MapKeyNameConversion {
    @Override
    public String convert(String name) {
        return name;
    }
}
