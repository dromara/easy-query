package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.basic.jdbc.parameter.DefaultSqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SqlRouteUnit;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/25 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseExecutionCreator implements ExecutionCreator{
    protected ExecutionUnit createExecutionUnit(String dataSource,int index, EasyEntitySqlExpression expression, List<Object> entities, boolean fillAutoIncrement){
        SqlRouteUnit sqlUnit = createSqlUnit(expression, entities, fillAutoIncrement);
        return createExecutionUnit(dataSource,index, sqlUnit);
    }
    protected SqlRouteUnit createSqlUnit(EasyEntitySqlExpression expression, List<Object> entities, boolean fillAutoIncrement){
        return new SqlRouteUnit(expression,entities, fillAutoIncrement);
    }
    protected ExecutionUnit createExecutionUnit(String dataSource, int index, SqlRouteUnit sqlUnit){
        return new  ExecutionUnit(dataSource, index,sqlUnit);
    }
    protected ExecutionContext createExecutionContext(Collection<ExecutionUnit> executionUnits){
        return new ExecutionContext(executionUnits,sequenceQuery(),isCrossTable(),isCrossDataSource());
    }


    protected boolean sequenceQuery(){
        return false;
    }
    protected boolean isCrossTable(){
        return false;
    }
    protected boolean isCrossDataSource(){
        return false;
    }
    @Override
    public ExecutionContext create() {
        Collection<ExecutionUnit> executionUnits = createExecutionUnits();
        return createExecutionContext(executionUnits);
    }
    protected abstract Collection<ExecutionUnit> createExecutionUnits();
}
