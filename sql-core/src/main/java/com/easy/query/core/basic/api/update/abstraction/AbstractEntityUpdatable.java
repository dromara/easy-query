package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.abstraction.AbstractSqlExecuteRows;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.UpdateEntityNode;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.UpdateStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetSelector;
import com.easy.query.core.interceptor.GlobalEntityInterceptor;
import com.easy.query.core.interceptor.GlobalInterceptor;
import com.easy.query.core.query.EasyEntityTableExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.query.SqlEntityUpdateExpression;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.abstraction.metadata.EntityMetadata;

import java.util.*;

/**
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 * @Created by xuejiaming
 */
public abstract class AbstractEntityUpdatable<T> extends AbstractSqlExecuteRows implements EntityUpdatable<T> {
    protected final List<T> entities = new ArrayList<>();
    protected final SqlEntityTableExpression table;
    protected final  EntityMetadata entityMetadata;
    protected final SqlEntityUpdateExpression sqlEntityUpdateExpression;

    public AbstractEntityUpdatable(Collection<T> entities, SqlEntityUpdateExpression sqlEntityUpdateExpression) {
        super(sqlEntityUpdateExpression);
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
            Map<String, UpdateEntityNode> updateEntityNodeMap = new LinkedHashMap<>();
            for (T entity : entities) {
                String updateSql = toSql(entity);
                List<SQLParameter> parameters = new ArrayList<>(sqlEntityUpdateExpression.getParameters());
                UpdateEntityNode updateEntityNode = updateEntityNodeMap.computeIfAbsent(updateSql, k -> new UpdateEntityNode(updateSql, parameters));
                updateEntityNode.getEntities().add(entity);
            }
            EasyExecutor easyExecutor = sqlEntityUpdateExpression.getRuntimeContext().getEasyExecutor();
            int i=0;
            for (String updateSql : updateEntityNodeMap.keySet()) {
                UpdateEntityNode updateEntityNode = updateEntityNodeMap.get(updateSql);
                i+= easyExecutor.executeRows(ExecutorContext.create(sqlEntityUpdateExpression.getRuntimeContext()), updateSql, updateEntityNode.getEntities(), updateEntityNode.getSqlParameters());
            }
            return i;
        }
        return 0;
    }

    protected void updateBefore() {

        List<String> interceptors = entityMetadata.getEntityInterceptors();
        boolean hasInterceptor = !interceptors.isEmpty();
        ArrayList<GlobalEntityInterceptor> globalEntityInterceptors = new ArrayList<>(interceptors.size());
        if (hasInterceptor) {
            for (String interceptor : interceptors) {
                GlobalInterceptor globalInterceptor = sqlEntityUpdateExpression.getRuntimeContext().getEasyQueryConfiguration().getGlobalInterceptor(interceptor);
                globalEntityInterceptors.add((GlobalEntityInterceptor) globalInterceptor);
            }
            for (T entity : entities) {
                for (GlobalEntityInterceptor globalEntityInterceptor : globalEntityInterceptors) {
                    globalEntityInterceptor.configureUpdate(entityMetadata.getEntityClass(), sqlEntityUpdateExpression,entity);
                }
            }
        }
    }
    protected  String toSql(Object entity) {
        return toInternalSql(entity);
    }
    protected abstract String toInternalSql(Object entity);


    @Override
    public EntityUpdatable<T> setOnlyColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
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

    @Override
    public EntityUpdatable<T> setUpdateStrategy(boolean condition, UpdateStrategyEnum updateStrategy) {
        if(condition){
            sqlEntityUpdateExpression.getSqlExpressionContext().useUpdateStrategy(updateStrategy);
        }
        return this;
    }
}
