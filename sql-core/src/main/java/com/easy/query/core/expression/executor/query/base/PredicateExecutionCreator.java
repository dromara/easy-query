package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/25 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateExecutionCreator extends BaseExecutionCreator{

    private final String dataSource;
    private final EasyEntitySqlExpression easyEntitySqlExpression;

    public PredicateExecutionCreator(String dataSource, EasyEntitySqlExpression easyEntitySqlExpression) {
        this.dataSource = dataSource;
        this.easyEntitySqlExpression = easyEntitySqlExpression;
    }

    @Override
    protected List<ExecutionUnit> createExecutionUnits() {
        ExecutionUnit executionUnit = createExecutionUnit(dataSource,easyEntitySqlExpression, null, false);
        return Collections.singletonList(executionUnit);
    }
}
