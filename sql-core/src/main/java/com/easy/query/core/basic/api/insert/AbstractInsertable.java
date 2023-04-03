package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.plugin.interceptor.EasyInterceptorEntry;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.basic.plugin.interceptor.EasyEntityInterceptor;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: AbstractInsertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 12:30
 */
public abstract class AbstractInsertable<T> implements Insertable<T> {
    protected final List<T> entities;
    protected final EntityMetadata entityMetadata;
    protected final EntityInsertExpression entityInsertExpression;

    public AbstractInsertable(Class<T> clazz, EntityInsertExpression entityInsertExpression) {
        this.entityInsertExpression = entityInsertExpression;
        this.entities = new ArrayList<>();
        entityMetadata = this.entityInsertExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();

        EntityTableExpression table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.entityInsertExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public Insertable<T> insert(T entity) {
        if (entity != null) {
            entities.add(entity);
        }
        return this;
    }

//
//    @Override
//    public Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression) {
//        return null;
//    }

    protected void insertBefore() {

        List<EasyInterceptorEntry> insertInterceptors = entityMetadata.getEntityInterceptors();
        if (ArrayUtil.isNotEmpty(insertInterceptors)) {
            EasyQueryConfiguration easyQueryConfiguration = entityInsertExpression.getRuntimeContext().getEasyQueryConfiguration();
            List<EasyEntityInterceptor> entityInterceptors = entityInsertExpression.getExpressionContext().getInterceptorFilter(insertInterceptors)
                    .map(interceptor -> (EasyEntityInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor.getName())).collect(Collectors.toList());
            if (ArrayUtil.isNotEmpty(entityInterceptors)) {
                Class<?> entityClass = entityMetadata.getEntityClass();
                for (T entity : entities) {
                    for (EasyEntityInterceptor entityInterceptor : entityInterceptors) {
                        entityInterceptor.configureInsert(entityClass, entityInsertExpression, entity);
                    }
                }
            }
        }
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        if (!entities.isEmpty()) {
            insertBefore();
            String insertSql = toSql();
            if (!StringUtil.isBlank(insertSql)) {
                EasyExecutor easyExecutor = entityInsertExpression.getRuntimeContext().getEasyExecutor();
                return easyExecutor.insert(ExecutorContext.create(entityInsertExpression.getRuntimeContext()), insertSql, entities, entityInsertExpression.getParameters(), fillAutoIncrement);
            }
        }

        return 0;
    }

    @Override
    public Insertable<T> asTable(Function<String, String> tableNameAs) {
        entityInsertExpression.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public Insertable<T> noInterceptor() {
        entityInsertExpression.getExpressionContext().noInterceptor();
        return this;
    }
    @Override
    public Insertable<T> useInterceptor(String name) {
        entityInsertExpression.getExpressionContext().useInterceptor(name);
        return this;
    }
    @Override
    public Insertable<T> noInterceptor(String name) {
        entityInsertExpression.getExpressionContext().noInterceptor(name);
        return this;
    }

    @Override
    public Insertable<T> useInterceptor() {
        entityInsertExpression.getExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public abstract String toSql();
}
