package com.easy.query.core.configuration.nameconversion.impl;

import com.easy.query.core.configuration.nameconversion.NameConversion;

/**
 * create time 2023/2/11 13:22
 * 默认不转换
 *
 * @author xuejiaming
 */
public class DefaultNameConversion implements NameConversion {


    @Override
    public String convert(String name) {
        return name;
    }
}
