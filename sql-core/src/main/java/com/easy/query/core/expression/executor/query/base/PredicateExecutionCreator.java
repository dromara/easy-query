package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;

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
    private final EntitySQLExpression easyEntitySQLExpression;

    public PredicateExecutionCreator(String dataSource, EntitySQLExpression easyEntitySQLExpression) {
        this.dataSource = dataSource;
        this.easyEntitySQLExpression = easyEntitySQLExpression;
    }

    @Override
    protected List<ExecutionUnit> createExecutionUnits() {
        ExecutionUnit executionUnit = createExecutionUnit(dataSource, easyEntitySQLExpression, null, false,null);
        return Collections.singletonList(executionUnit);
    }
}
