package com.easy.query.core.expression.segment;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @author xuejiaming
 */
public class FuncColumnSegmentImpl implements AggregationColumnSegment {


    protected final TableAvailable table;
    protected final String propertyName;
    protected final EasyQueryRuntimeContext runtimeContext;
    protected final ColumnFunction columnFunction;
    protected String alias;

    public FuncColumnSegmentImpl(TableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext, ColumnFunction columnFunction){
        this(table,propertyName,runtimeContext,columnFunction,null);
    }
    public FuncColumnSegmentImpl(TableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext, ColumnFunction columnFunction, String alias){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.columnFunction = columnFunction;
        this.alias = alias;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        String funcColumn = columnFunction.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(alias!=null){
            sql.append(" AS ").append(SqlExpressionUtil.getQuoteName(runtimeContext,alias));
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
    public AggregationColumnSegment cloneSqlEntitySegment() {
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
