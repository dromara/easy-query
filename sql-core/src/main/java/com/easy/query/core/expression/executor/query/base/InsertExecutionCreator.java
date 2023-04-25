package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.util.SqlExpressionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/25 08:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertExecutionCreator extends BaseExecutionCreator {
    private final String dataSource;
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    private final List<Object> entities;
    private final boolean fillAutoIncrement;

    public InsertExecutionCreator(String dataSource, EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement) {
        this.dataSource = dataSource;
        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
        this.entities = entities;
        this.fillAutoIncrement = fillAutoIncrement;
    }

    @Override
    protected Collection<ExecutionUnit> createExecutionUnits() {
        //是否单个对象运行sql
        boolean isSingleEntityRun = SqlExpressionUtil.sqlExecuteStrategyNonDefault(entityInsertExpressionBuilder.getExpressionContext());
        if (isSingleEntityRun) {
            return createSingleExecutionUnits();
        }

        return createMultiExecutionUnits();
    }

    private Collection<ExecutionUnit> createSingleExecutionUnits() {
        List<ExecutionUnit> executionUnits = new ArrayList<>(entities.size());
        for (Object entity : entities) {
            EasyInsertSqlExpression expression = entityInsertExpressionBuilder.toExpression(entity);
            ExecutionUnit executionUnit = createExecutionUnit(dataSource, expression, Collections.singletonList(entity), fillAutoIncrement);
            executionUnits.add(executionUnit);
        }
        return executionUnits;
    }
    private Collection<ExecutionUnit> createMultiExecutionUnits() {
        EasyInsertSqlExpression expression = entityInsertExpressionBuilder.toExpression(null);
        ExecutionUnit executionUnit = createExecutionUnit(dataSource, expression, entities, fillAutoIncrement);
        return Collections.singletonList(executionUnit);
    }
}
