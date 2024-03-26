package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/12/1 23:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectAsEntryImpl implements SQLSelectAsExpression {

    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;
    private final String alias;


    public SQLSelectAsEntryImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this(entitySQLContext, table, property, null);
    }

    public SQLSelectAsEntryImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, String alias) {
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
        this.alias = alias;
    }

    @Override
    public void accept(GroupSelector s) {
        s.column(table,property);
    }

    @Override
    public void accept(AsSelector s) {
        if(alias==null){
            s.column(table,property);
        }else{
            s.columnAs(table,property,alias);
        }
    }

    @Override
    public void accept(Selector s) {
        s.column(table,property);
    }

    @Override
    public void accept(OnlySelector s) {
        s.column(table,property);
    }

    @Override
    public String getValue() {
        return property;
    }


    @Override
    public SQLSelectAsExpression as(TablePropColumn propColumn) {
        return as(propColumn.getValue());
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsEntryImpl(entitySQLContext,table,property,propertyAlias);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
}
