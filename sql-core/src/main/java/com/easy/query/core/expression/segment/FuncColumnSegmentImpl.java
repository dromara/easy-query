package com.easy.query.core.expression.segment;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @author xuejiaming
 */
public class FuncColumnSegmentImpl implements AggregationColumnSegment {


    protected final TableAvailable table;
    protected final String propertyName;
    protected final QueryRuntimeContext runtimeContext;
    protected final ColumnFunction columnFunction;
    protected String alias;

    public FuncColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction){
        this(table,propertyName,runtimeContext,columnFunction,null);
    }
    public FuncColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, ColumnFunction columnFunction, String alias){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.columnFunction = columnFunction;
        this.alias = alias;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {

        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
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
        return propertyName;
    }

    @Override
    public AggregationColumnSegment cloneSQLEntitySegment() {
        throw new UnsupportedOperationException();
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
