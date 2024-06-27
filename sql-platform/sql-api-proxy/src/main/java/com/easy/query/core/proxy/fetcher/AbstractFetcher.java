package com.easy.query.core.proxy.fetcher;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;

import java.util.Collection;

/**
 * create time 2023/12/6 23:18
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractFetcher<TProxy extends AbstractProxyEntity<TProxy, TEntity>, TEntity, TChain extends AbstractFetcher<TProxy, TEntity, TChain>> implements EntityFetcher<TProxy, TEntity, TChain> {


    private final TProxy tProxy;
    private final AbstractFetcher<TProxy, TEntity, TChain> prev;
    private SQLSelectAsExpression sqlSelectAsExpression;

    public AbstractFetcher(TProxy tProxy, AbstractFetcher<TProxy, TEntity, TChain> prev, SQLSelectAsExpression sqlSelectAsExpression) {

        this.tProxy = tProxy;
        this.prev = prev;
        this.sqlSelectAsExpression = sqlSelectAsExpression;
    }

    @Override
    public TProxy getProxy() {
        return tProxy;
    }

    @Override
    public TableAvailable getTable() {
        return tProxy.getTable();
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return tProxy.getEntitySQLContext();
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TChain allFields() {
        SQLSelectAsExpression sqlSelectAsExpression = new SQLSelectAllImpl(tProxy.getEntitySQLContext(), tProxy.getTable(), null);
        return createFetcher(tProxy, this, sqlSelectAsExpression);
    }


    @Override
    public TChain allFieldsExclude(Collection<SQLColumn<TProxy, ?>> ignoreColumns) {
        SQLSelectAsExpression sqlSelectAsExpression = new SQLSelectAllImpl(tProxy.getEntitySQLContext(), tProxy.getTable(), ignoreColumns.stream().toArray(SQLColumn[]::new));
        return createFetcher(tProxy, this, sqlSelectAsExpression);
    }

    @Override
    public TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, Collection<SQLColumn<TProxy, ?>> ignoreColumns) {
        SQLSelectAsExpression columnWithout = SQLSelectAsExpression.createColumnExclude(column, ignoreColumns);
        return createFetcher(tProxy, this, columnWithout);
    }

    //    protected TChain createFetcher(TProxy tProxy, AbstractFetcher<TProxy, TEntity, TChain> prev, TableAvailable table,String property){
//        SQLSelectAsExpression selectAsExpression = SQLSelectAsExpression.createDefault(table, property);
//        return createFetcher(tProxy, this, selectAsExpression);
//    }
    protected abstract TChain createFetcher(TProxy tProxy, AbstractFetcher<TProxy, TEntity, TChain> prev, SQLSelectAsExpression sqlSelectExpression);

    protected TChain add(SQLColumn<TProxy, ?> sqlColumn) {
        SQLSelectAsExpression selectAsExpression = SQLSelectAsExpression.createDefault(getProxy().getEntitySQLContext(), getProxy().getTable(), sqlColumn.getValue());
        return createFetcher(tProxy, this, selectAsExpression);
    }

    public TChain columnKeys() {
        SQLSelectAsExpression keys = tProxy.columnKeys();
        return createFetcher(tProxy, this, keys);
    }

    @Override
    public void accept(AsSelector s) {
        acceptAsSelector(s);
    }

    protected void acceptAsSelector(AsSelector s) {
        if (prev != null) {
            prev.acceptAsSelector(s);
            sqlSelectAsExpression.accept(s);
        }
    }

    @Override
    public void accept(Selector s) {
        acceptSelector(s);
    }

    protected void acceptSelector(Selector s) {
        if (prev != null) {
            prev.acceptSelector(s);
            sqlSelectAsExpression.accept(s);
        }
    }

    @Override
    public void accept(GroupSelector s) {
        acceptGroupSelector(s);
    }

    protected void acceptGroupSelector(GroupSelector s) {
        if (prev != null) {
            prev.acceptGroupSelector(s);
            sqlSelectAsExpression.accept(s);
        }
    }

    @Override
    public void accept(OnlySelector s) {
        acceptOnlySelector(s);
    }

    protected void acceptOnlySelector(OnlySelector s) {
        if (prev != null) {
            prev.acceptOnlySelector(s);
            sqlSelectAsExpression.accept(s);
        }
    }

    @Override
    public TChain as(String propertyAlias) {
        sqlSelectAsExpression = sqlSelectAsExpression.as(propertyAlias);
        return (TChain) this;
    }

    @Override
    public <TEntity, TR> TChain as(Property<TEntity, TR> propertyAlias) {
        sqlSelectAsExpression = sqlSelectAsExpression.as(propertyAlias);
        return (TChain) this;
    }

    @Override
    public TProxy fetchProxy() {
        TProxy proxy = tProxy.create(null, tProxy.getEntitySQLContext());
        proxy.selectExpression(this);
        return proxy;
    }
}
