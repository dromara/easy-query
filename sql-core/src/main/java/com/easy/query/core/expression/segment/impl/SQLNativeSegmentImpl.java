package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeSegmentImpl extends AbstractSQLNativeSegmentImpl implements SQLNativeSegment {
    protected final String sqlSegment;
    protected final SQLNativeExpression sqlNativeExpression;
    private String alias;

    public SQLNativeSegmentImpl(ExpressionContext expressionContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(expressionContext,sqlSegment,sqlNativeExpression);
        this.sqlSegment = sqlSegment;
        this.sqlNativeExpression = sqlNativeExpression;
        this.alias=sqlNativeExpression.getAlias();
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias=alias;
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new SQLNativeSegmentImpl(expressionContext, sqlSegment, sqlNativeExpression);
    }

    public ColumnMetadata getColumnMetadata(){
        return null;
    }
}
