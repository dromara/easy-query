package com.easy.query.test;

import com.easy.query.core.enums.EasyFunc;

/**
 * create time 2023/3/30 22:13
 * 文件说明
 *
 * @author xuejiaming
 */
public enum IfNullEasyFunc implements EasyFunc {
    IfNull("IfNull(%s,'')");
    private final String format;

    IfNullEasyFunc(String format){

        this.format = format;
    }
    @Override
    public String getFuncColumn(String column) {
        return String.format(format,column);
    }
}
