package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnConstSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnConstSegmentImpl implements ColumnConstSegment {
    protected final TableAvailable table;
    protected final QueryRuntimeContext runtimeContext;
    protected final String columnConst;

    public ColumnConstSegmentImpl(TableAvailable table, QueryRuntimeContext runtimeContext, String columnConst){
        this.table = table;
        this.runtimeContext = runtimeContext;
        this.columnConst = columnConst;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public SQLEntitySegment cloneSQLEntitySegment() {
        return new ColumnConstSegmentImpl(table,runtimeContext,columnConst);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return columnConst;
    }
}
