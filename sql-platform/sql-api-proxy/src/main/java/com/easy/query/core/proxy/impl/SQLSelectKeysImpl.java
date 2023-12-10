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
 * create time 2023/12/10 14:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectKeysImpl implements SQLSelectAsExpression {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;

    public SQLSelectKeysImpl(EntitySQLContext entitySQLContext, TableAvailable table){

        this.entitySQLContext = entitySQLContext;
        this.table = table;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
    @Override
    public SQLSelectAsExpression as(TablePropColumn propColumn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Selector s) {
        s.columnKeys(table);
    }

    @Override
    public void accept(OnlySelector s) {
        s.columnKeys(table);
    }

    @Override
    public void accept(GroupSelector s) {
        s.columnKeys(table);
    }


    @Override
    public void accept(AsSelector f) {
        f.columnKeys(table);
    }

}
