package com.easy.query.core.config;

import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.annotation.Table;

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
