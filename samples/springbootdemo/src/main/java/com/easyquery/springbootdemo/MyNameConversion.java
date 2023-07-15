package com.easyquery.springbootdemo;

import com.easy.query.core.configuration.nameconversion.NameConversion;

/**
 * create time 2023/7/4 22:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyNameConversion implements NameConversion {
    @Override
    public String convert(String name) {
        return "["+name+"]";
    }
}
