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
public class SQLSelectAs2Impl implements SQLSelectAsExpression {

    private final SQLSelectAsExpression sqlPreSelectAsExpression;
    private final SQLSelectAsExpression sqlNextSelectAsExpression;
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final  String property;

    public SQLSelectAs2Impl(SQLSelectAsExpression sqlPreSelectAsExpression,SQLSelectAsExpression sqlNextSelectAsExpression) {
        this(sqlPreSelectAsExpression,sqlNextSelectAsExpression,null,null,null);
    }

    public SQLSelectAs2Impl(SQLSelectAsExpression sqlPreSelectAsExpression,SQLSelectAsExpression sqlNextSelectAsExpression, EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.sqlPreSelectAsExpression = sqlPreSelectAsExpression;
        this.sqlNextSelectAsExpression = sqlNextSelectAsExpression;

        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
    }

    @Override
    public void accept(GroupSelector s) {
        sqlPreSelectAsExpression.accept(s);
        sqlNextSelectAsExpression.accept(s);
    }

    @Override
    public void accept(AsSelector s) {
        sqlPreSelectAsExpression.accept(s);
        sqlNextSelectAsExpression.accept(s);
    }

    @Override
    public void accept(Selector s) {
        sqlPreSelectAsExpression.accept(s);
        sqlNextSelectAsExpression.accept(s);
    }

    @Override
    public void accept(OnlySelector s) {
        sqlPreSelectAsExpression.accept(s);
        sqlNextSelectAsExpression.accept(s);
    }

    @Override
    public String getValue() {
        if(property==null){
            throw new UnsupportedOperationException();
        }
        return property;
    }


    @Override
    public SQLSelectAsExpression as(TablePropColumn propColumn) {
        return as(propColumn.getValue());
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            s.columnAs(this.getTable(), this.getValue(), propertyAlias);
        }, s -> {
            s.columnAs(this.getTable(), this.getValue(), propertyAlias);
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    public TableAvailable getTable() {
        if(table==null){
            throw new UnsupportedOperationException();
        }
        return table;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
}
