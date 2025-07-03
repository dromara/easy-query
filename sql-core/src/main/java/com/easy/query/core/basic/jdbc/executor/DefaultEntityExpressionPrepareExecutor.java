package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.trigger.EntityExpressionTrigger;
import com.easy.query.core.trigger.TriggerTypeEnum;

import java.util.List;

/**
 * create time 2025/7/3 18:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEntityExpressionPrepareExecutor implements EntityExpressionPrepareExecutor {
    private final EntityExpressionExecutor entityExpressionExecutor;
    private final EntityExpressionTrigger entityExpressionTrigger;

    public DefaultEntityExpressionPrepareExecutor(EntityExpressionExecutor entityExpressionExecutor, EntityExpressionTrigger entityExpressionTrigger) {
        this.entityExpressionExecutor = entityExpressionExecutor;
        this.entityExpressionTrigger = entityExpressionTrigger;
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return entityExpressionExecutor.query(executorContext, resultMetadata, entityQueryExpressionBuilder);
    }

    @Override
    public <TR> JdbcResult<TR> queryStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return entityExpressionExecutor.queryStreamResultSet(executorContext, resultMetadata, entityQueryExpressionBuilder);
    }

    @Override
    public <TR> List<TR> querySQL(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters) {
        return entityExpressionExecutor.querySQL(executorContext, resultMetadata, sql, sqlParameters);
    }

    @Override
    public <TR> JdbcResult<TR> querySQLStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters) {
        return entityExpressionExecutor.querySQLStreamResultSet(executorContext, resultMetadata, sql, sqlParameters);
    }

    @Override
    public long executeSQLRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters) {
        return entityExpressionExecutor.executeSQLRows(executorContext, sql, sqlParameters);
    }

    @Override
    public <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement) {
        long rows = entityExpressionExecutor.insert(executorContext, entities, entityInsertExpressionBuilder, fillAutoIncrement);
        entityExpressionTrigger.trigger(entities.get(0).getClass(), entities, getTriggerType(executorContext.getExecuteMethod()), executorContext.getRuntimeContext());
        return rows;
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        long rows = entityExpressionExecutor.executeRows(executorContext, entityExpressionBuilder, entities);
        entityExpressionTrigger.trigger(entities.get(0).getClass(), entities, getTriggerType(executorContext.getExecuteMethod()), executorContext.getRuntimeContext());
        return rows;
    }

    @Override
    public long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder, EntityPredicateSQLExpression entityPredicateSQLExpression) {
        long rows = entityExpressionExecutor.executeRows(executorContext, entityPredicateExpressionBuilder, entityPredicateSQLExpression);
        Class<?> entityClass = entityPredicateExpressionBuilder.getTable(0).getEntityTable().getEntityClass();
        entityExpressionTrigger.trigger(entityClass, null, getTriggerType(executorContext.getExecuteMethod()), executorContext.getRuntimeContext());
        return rows;
    }

    private TriggerTypeEnum getTriggerType(ExecuteMethodEnum executeMethod) {
        switch (executeMethod) {
            case INSERT:
                return TriggerTypeEnum.INSERT;
            case UPDATE:
                return TriggerTypeEnum.UPDATE;
            case DELETE:
                return TriggerTypeEnum.DELETE;
            default:
                return TriggerTypeEnum.UNKNOWN;
        }
    }
}
