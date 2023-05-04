package com.easy.query.core.expression.segment;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.sharding.merge.result.aggregation.AggregationType;
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
    protected final EasyFunc easyFunc;
    protected String alias;

    public FuncColumnSegmentImpl(TableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext, EasyFunc easyFunc){
        this(table,propertyName,runtimeContext,easyFunc,null);
    }
    public FuncColumnSegmentImpl(TableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext, EasyFunc easyFunc, String alias){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
        this.easyFunc = easyFunc;
        this.alias = alias;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        String funcColumn = easyFunc.getFuncColumn(sqlColumnSegment);
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
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public AggregationType getAggregationType() {
        return easyFunc.getAggregationType();
    }
}
