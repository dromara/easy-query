package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @author xuejiaming
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 */
public class FuncColumnSegmentImpl implements FuncColumnSegment {


    protected final TableAvailable table;
    protected final ColumnMetadata columnMetadata;
    protected final ExpressionContext expressionContext;
    protected final ColumnFunction columnFunction;
    protected String alias;

    public FuncColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, ColumnFunction columnFunction, String alias) {
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.expressionContext = expressionContext;
        this.columnFunction = columnFunction;
        this.alias = alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext, true, false);
        String funcColumn = columnFunction.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        String alias = getAlias();
        if (alias != null) {
            sql.append(" AS ").append(EasySQLExpressionUtil.getQuoteName(expressionContext.getRuntimeContext(), alias));
        }
        return sql.toString();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return columnMetadata.getPropertyName();
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    @Override
    public FuncColumnSegment cloneSQLColumnSegment() {
        return new FuncColumnSegmentImpl(this.table, this.columnMetadata, this.expressionContext, this.columnFunction, this.alias);
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias=alias;
    }

    @Override
    public AggregationType getAggregationType() {
        return columnFunction.getAggregationType();
    }
}
