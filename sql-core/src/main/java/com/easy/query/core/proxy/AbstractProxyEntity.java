package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy,TEntity> {

    protected TableAvailable table;
    protected <TProperty> SQLColumn<TProxy,TProperty> get(String property){
        return new SQLColumnImpl<>(table, property);
    }
    protected <TPropertyProxy extends SQLColumn<TProxy,TProperty>,TProperty> TPropertyProxy getValueObject(TPropertyProxy propertyProxy){
        return propertyProxy;
    }
    protected String getValueProperty(String property){
        return property;
    }

    @Override
    public TableAvailable getTable() {
        Objects.requireNonNull(table,"cant found table in sql context");
        return table;
    }

    @Override
    public TProxy create(TableAvailable table) {
        this.table=table;
        return EasyObjectUtil.typeCastNullable(this);
    }

    public SQLSelectAsExpression allFields(){
        return new SQLSelectAllImpl(getTable(),new TablePropColumn[0]);
    }
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final SQLSelectAsExpression allFieldsExclude(SQLColumn<TProxy,?>... ignorePropColumns){
        return new SQLSelectAllImpl(getTable(),ignorePropColumns);
    }

    public <T> ColumnFuncComparableExpression<T> _now(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),null, SQLFunc::now);
    }
    public <T> ColumnFuncComparableExpression<T> _utcNow(){
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getTable(),null, SQLFunc::utcNow);
    }

}
