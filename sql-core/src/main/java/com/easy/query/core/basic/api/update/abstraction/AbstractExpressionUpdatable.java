package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetter;
import com.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import com.easy.query.core.query.EasyEntityTableExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.query.SqlEntityUpdateExpression;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.interceptor.GlobalInterceptor;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

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
    protected final SqlColumnSetter<T> sqlColumnSetter;

    public AbstractExpressionUpdatable(Class<T> clazz, SqlEntityUpdateExpression sqlEntityUpdateExpression) {

        this.clazz = clazz;
        this.sqlEntityUpdateExpression = sqlEntityUpdateExpression;

         entityMetadata = this.sqlEntityUpdateExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        SqlEntityTableExpression table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.sqlEntityUpdateExpression.addSqlEntityTableExpression(table);
        sqlColumnSetter = new DefaultSqlColumnSetter<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns());
    }
    @Override
    public SqlEntityUpdateExpression getSqlEntityUpdateExpression() {
        return sqlEntityUpdateExpression;
    }

    @Override
    public long executeRows() {
        String updateSql = toSql();
        if (StringUtil.isNotBlank(updateSql)) {
            EasyExecutor easyExecutor = sqlEntityUpdateExpression.getRuntimeContext().getEasyExecutor();
            return easyExecutor.executeRows(ExecutorContext.create(sqlEntityUpdateExpression.getRuntimeContext()), updateSql, sqlEntityUpdateExpression.getParameters());
        }

        return 0;
    }

    @Override
    public ExpressionUpdatable<T> set(boolean condition, Property<T, ?> column, Object val) {
        sqlColumnSetter.set(true,column,val);
        return this;
    }

    @Override
    public  ExpressionUpdatable<T> set(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        sqlColumnSetter.set(true,column1,column2);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> setIncrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {
        sqlColumnSetter.setIncrementNumber(true,column,val);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> setDecrementNumber(boolean condition, Property<T, ? extends Number> column, Number val) {
        sqlColumnSetter.setDecrementNumber(true,column,val);
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