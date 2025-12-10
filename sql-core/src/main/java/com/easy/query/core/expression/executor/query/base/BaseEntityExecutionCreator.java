package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/25 21:35
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseEntityExecutionCreator extends BaseExecutionCreator {
    protected final String dataSource;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final List<Object> entities;
    private final ExecutorContext executorContext;

    public BaseEntityExecutionCreator(String dataSource, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities, ExecutorContext executorContext) {

        this.dataSource = dataSource;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.entities = entities;
        this.executorContext = executorContext;
    }
    @Override
    protected List<ExecutionUnit> createExecutionUnits() {
        return createSingleExecutionUnits();
    }

    protected List<ExecutionUnit> createSingleExecutionUnits() {
        List<ExecutionUnit> routeExecutionUnits = new ArrayList<>(entities.size());
        for (Object entity : entities) {
            EntitySQLExpression expression = createEasySQLExpression(entity);
            //todo 根据sql聚合或者根据sql顺序聚合
            ExecutionUnit executionUnit = createExecutionUnit(dataSource, expression, Collections.singletonList(entity), getFillAutoIncrement(), null);
            //开启追踪的情况下update可能没有可以更新的数据那么就不会生成sql
            if (EasyStringUtil.isNotBlank(executionUnit.getSQLRouteUnit().getSQL())) {
                routeExecutionUnits.add(executionUnit);
            }
        }
        return createBatchExecutionUnits(routeExecutionUnits);
    }

    @Override
    protected boolean useEntityBatch() {

        if (entityExpressionBuilder.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.EXECUTE_NO_BATCH)) {
            return false;
        }
        return entityExpressionBuilder.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.EXECUTE_BATCH);
    }

    protected abstract EntitySQLExpression createEasySQLExpression(Object entity);

    protected abstract boolean getFillAutoIncrement();
}
