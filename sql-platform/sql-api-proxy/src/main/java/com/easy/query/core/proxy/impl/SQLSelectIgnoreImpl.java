package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * create time 2023/12/1 23:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectIgnoreImpl implements SQLSelectAsExpression {
    private final TableAvailable table;
    private final TablePropColumn[] ignoreProps;

    public SQLSelectIgnoreImpl(TableAvailable table, TablePropColumn[] ignoreProps) {
        this.table = table;

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
        if (table != null) {
            SQLBuilderSegment projects = s.getEntityQueryExpressionBuilder().getProjects();
            if (EasySQLSegmentUtil.isEmpty(projects)) {
                s.columnAll(table);
            }
        }
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
        return this.table;
    }

    @Override
    public void accept(AsSelector f) {

        if (table != null) {
            SQLBuilderSegment projects = f.getEntityQueryExpressionBuilder().getProjects();
            if (EasySQLSegmentUtil.isEmpty(projects)) {
                f.columnAll(table);
            }
        }
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
