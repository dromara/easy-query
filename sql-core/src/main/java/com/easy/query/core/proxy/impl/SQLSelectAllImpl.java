package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/1 23:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectAllImpl implements SQLSelectAsExpression {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final PropColumn[] ignoreProps;

    public SQLSelectAllImpl(EntitySQLContext entitySQLContext,TableAvailable table, PropColumn[] ignoreProps) {
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.ignoreProps = ignoreProps;
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLSelectAsExpression alias(TablePropColumn propColumn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Selector s) {
        s.columnAll(table);
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (PropColumn ignoreProp : ignoreProps) {
                s.columnIgnore(table, ignoreProp.getValue());
            }
        }
    }

    @Override
    public void accept(OnlySelector s) {
        s.columnAll(table);
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (PropColumn ignoreProp : ignoreProps) {
                s.columnIgnore(table, ignoreProp.getValue());
            }
        }
    }

    @Override
    public void accept(GroupSelector s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public void accept(AsSelector f) {
        f.columnAll(table);
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (PropColumn ignoreProp : ignoreProps) {
                f.columnIgnore(table, ignoreProp.getValue());
            }
        }
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
}
