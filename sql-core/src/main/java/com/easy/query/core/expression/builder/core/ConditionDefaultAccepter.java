package com.easy.query.core.expression.builder.core;

import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/8/19 15:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConditionDefaultAccepter implements ConditionAccepter {
    public static final ConditionAccepter DEFAULT=new ConditionDefaultAccepter();
    @Override
    public boolean accept(Object value) {
        if(value==null){
            return false;
        }
        if(value instanceof String){
            return EasyStringUtil.isNotBlank((String) value);
        }
        return false;
    }
}
