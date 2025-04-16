package com.easy.query.core.proxy.extra;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.extra.ExtraSelect;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2025/4/16 11:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityExtraSelect<TProxy> implements ExtraSelect {
    private final SQLFuncExpression1<TProxy, SQLSelectAsExpression> selectExpression;

    public EntityExtraSelect(SQLFuncExpression1<TProxy, SQLSelectAsExpression> selectExpression){
        this.selectExpression = selectExpression;
    }
    @Override
    public void select(ColumnAsSelector<?,?> columnAsSelector) {
        TableAvailable table = columnAsSelector.getTable();

        ProxyEntity tEntityProxy = EntityQueryProxyManager.create(EasyObjectUtil.typeCastNullable(table.getEntityClass()));
        ProxyEntity tableProxy = (ProxyEntity)tEntityProxy.create(table, columnAsSelector.getAsSelector().getEntityQueryExpressionBuilder(), columnAsSelector.getRuntimeContext());
        SQLSelectAsExpression selectAsExpression = selectExpression.apply((TProxy)tableProxy);
        selectAsExpression.accept(columnAsSelector.getAsSelector());
    }
}
