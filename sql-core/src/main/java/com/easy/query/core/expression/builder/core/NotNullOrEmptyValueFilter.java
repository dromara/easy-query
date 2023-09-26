package com.easy.query.core.expression.builder.core;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/8/19 15:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class NotNullOrEmptyValueFilter implements ValueFilter {
    public static final ValueFilter DEFAULT=new NotNullOrEmptyValueFilter();
    @Override
    public boolean accept(TableAvailable table, String property, Object value) {
        if(value==null){
            return false;
        }
        if(value instanceof String){
            return EasyStringUtil.isNotBlank((String) value);
        }
        return true;
    }
}
