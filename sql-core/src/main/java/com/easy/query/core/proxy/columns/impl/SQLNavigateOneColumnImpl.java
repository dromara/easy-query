package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.columns.SQLNavigateOneColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/9 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNavigateOneColumnImpl<TProxy, TProperty> extends SQLColumnImpl<TProxy,TProperty> implements SQLNavigateOneColumn<TProxy, TProperty> {
    public SQLNavigateOneColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property);
    }

    @Override
    public ClientQueryable<TProperty> asQueryable() {
        NavigateMetadata navigateMetadata = getTable().getEntityMetadata().getNavigateNotNull(getValue());
        return new EasyClientQueryable<>(EasyObjectUtil.typeCastNullable(navigateMetadata.getNavigatePropertyType()),null);
    }
}
