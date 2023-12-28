package com.easy.query.core.proxy;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.SQLBooleanColumn;
import com.easy.query.core.proxy.columns.SQLDateTimeColumn;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.columns.SQLNumberColumn;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLBooleanColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLDateTimeColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNavigateColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNumberColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLStringColumnImpl;
import com.easy.query.core.proxy.core.ColumnSelectSQLContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy, TEntity>, EntitySQLContextAvailable {

    protected TableAvailable table;
    protected EntitySQLContext entitySQLContext=new ColumnSelectSQLContext();

    @Deprecated
    protected <TProperty> SQLColumn<TProxy, TProperty> get(String property) {
        return get(property,null);
    }
    protected <TProperty> SQLColumn<TProxy, TProperty> get(String property,@Nullable Class<TProperty> propType) {
        return new SQLColumnImpl<>(entitySQLContext, table, property, propType);
    }
    protected <TProperty> SQLDateTimeColumn<TProxy, TProperty> getDateTimeColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLDateTimeColumnImpl<>(entitySQLContext, table, property, propType);
    }
    protected <TProperty> SQLNumberColumn<TProxy, TProperty> getNumberColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLNumberColumnImpl<>(entitySQLContext, table, property, propType);
    }
    protected <TProperty> SQLStringColumn<TProxy, TProperty> getStringColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLStringColumnImpl<>(entitySQLContext, table, property, propType);
    }
    protected <TProperty> SQLBooleanColumn<TProxy, TProperty> getBooleanColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLBooleanColumnImpl<>(entitySQLContext, table, property, propType);
    }
    protected <TProperty> SQLAnyColumn<TProxy, TProperty> getAnyColumn(String property, @Nullable Class<TProperty> propType) {
        return new SQLAnyColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected <TProperty> SQLNavigateColumn<TProxy, TProperty> getNavigate(String property, Class<TProperty> propType) {
        return new SQLNavigateColumnImpl<>(entitySQLContext, table, property, propType);
    }

    protected void selectColumns(SQLSelectAsExpression... sqlSelectAsExpression) {
        entitySQLContext.accept(sqlSelectAsExpression);
    }
//    protected <TSubQuery> SQLSelectAsExpression subQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, TablePropColumn propColumn) {
//        return Select.subQueryAs(subQueryableFunc,propColumn);
//    }
}
