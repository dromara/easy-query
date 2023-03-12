package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetSelector;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.interceptor.GlobalInterceptorStrategy;
import com.easy.query.core.interceptor.update.GlobalUpdateInterceptorStrategy;
import com.easy.query.core.query.EasyEntityTableExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.query.SqlEntityUpdateExpression;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.query.*;
import com.easy.query.core.util.ClassUtil;

import java.util.*;

/**
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 * @Created by xuejiaming
 */
public abstract class AbstractEntityUpdatable<T> implements EntityUpdatable<T> {
    protected final List<T> entities = new ArrayList<>();
    protected final SqlEntityTableExpression table;
    protected final  EntityMetadata entityMetadata;
    protected final SqlEntityUpdateExpression sqlEntityUpdateExpression;

    public AbstractEntityUpdatable(Collection<T> entities, SqlEntityUpdateExpression sqlEntityUpdateExpression) {
        if (entities == null || entities.isEmpty()) {
            throw new EasyQueryException("不支持空对象的update");
        }
        this.entities.addAll(entities);

        Class<?> clazz = entities.iterator().next().getClass();
        this.sqlEntityUpdateExpression = sqlEntityUpdateExpression;

         entityMetadata = this.sqlEntityUpdateExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.sqlEntityUpdateExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {
            updateBefore();
            String updateSql = toSql();
            if (!StringUtil.isBlank(updateSql)) {
                EasyExecutor easyExecutor = sqlEntityUpdateExpression.getRuntimeContext().getEasyExecutor();
                return easyExecutor.executeRows(ExecutorContext.create(sqlEntityUpdateExpression.getRuntimeContext()), updateSql, entities, sqlEntityUpdateExpression.getParameters());
            }
        }
        return 0;
    }

    protected void updateBefore() {

        List<String> interceptors = entityMetadata.getUpdateInterceptors();
        boolean hasInterceptor = !interceptors.isEmpty();
        ArrayList<GlobalUpdateInterceptorStrategy> globalUpdateInterceptorStrategies = new ArrayList<>(interceptors.size());
        if (hasInterceptor) {
            for (String interceptor : interceptors) {
                GlobalInterceptorStrategy globalInterceptorStrategy = sqlEntityUpdateExpression.getRuntimeContext().getEasyQueryConfiguration().getGlobalInterceptorStrategy(interceptor);
                globalUpdateInterceptorStrategies.add((GlobalUpdateInterceptorStrategy) globalInterceptorStrategy);
            }
            for (T entity : entities) {
                for (GlobalUpdateInterceptorStrategy globalUpdateInterceptorStrategy : globalUpdateInterceptorStrategies) {
                    globalUpdateInterceptorStrategy.configure(entityMetadata.getEntityClass(), sqlEntityUpdateExpression,entity);
                }
            }
        }
    }
    @Override
    public String toSql() {
//如果没有指定where那么就使用主键作为更新条件
        if (!sqlEntityUpdateExpression.hasWhereColumns()) {
            SqlBuilderSegment whereColumns = sqlEntityUpdateExpression.getWhereColumns();
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            if (keyProperties.isEmpty()) {
                throw new EasyQueryException("对象:" + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " 未找到主键信息");
            }
            for (String keyProperty : keyProperties) {
                whereColumns.append(new ColumnPropertyPredicate(table, keyProperty, sqlEntityUpdateExpression));
            }
        }
        //如果用户没有指定set的列,那么就是set所有列,并且要去掉主键部分
        if (sqlEntityUpdateExpression.getSetColumns().isEmpty()) {
            EasyQueryRuntimeContext runtimeContext = sqlEntityUpdateExpression.getRuntimeContext();
            EasyQueryLambdaFactory easyQueryLambdaFactory = runtimeContext.getEasyQueryLambdaFactory();
            SqlExpression<SqlColumnSelector<?>> selectExpression = ColumnSelector::columnAll;
            SqlColumnSelector<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetSelector(table.getIndex(), sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns());
            selectExpression.apply(sqlColumnSetter);//获取set的值
            //非手动指定的那么需要移除where的那一部分
            List<SqlSegment> sqlSegments = sqlEntityUpdateExpression.getSetColumns().getSqlSegments();
            List<SqlSegment> whereSqlSegments = sqlEntityUpdateExpression.getWhereColumns().getSqlSegments();
            HashSet<String> whereProperties = new HashSet<>(whereSqlSegments.size());
            for (SqlSegment sqlSegment : whereSqlSegments) {
                whereProperties.add(((SqlEntitySegment) sqlSegment).getPropertyName());
            }

//                EntityMetadata entityMetadata = table.getEntityMetadata();
            sqlSegments.removeIf(o -> {
                String propertyName = ((SqlEntitySegment) o).getPropertyName();
                return whereProperties.contains(propertyName);
            });
        }
        return toInternalSql();
    }
    protected abstract String toInternalSql();

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if (rows != expectRows) {
            throw new EasyQueryConcurrentException(msg, code);
        }
    }

    @Override
    public EntityUpdatable<T> setColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if (condition) {
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> setIgnoreColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if (condition) {
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetIgnoreColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> whereColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        if (condition) {
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getWhereColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }
}
