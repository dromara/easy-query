package com.easy.query.core.basic.extension.track.update;

import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/5/30 21:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public interface ValueUpdateAtomicTrack<TProperty> {
    void configureSet(String propertyName, TProperty originalValue, TProperty currentValue, ColumnSetter<Object> sqlColumnSetter);

    void configureWhere(String propertyName, TProperty originalValue, TProperty currentValue, WherePredicate<Object> sqlWherePredicate);
}
