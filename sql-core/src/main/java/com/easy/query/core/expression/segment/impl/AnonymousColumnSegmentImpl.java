package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/6/16 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousColumnSegmentImpl extends ColumnSegmentImpl{
    public AnonymousColumnSegmentImpl(TableAvailable table, QueryRuntimeContext runtimeContext, String alias) {
        super(table, null, runtimeContext,alias);
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,alias,toSQLContext);
        return sqlColumnSegment +
                " AS " + EasySQLExpressionUtil.getQuoteName(runtimeContext, alias);
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new AnonymousColumnSegmentImpl(table,runtimeContext,alias);
    }
}
