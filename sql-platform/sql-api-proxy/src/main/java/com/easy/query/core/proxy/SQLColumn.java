package com.easy.query.core.proxy;

import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.extension.ColumnComparableExpression;
import com.easy.query.core.proxy.set.DSLColumnSet;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/22 13:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumn<TProxy, TProperty> extends ColumnComparableExpression<TProperty>,
        PropTypeColumn<TProperty>, DSLColumnSet<TProxy,TProperty> {

    default <TRProxy,TRProperty> SQLAnyColumn<TRProxy, TRProperty> asAny() {
        Class<?> propertyType = getPropertyType();
        SQLAnyColumnImpl<TProxy, TProperty> column = new SQLAnyColumnImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), EasyObjectUtil.typeCastNullable(propertyType));
        column._setProxy(castChain());
        return EasyObjectUtil.typeCastNullable(column);
    }

    @Override
    default <TR> SQLColumn<TProxy, TR> asAnyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    void _setProxy(TProxy tProxy);
}
