package com.easy.query.core.configuration.nameconversion.impl;

import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/7/27 18:19
 * 大驼峰
 * 首字母大写
 *
 * @author xuejiaming
 */
public class UpperCamelCaseNameConversion implements NameConversion {
    @Override
    public String convert(String name) {
        if (name == null) {
            return null;
        }
        return EasyStringUtil.toUpperCaseFirstOne(name);
    }
}
