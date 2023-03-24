package com.easy.query.core.basic.api.insert;

import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.interceptor.GlobalEntityInterceptor;
import com.easy.query.core.interceptor.GlobalInterceptor;
import com.easy.query.core.query.EasyEntityTableExpression;
import com.easy.query.core.query.SqlEntityInsertExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractInsertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 12:30
 * @author xuejiaming
 */
public abstract class AbstractInsertable<T> implements Insertable<T> {
    protected final List<T> entities;
    protected final EntityMetadata entityMetadata;
    protected final SqlEntityInsertExpression sqlEntityInsertExpression;

    public AbstractInsertable(Class<T> clazz, SqlEntityInsertExpression sqlEntityInsertExpression) {
        this.sqlEntityInsertExpression = sqlEntityInsertExpression;
        this.entities = new ArrayList<>();
        entityMetadata = this.sqlEntityInsertExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();

        SqlEntityTableExpression table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.sqlEntityInsertExpression.addSqlEntityTableExpression(table);
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

        List<String> insertInterceptors = entityMetadata.getEntityInterceptors();
        boolean hasInsertInterceptors = !insertInterceptors.isEmpty();
        ArrayList<GlobalEntityInterceptor> globalEntityInterceptors = new ArrayList<>(insertInterceptors.size());
        if (hasInsertInterceptors) {
            for (String insertInterceptor : insertInterceptors) {
                GlobalInterceptor globalInterceptorStrategy = sqlEntityInsertExpression.getRuntimeContext().getEasyQueryConfiguration().getGlobalInterceptor(insertInterceptor);
                globalEntityInterceptors.add((GlobalEntityInterceptor) globalInterceptorStrategy);
            }
            for (T entity : entities) {
                for (GlobalEntityInterceptor globalEntityInterceptor : globalEntityInterceptors) {
                    globalEntityInterceptor.configureInsert(entityMetadata.getEntityClass(), sqlEntityInsertExpression,entity);
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
                EasyExecutor easyExecutor = sqlEntityInsertExpression.getRuntimeContext().getEasyExecutor();
                return easyExecutor.insert(ExecutorContext.create(sqlEntityInsertExpression.getRuntimeContext()), insertSql, entities, sqlEntityInsertExpression.getParameters(),fillAutoIncrement);
            }
        }

        return 0;
    }


    @Override
    public abstract String toSql();
}
