package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @author xuejiaming
 */
public class FuncColumnSegmentImpl implements FuncColumnSegment {


    protected final TableAvailable table;
    protected final ColumnMetadata columnMetadata;
    protected final QueryRuntimeContext runtimeContext;
    protected final ColumnFunction columnFunction;
    protected String alias;

    public FuncColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, String alias){
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.runtimeContext = runtimeContext;
        this.columnFunction = columnFunction;
        this.alias = alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext,table,columnMetadata,toSQLContext,true,false);
        String funcColumn = columnFunction.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(alias!=null){
            sql.append(" AS ").append(EasySQLExpressionUtil.getQuoteName(runtimeContext,alias));
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
        return new FuncColumnSegmentImpl(this.table,this.columnMetadata,this.runtimeContext,this.columnFunction,this.alias);
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public AggregationType getAggregationType() {
        return columnFunction.getAggregationType();
    }
}
