package org.easy.query.core.basic.api.update.abstraction;

import org.easy.query.core.basic.jdbc.executor.EasyExecutor;
import org.easy.query.core.basic.jdbc.executor.ExecutorContext;
import org.easy.query.core.basic.api.update.ExpressionUpdatable;
import org.easy.query.core.interceptor.GlobalInterceptorStrategy;
import org.easy.query.core.interceptor.update.GlobalUpdateSetInterceptorStrategy;
import org.easy.query.core.exception.EasyQueryConcurrentException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.enums.MultiTableTypeEnum;
import org.easy.query.core.expression.parser.impl.DefaultSqlColumnSetter;
import org.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import org.easy.query.core.query.*;
import org.easy.query.core.util.StringUtil;

import java.util.List;

/**
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 * @Created by xuejiaming
 */
public abstract class AbstractExpressionUpdatable<T> implements ExpressionUpdatable<T> {
    protected final Class<T> clazz;
    protected final  EntityMetadata entityMetadata;
    protected final SqlEntityUpdateExpression sqlEntityUpdateExpression;

    public AbstractExpressionUpdatable(Class<T> clazz, SqlEntityUpdateExpression sqlEntityUpdateExpression) {

        this.clazz = clazz;
        this.sqlEntityUpdateExpression = sqlEntityUpdateExpression;

         entityMetadata = this.sqlEntityUpdateExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        SqlEntityTableExpression table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.sqlEntityUpdateExpression.addSqlEntityTableExpression(table);
    }

    protected void updateBefore() {

        List<String> interceptors = entityMetadata.getUpdateSetInterceptors();
        boolean hasInterceptor = !interceptors.isEmpty();
        if (hasInterceptor) {
            for (String interceptor : interceptors) {
                GlobalInterceptorStrategy globalInterceptorStrategy = sqlEntityUpdateExpression.getRuntimeContext().getEasyQueryConfiguration().getGlobalInterceptorStrategy(interceptor);
                ((GlobalUpdateSetInterceptorStrategy) globalInterceptorStrategy).configure(entityMetadata.getEntityClass(),sqlEntityUpdateExpression,new DefaultSqlColumnSetter<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns()));
            }
        }
    }
    @Override
    public long executeRows() {
        updateBefore();
        String updateSql = toSql();
        if (StringUtil.isNotBlank(updateSql)) {
            EasyExecutor easyExecutor = sqlEntityUpdateExpression.getRuntimeContext().getEasyExecutor();
            return easyExecutor.executeRows(ExecutorContext.create(sqlEntityUpdateExpression.getRuntimeContext()), updateSql, sqlEntityUpdateExpression.getParameters());
        }

        return 0;
    }

    @Override
    public void executeRows(long expectRows, String msg,String code) {
        long rows = executeRows();
        if(rows!=expectRows){
            throw new EasyQueryConcurrentException(msg,code);
        }
    }

    @Override
    public ExpressionUpdatable<T> set(boolean condition, SqlExpression<SqlColumnSetter<T>> setExpression) {
        if (condition) {
            DefaultSqlColumnSetter<T> columnSetter = new DefaultSqlColumnSetter<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns());
            setExpression.apply(columnSetter);
        }
        return this;
    }

    @Override
    public ExpressionUpdatable<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression) {
        if (condition) {
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }
}
