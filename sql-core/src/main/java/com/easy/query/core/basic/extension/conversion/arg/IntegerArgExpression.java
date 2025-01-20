package com.easy.query.core.basic.extension.conversion.arg;

/**
 * create time 2025/1/20 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class IntegerArgExpression implements ArgExpression{
    @Override
    public Object parse(String val) {
        return Integer.parseInt(val);
    }
}
