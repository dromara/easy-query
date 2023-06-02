package com.easy.query.core.basic.extension.track.update;

import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/5/30 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTrackValueUpdate implements TrackValueUpdate<Object> {

    @Override
    public void configureSet(String propertyName, Object originalValue, Object currentValue, ColumnSetter<Object> sqlColumnSetter) {

    }

    @Override
    public void configureWhere(String propertyName, Object originalValue, Object currentValue, WherePredicate<Object> sqlWherePredicate) {

    }
}
