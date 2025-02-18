package com.easy.query.test.conversion;

import com.easy.query.core.configuration.nameconversion.MapKeyNameConversion;

/**
 * create time 2025/2/18 14:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpperMapKeyNameConversion implements MapKeyNameConversion {
    @Override
    public String convert(String name) {
        if (name == null) {
            return null;
        }
        return name.toUpperCase();
    }
}
