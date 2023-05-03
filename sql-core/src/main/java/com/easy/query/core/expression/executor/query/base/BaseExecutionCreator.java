package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.basic.jdbc.parameter.DefaultSqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SqlUnit;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/25 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseExecutionCreator implements ExecutionCreator{
    protected ExecutionUnit createExecutionUnit(String dataSource, EasySqlExpression expression, List<Object> entities, boolean fillAutoIncrement){
        SqlUnit sqlUnit = createSqlUnit(expression, entities, fillAutoIncrement);
        return createExecutionUnit(dataSource, sqlUnit);
    }
    protected SqlUnit createSqlUnit(EasySqlExpression expression, List<Object> entities, boolean fillAutoIncrement){
        SqlParameterCollector sqlParameterCollector = DefaultSqlParameterCollector.defaultCollector();
        String sql = expression.toSql(sqlParameterCollector);
        return new SqlUnit(sql, sqlParameterCollector.getParameters(),entities, fillAutoIncrement);
    }
    protected ExecutionUnit createExecutionUnit(String dataSource,SqlUnit sqlUnit){
        return new  ExecutionUnit(dataSource, sqlUnit);
    }
    protected ExecutionContext createExecutionContext(Collection<ExecutionUnit> executionUnits){
        return new ExecutionContext(executionUnits);
    }

    @Override
    public ExecutionContext create() {
        Collection<ExecutionUnit> executionUnits = createExecutionUnits();
        return createExecutionContext(executionUnits);
    }
    protected abstract Collection<ExecutionUnit> createExecutionUnits();
}
