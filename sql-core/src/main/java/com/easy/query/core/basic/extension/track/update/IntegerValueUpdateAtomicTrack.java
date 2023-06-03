package com.easy.query.core.basic.extension.track.update;

import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/5/30 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class IntegerValueUpdateAtomicTrack implements ValueUpdateAtomicTrack<Integer> {
    @Override
    public void configureSet(String propertyName, Integer originalValue, Integer currentValue, ColumnSetter<Object> sqlColumnSetter) {
        //扣库存
        if (originalValue > currentValue) {
            int decrement = originalValue - currentValue;
            sqlColumnSetter.setDecrement(propertyName, decrement);
        } else {
            int increment = currentValue - originalValue;
            sqlColumnSetter.setIncrement(propertyName, increment);
        }
    }

    @Override
    public void configureWhere(String propertyName, Integer originalValue, Integer currentValue, WherePredicate<Object> sqlWherePredicate) {
        //扣库存
        if (originalValue > currentValue) {
            int decrement = originalValue - currentValue;
            sqlWherePredicate.ge(propertyName, decrement);//where column>=decrement
        }//增加不用加更新条件
    }
}
