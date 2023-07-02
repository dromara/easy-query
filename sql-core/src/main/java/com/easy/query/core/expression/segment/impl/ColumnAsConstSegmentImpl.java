package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnAsConstSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnAsConstSegmentImpl extends ColumnConstSegmentImpl implements ColumnAsConstSegment {
    protected final String alias;

    public ColumnAsConstSegmentImpl(TableAvailable table, QueryRuntimeContext runtimeContext, String columnConst, String alias) {
        super(table, runtimeContext, columnConst);
        this.alias = alias;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new ColumnAsConstSegmentImpl(table,runtimeContext,columnConst,alias);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return columnConst+" AS "+alias;
    }
}
