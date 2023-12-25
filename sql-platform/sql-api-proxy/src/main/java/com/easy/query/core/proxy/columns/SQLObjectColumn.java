package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/25 09:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLObjectColumn<TProxy, TProperty> extends SQLColumn<TProxy,TProperty>{

    default SQLAnyColumn<TProxy,TProperty> asAny() {
        Class<?> propertyType = getPropertyType();
        return new SQLAnyColumnImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), EasyObjectUtil.typeCastNullable(propertyType));
    }
}
