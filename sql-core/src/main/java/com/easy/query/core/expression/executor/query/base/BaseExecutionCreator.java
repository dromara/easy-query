package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;

import java.util.List;

/**
 * create time 2023/4/25 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseExecutionCreator implements ExecutionCreator{
    protected ExecutionUnit createExecutionUnit(String dataSource, EntitySQLExpression expression, List<Object> entities, boolean fillAutoIncrement){
        SQLRouteUnit sqlUnit = createSQLUnit(expression, entities, fillAutoIncrement);
        return createExecutionUnit(dataSource, sqlUnit);
    }
    protected SQLRouteUnit createSQLUnit(EntitySQLExpression expression, List<Object> entities, boolean fillAutoIncrement){
        return new SQLRouteUnit(expression,entities, fillAutoIncrement);
    }
    protected ExecutionUnit createExecutionUnit(String dataSource, SQLRouteUnit sqlUnit){
        return new  ExecutionUnit(dataSource, sqlUnit);
    }
    protected ExecutionContext createExecutionContext(List<ExecutionUnit> executionUnits){
        return new ExecutionContext(executionUnits,sequenceQuery(),isCrossTable(),isCrossDataSource(),isReverseMerge(),mergeBehavior());
    }


    protected int mergeBehavior(){
        return MergeBehaviorEnum.DEFAULT.getCode();
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
    protected boolean isReverseMerge(){
        return false;
    }
    @Override
    public ExecutionContext create() {
        List<ExecutionUnit> executionUnits = createExecutionUnits();
        return createExecutionContext(executionUnits);
    }
    protected abstract List<ExecutionUnit> createExecutionUnits();
}
