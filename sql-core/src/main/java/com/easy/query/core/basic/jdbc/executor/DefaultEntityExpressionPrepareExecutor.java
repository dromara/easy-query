package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.trigger.EntityExpressionTrigger;
import com.easy.query.core.trigger.TriggerTypeEnum;
import com.easy.query.core.util.EasyCollectionUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
        if (entityExpressionTrigger.hasListener()) {
            Class<?> entityClass = entities.get(0).getClass();
            if(entityExpressionTrigger.support(entityClass)){
                entityExpressionTrigger.trigger(entityClass, entities, getTriggerType(executorContext.getExecuteMethod()), executorContext.getCreateTime(), executorContext.getRuntimeContext());
            }
        }
        return rows;
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        long rows = entityExpressionExecutor.executeRows(executorContext, entityExpressionBuilder, entities);
        if (entityExpressionTrigger.hasListener()) {
            Class<?> entityClass = entities.get(0).getClass();
            if(entityExpressionTrigger.support(entityClass)){
                entityExpressionTrigger.trigger(entityClass, entities, getTriggerType(executorContext.getExecuteMethod()), executorContext.getCreateTime(), executorContext.getRuntimeContext());
            }
        }
        return rows;
    }

    @Override
    public long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder, EntityPredicateSQLExpression entityPredicateSQLExpression) {
        long rows = entityExpressionExecutor.executeRows(executorContext, entityPredicateExpressionBuilder, entityPredicateSQLExpression);
        if (entityExpressionTrigger.hasListener()) {
            Class<?> entityClass = entityPredicateExpressionBuilder.getTable(0).getEntityTable().getEntityClass();
            if (entityExpressionTrigger.support(entityClass)) {
                //尝试从表达式中获取实体
                List<?> singleKeyEntities = getSingleKeyEntities(rows, entityPredicateSQLExpression);
                entityExpressionTrigger.trigger(entityClass, singleKeyEntities, getTriggerType(executorContext.getExecuteMethod()), executorContext.getCreateTime(), executorContext.getRuntimeContext());
            }
        }
        return rows;
    }

    /**
     * 获取表达式内对象如果是单个主键模式的情况下获取表达式的eq和in的主键值，然后转成对象给trigger
     * @param rows
     * @param expression
     * @return
     */
    @Nullable
    private List<?> getSingleKeyEntities(long rows, EntityPredicateSQLExpression expression) {
        if (rows <= 0) {
            return null;
        }

        TableAvailable fromTable = expression.getTables().get(0).getEntityTable();
        EntityMetadata entityMetadata = fromTable.getEntityMetadata();
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            return null;
        }
        String keyProperty = EasyCollectionUtil.first(keyProperties);
        PredicateSegment where = expression.getWhere();
        Set<Object> keyValues = new HashSet<>();

        List<Predicate> flatAndPredicates = where.getFlatAndPredicates();
        for (Predicate predicate : flatAndPredicates) {

            if (predicate.getTable() == fromTable &&
                    (predicate.getOperator() == SQLPredicateCompareEnum.EQ || predicate.getOperator() == SQLPredicateCompareEnum.IN)) {
                if (Objects.equals(keyProperty, predicate.getPropertyName())) {
                    if (predicate.getOperator() == SQLPredicateCompareEnum.EQ) {
                        if (predicate instanceof ValuePredicate) {
                            ValuePredicate valuePredicate = (ValuePredicate) predicate;
                            SQLParameter parameter = valuePredicate.getParameter();
                            if (parameter instanceof ConstSQLParameter) {
                                Object value = parameter.getValue();
                                keyValues.add(value);
                            }
                        }
                    } else if (predicate.getOperator() == SQLPredicateCompareEnum.IN) {
                        if (predicate instanceof ValuesPredicate) {
                            ValuesPredicate valuesPredicate = (ValuesPredicate) predicate;
                            Collection<SQLParameter> parameters = valuesPredicate.getParameters();
                            for (SQLParameter parameter : parameters) {
                                if (parameter instanceof ConstSQLParameter) {
                                    Object value = parameter.getValue();
                                    keyValues.add(value);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (EasyCollectionUtil.isNotEmpty(keyValues)) {
            ArrayList<Object> entities = new ArrayList<>();
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(keyProperty);
            for (Object keyValue : keyValues) {
                Object entity = entityMetadata.getBeanConstructorCreator().get();
                columnMetadata.getSetterCaller().call(entity, keyValue);
                entities.add(entity);
            }
            return entities;

        }
        return null;
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
