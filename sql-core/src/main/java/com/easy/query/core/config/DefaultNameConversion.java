package com.easy.query.core.config;

import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.annotation.Table;

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
