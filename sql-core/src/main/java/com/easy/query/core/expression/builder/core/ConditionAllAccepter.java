package com.easy.query.core.expression.builder.core;

/**
 * create time 2023/8/19 15:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConditionAllAccepter implements ConditionAccepter {
    public static final ConditionAccepter DEFAULT=new ConditionAllAccepter();
    private ConditionAllAccepter(){

    }
    @Override
    public boolean accept(Object value) {
        return true;
    }
}
