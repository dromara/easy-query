package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContextImpl;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.Objects;

/**
 * create time 2023/8/8 16:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLPropertyConverter implements SQLPropertyConverter {
    private final TableAvailable table;
    private final ExpressionContext expressionContext;
    private final boolean ignoreAlias;
    private SQLNativeSegment columnSegment;

    public DefaultSQLPropertyConverter(TableAvailable table,ExpressionContext expressionContext) {
        this(table,expressionContext, false);
    }

    public DefaultSQLPropertyConverter(TableAvailable table, ExpressionContext expressionContext, boolean ignoreAlias) {

        this.table = table;
        this.expressionContext = expressionContext;
        this.ignoreAlias = ignoreAlias;
    }

    @Override
    public SQLNativeSegment getColumnSegment() {
        return columnSegment;
    }


    @Override
    public void sqlNativeSegment(String sqlSegment, SQLActionExpression1<SQLNativePropertyExpressionContext> contextConsume) {
        Objects.requireNonNull(sqlSegment, "sqlSegment can not be null");
        Objects.requireNonNull(contextConsume, "sqlExpression can not be null");
//        this.sqlFunction = expressionContext.getRuntimeContext().fx().anySQLFunction(sqlSegment, sqlExpression);
//
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext,expressionContext.getRuntimeContext());
        SQLNativePropertyExpressionContextImpl sqlNativePropertyExpressionContext = new SQLNativePropertyExpressionContextImpl(table, sqlNativeExpressionContext);
        contextConsume.apply(sqlNativePropertyExpressionContext);

        if (ignoreAlias) {
            sqlNativeExpressionContext.setAlias(null);
        }
        this.columnSegment = expressionContext.getRuntimeContext().getSQLSegmentFactory().createSQLNativeSegment(expressionContext, sqlSegment, sqlNativeExpressionContext);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        Objects.requireNonNull(columnSegment, "columnSegment can not be null");
        return columnSegment.toSQL(toSQLContext);
    }
}
