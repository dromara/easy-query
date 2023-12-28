package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLColumnSetExpression;

/**
 * create time 2023/12/27 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetColumnImpl implements SQLColumnSetExpression {
    private final TableAvailable table;
    private final String property;
    private final SQLColumn<?, ?> sqlColumn;

    public SQLColumnSetColumnImpl(TableAvailable table, String property, SQLColumn<?,?> sqlColumn){
        this.table = table;
        this.property = property;
        this.sqlColumn = sqlColumn;
    }

    @Override
    public void accept(Setter s) {
        if(table!=sqlColumn.getTable()){
            throw new UnsupportedOperationException();
        }
        s.setWithColumn(table,property,sqlColumn.getValue());
    }

    @Override
    public void accept(Selector s) {
        s.columnAs(sqlColumn.getTable(),sqlColumn.getValue(),property);
    }

    @Override
    public void accept(AsSelector s) {
        s.columnAs(sqlColumn.getTable(),sqlColumn.getValue(),property);
    }
}
