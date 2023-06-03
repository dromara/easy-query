package com.easy.query.core.basic.extension.track.update;

import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/6/3 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class LongNotValueUpdateAtomicTrack implements ValueUpdateAtomicTrack<Long> {
    @Override
    public void configureSet(String propertyName, Long originalValue, Long currentValue, ColumnSetter<Object> sqlColumnSetter) {
        if (originalValue == null || currentValue == null) {
            throw new IllegalArgumentException(propertyName + ":originalValue==null||currentValue==null");
        }
        //扣库存
        if (originalValue > currentValue) {
            long decrement = originalValue - currentValue;
            sqlColumnSetter.setDecrement(propertyName, decrement);
        } else {
            long increment = currentValue - originalValue;
            sqlColumnSetter.setIncrement(propertyName, increment);
        }
    }

    @Override
    public void configureWhere(String propertyName, Long originalValue, Long currentValue, WherePredicate<Object> sqlWherePredicate) {
        //扣库存
        if (originalValue > currentValue) {
            long decrement = originalValue - currentValue;
            sqlWherePredicate.ge(propertyName, decrement);//where column>=decrement
        }//增加不用加更新条件
    }
}
