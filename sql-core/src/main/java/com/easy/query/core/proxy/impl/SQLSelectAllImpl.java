package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/1 23:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectAllImpl implements SQLSelectAs {
    private final TableAvailable table;
    private final TablePropColumn[] ignoreProps;

    public SQLSelectAllImpl(TableAvailable table, TablePropColumn[] ignoreProps) {

        this.table = table;
        this.ignoreProps = ignoreProps;
    }

    @Override
    public String value() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLSelectAs as(TablePropColumn propColumn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Selector f) {
        f.columnAll(table);
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (TablePropColumn ignoreProp : ignoreProps) {
                f.columnIgnore(ignoreProp.getTable(), ignoreProp.value());
            }
        }
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public void accept(AsSelector f) {
        f.columnAll(table);
        if (EasyArrayUtil.isNotEmpty(ignoreProps)) {
            for (TablePropColumn ignoreProp : ignoreProps) {
                f.columnIgnore(ignoreProp.getTable(), ignoreProp.value());
            }
        }
    }
}
