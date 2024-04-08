package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/6/16 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousColumnSegmentImpl extends ColumnSegmentImpl{
    public AnonymousColumnSegmentImpl(TableAvailable table, ExpressionContext expressionContext, String alias) {
        super(table, null, expressionContext,alias);
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(expressionContext.getRuntimeContext(),table,alias,toSQLContext);
        return sqlColumnSegment +
                " AS " + EasySQLExpressionUtil.getQuoteName(expressionContext.getRuntimeContext(), alias);
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new AnonymousColumnSegmentImpl(table,expressionContext,alias);
    }
}
