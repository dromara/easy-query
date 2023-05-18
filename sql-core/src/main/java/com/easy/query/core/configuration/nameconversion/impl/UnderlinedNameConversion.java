package com.easy.query.core.configuration.nameconversion.impl;

import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 023/2/11 13:29
 * 下划线转换
 *
 * @author xuejiaming
 */
public class UnderlinedNameConversion implements NameConversion {

    @Override
    public String convert(String name) {
        return EasyStringUtil.enCodeUnderlined(name);
    }
}
