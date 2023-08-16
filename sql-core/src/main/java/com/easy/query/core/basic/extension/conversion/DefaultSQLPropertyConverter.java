package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContextImpl;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;

import java.util.Objects;

/**
 * create time 2023/8/8 16:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLPropertyConverter implements SQLPropertyConverter {
    private final TableAvailable table;
    private final QueryRuntimeContext runtimeContext;
    private final boolean ignoreAlias;
    private SQLNativeSegment columnSegment;

    public DefaultSQLPropertyConverter(TableAvailable table, QueryRuntimeContext runtimeContext) {
        this(table, runtimeContext, false);
    }

    public DefaultSQLPropertyConverter(TableAvailable table, QueryRuntimeContext runtimeContext, boolean ignoreAlias) {

        this.table = table;
        this.runtimeContext = runtimeContext;
        this.ignoreAlias = ignoreAlias;
    }

    @Override
    public void sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativePropertyExpressionContext> contextConsume) {
        Objects.requireNonNull(sqlSegment, "sqlSegment can not be null");
        Objects.requireNonNull(contextConsume, "contextConsume can not be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(null);
        SQLNativePropertyExpressionContextImpl sqlNativePropertyExpressionContext = new SQLNativePropertyExpressionContextImpl(table, sqlNativeExpressionContext);
        contextConsume.apply(sqlNativePropertyExpressionContext);
        if (ignoreAlias) {
            sqlNativeExpressionContext.setAlias(null);
        }
        this.columnSegment = runtimeContext.getSQLSegmentFactory().createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
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
