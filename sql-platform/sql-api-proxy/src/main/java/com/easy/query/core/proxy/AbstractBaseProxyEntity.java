package com.easy.query.core.proxy;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
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
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsEntryImpl;
import com.easy.query.core.proxy.impl.SQLSelectIgnoreImpl;
import com.easy.query.core.proxy.impl.SQLSelectKeysImpl;

import java.util.function.Consumer;

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
    public SQLSelectAsExpression columnKeys() {
        return new SQLSelectKeysImpl(this.getEntitySQLContext(),getTable());
    }

    public <T> ColumnFuncComparableExpression<T> _now() {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(), null, SQLFunc::now);
    }

    public <T> ColumnFuncComparableExpression<T> _utcNow() {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(), null, SQLFunc::utcNow);
    }

    public <TRProxy extends ProxyEntity<TRProxy, TREntity>, TREntity> TProxy selectAll(TRProxy proxy) {
        entitySQLContext.accept(new SQLSelectAllImpl(proxy.getEntitySQLContext(),proxy.getTable(), new TablePropColumn[0]));
        return castProxy();
    }
    public <TRProxy extends ProxyEntity<TRProxy, TREntity>, TREntity> TProxy selectIgnores(TablePropColumn... ignoreTableProps) {
        entitySQLContext.accept(new SQLSelectIgnoreImpl(ignoreTableProps));
        return castProxy();
    }

    /**
     * 快速选择表达式
     * @param sqlSelectAsExpression
     */
    public TProxy selectExpression(SQLSelectAsExpression... sqlSelectAsExpression) {
        entitySQLContext.accept(sqlSelectAsExpression);
        return castProxy();
    }
    /**
     * 支持动态select+动态group取列防止sql注入
     * @param sqlTableOwner
     * @param property
     */
    public TProxy selectColumn(SQLTableOwner sqlTableOwner, String property) {
        entitySQLContext.accept(new SQLSelectAsEntryImpl(this.getEntitySQLContext(),sqlTableOwner.getTable(),property));
        return castProxy();
    }
    /**
     * 支持动态select+动态selectAs取列防止sql注入
     * @param sqlTableOwner
     * @param property
     * @param propertyAlias
     */
    public TProxy selectColumnAs(SQLTableOwner sqlTableOwner,String property,String propertyAlias) {
        entitySQLContext.accept(new SQLSelectAsEntryImpl(this.getEntitySQLContext(),sqlTableOwner.getTable(),property,propertyAlias));
        return castProxy();
    }

    private TProxy castProxy(){
        return (TProxy)this;
    }
    public TProxy adapter(Consumer<TProxy> select) {
        select.accept(castProxy());
        return castProxy();
    }
}
