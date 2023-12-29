package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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
public class SQLSelectIgnoreImpl implements SQLSelectAsExpression {
    private final TablePropColumn[] ignoreProps;

    public SQLSelectIgnoreImpl(TablePropColumn[] ignoreProps) {

        this.ignoreProps = ignoreProps;
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLSelectAsExpression as(TablePropColumn propColumn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Selector s) {
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (TablePropColumn ignoreProp : ignoreProps) {
                s.columnIgnore(ignoreProp.getTable(), ignoreProp.getValue());
            }
        }
    }

    @Override
    public void accept(OnlySelector s) {
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (TablePropColumn ignoreProp : ignoreProps) {
                s.columnIgnore(ignoreProp.getTable(), ignoreProp.getValue());
            }
        }
    }

    @Override
    public void accept(GroupSelector s) {
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (TablePropColumn ignoreProp : ignoreProps) {
                s.columnIgnore(ignoreProp.getTable(), ignoreProp.getValue());
            }
        }
    }

    @Override
    public TableAvailable getTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(AsSelector f) {
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (TablePropColumn ignoreProp : ignoreProps) {
                f.columnIgnore(ignoreProp.getTable(), ignoreProp.getValue());
            }
        }
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        throw new UnsupportedOperationException();
    }
}
