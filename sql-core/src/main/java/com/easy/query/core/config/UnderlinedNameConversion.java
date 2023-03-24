package com.easy.query.core.config;

import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.annotation.Table;

/**
 * @FileName: UnderlinedNameConversion.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:29
 * @author xuejiaming
 */
public class UnderlinedNameConversion implements NameConversion {

    @Override
    public String convert(String name) {
        return StringUtil.enCodeUnderlined(name);
    }
}
