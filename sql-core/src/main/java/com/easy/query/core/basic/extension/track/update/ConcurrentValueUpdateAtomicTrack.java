package com.easy.query.core.basic.extension.track.update;

import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/5/30 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcurrentValueUpdateAtomicTrack implements ValueUpdateAtomicTrack<Object> {

    @Override
    public void configureSet(String propertyName, Object originalValue, Object currentValue, ColumnSetter<Object> sqlColumnSetter) {
        //设置为当前值
        sqlColumnSetter.set(propertyName,currentValue);
    }

    @Override
    public void configureWhere(String propertyName, Object originalValue, Object currentValue, WherePredicate<Object> sqlWherePredicate) {

        //设置旧值
        if(originalValue==null){
            sqlWherePredicate.isNull(propertyName);
        }else{
            sqlWherePredicate.eq(propertyName, originalValue);
        }
    }
}
