package com.easy.query.core.sql.nameconversion.impl;

import com.easy.query.core.sql.nameconversion.NameConversion;
import com.easy.query.core.util.StringUtil;

/**
 * create time 023/2/11 13:29
 * 下划线转换
 *
 * @author xuejiaming
 */
public class UnderlinedNameConversion implements NameConversion {

    @Override
    public String convert(String name) {
        return StringUtil.enCodeUnderlined(name);
    }
}
