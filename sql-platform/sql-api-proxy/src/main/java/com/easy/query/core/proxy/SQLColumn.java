package com.easy.query.core.proxy;

import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.extension.ColumnComparableExpression;
import com.easy.query.core.proxy.predicate.DSLPropertyOnlyAssertPredicate;
import com.easy.query.core.proxy.set.DSLColumnSet;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/22 13:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumn<TProxy, TProperty> extends ColumnComparableExpression<TProperty>,
        PropTypeColumn<TProperty>, DSLColumnSet<TProperty> {

    default SQLAnyColumn<TProxy, TProperty> asAny() {
        Class<?> propertyType = getPropertyType();
        return new SQLAnyColumnImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), EasyObjectUtil.typeCastNullable(propertyType));
    }

    @Override
    default <TR> SQLColumn<TProxy, TR> setPropertyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
