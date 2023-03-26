package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSqlExecuteRows;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.UpdateEntityNode;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.UpdateStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetSelector;
import com.easy.query.core.basic.plugin.interceptor.EasyEntityInterceptor;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptor;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityUpdateExpression;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.*;

/**
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 * @author xuejiaming
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

    private List<UpdateEntityNode> createUpdateEntityNode(){
        TrackManager trackManager = sqlEntityUpdateExpression.getRuntimeContext().getTrackManager();
        if(!UpdateStrategyEnum.DEFAULT.equals(sqlEntityUpdateExpression.getSqlExpressionContext().getUpdateStrategy())||trackManager.currentThreadTracking()){
            Map<String, UpdateEntityNode> updateEntityNodeMap = new LinkedHashMap<>();
            for (T entity : entities) {
                String updateSql = toSql(entity);
                //如果当前对象没有需要更新的列直接忽略
                if(updateSql==null){
                    continue;
                }
                List<SQLParameter> parameters = new ArrayList<>(sqlEntityUpdateExpression.getParameters());
                UpdateEntityNode updateEntityNode = updateEntityNodeMap.computeIfAbsent(updateSql, k -> new UpdateEntityNode(updateSql, parameters));
                updateEntityNode.getEntities().add(entity);
            }
            return new ArrayList<>(updateEntityNodeMap.values());
        }else{
            String updateSql = toSql(null);
            UpdateEntityNode updateEntityNode = new UpdateEntityNode(updateSql,new ArrayList<>(sqlEntityUpdateExpression.getParameters()),entities.size());
            updateEntityNode.getEntities().addAll(entities);
            return Collections.singletonList(updateEntityNode);
        }
    }

    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {
            updateBefore();
            List<UpdateEntityNode> updateEntityNodes = createUpdateEntityNode();
            EasyExecutor easyExecutor = sqlEntityUpdateExpression.getRuntimeContext().getEasyExecutor();
            int i=0;
            for (UpdateEntityNode updateEntityNode : updateEntityNodes) {

                i+= easyExecutor.executeRows(ExecutorContext.create(sqlEntityUpdateExpression.getRuntimeContext()), updateEntityNode.getSql(), updateEntityNode.getEntities(), updateEntityNode.getSqlParameters());

            }
            return i;
        }
        return 0;
    }

    protected void updateBefore() {

        List<String> interceptors = entityMetadata.getEntityInterceptors();
        boolean hasInterceptor = !interceptors.isEmpty();
        ArrayList<EasyEntityInterceptor> globalEntityInterceptors = new ArrayList<>(interceptors.size());
        if (hasInterceptor) {
            for (String interceptor : interceptors) {
                EasyInterceptor globalInterceptor = sqlEntityUpdateExpression.getRuntimeContext().getEasyQueryConfiguration().getGlobalInterceptor(interceptor);
                globalEntityInterceptors.add((EasyEntityInterceptor) globalInterceptor);
            }
            for (T entity : entities) {
                for (EasyEntityInterceptor globalEntityInterceptor : globalEntityInterceptors) {
                    globalEntityInterceptor.configureUpdate(entityMetadata.getEntityClass(), sqlEntityUpdateExpression,entity);
                }
            }
        }
    }
    public abstract  String toSql(Object entity);


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

    @Override
    public EntityUpdatable<T> setUpdateStrategy(boolean condition, UpdateStrategyEnum updateStrategy) {
        if(condition){
            sqlEntityUpdateExpression.getSqlExpressionContext().useUpdateStrategy(updateStrategy);
        }
        return this;
    }
}
