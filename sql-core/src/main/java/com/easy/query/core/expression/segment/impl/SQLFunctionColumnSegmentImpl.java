package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/10/18 16:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFunctionColumnSegmentImpl implements FuncColumnSegment {

    protected final TableAvailable table;
    protected final ColumnMetadata columnMetadata;
    protected final QueryRuntimeContext runtimeContext;
    private final SQLSegment sqlSegment;
    private final AggregationType aggregationType;
    protected String alias;

    public SQLFunctionColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, SQLSegment sqlSegment,AggregationType aggregationType, String alias){
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.runtimeContext = runtimeContext;
        this.sqlSegment = sqlSegment;
        this.aggregationType = aggregationType;
        this.alias = alias;
    }
    @Override
    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    @Override
    public FuncColumnSegment cloneSQLColumnSegment() {
        return new SQLFunctionColumnSegmentImpl(table,columnMetadata,runtimeContext,sqlSegment,aggregationType,alias);
    }

    @Override
    public AggregationType getAggregationType() {
        return aggregationType;
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
    public String toSQL(ToSQLContext toSQLContext) {
        return sqlSegment.toSQL(toSQLContext);
    }

    @Override
    public String getPropertyName() {
        return columnMetadata.getPropertyName();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
