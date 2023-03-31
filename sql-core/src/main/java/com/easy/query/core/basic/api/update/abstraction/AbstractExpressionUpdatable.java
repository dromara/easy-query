package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSqlExecuteRows;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetter;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.EntityUpdateExpression;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.util.Collection;
import java.util.function.Function;

/**
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 * @author xuejiaming
 */
public abstract class AbstractExpressionUpdatable<T> extends AbstractSqlExecuteRows<ExpressionUpdatable<T>> implements ExpressionUpdatable<T> {
    protected final Class<T> clazz;
    protected final  EntityMetadata entityMetadata;
    protected final EntityUpdateExpression entityUpdateExpression;
    protected final SqlColumnSetter<T> sqlColumnSetter;

    public AbstractExpressionUpdatable(Class<T> clazz, EntityUpdateExpression entityUpdateExpression) {
        super(entityUpdateExpression);

        this.clazz = clazz;
        this.entityUpdateExpression = entityUpdateExpression;

         entityMetadata = this.entityUpdateExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        EntityTableExpression table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.entityUpdateExpression.addSqlEntityTableExpression(table);
        sqlColumnSetter = new DefaultSqlColumnSetter<>(0, entityUpdateExpression, entityUpdateExpression.getSetColumns());
    }

    @Override
    public long executeRows() {
        String updateSql = toSql();
        if (StringUtil.isNotBlank(updateSql)) {
            EasyExecutor easyExecutor = entityUpdateExpression.getRuntimeContext().getEasyExecutor();
            return easyExecutor.executeRows(ExecutorContext.create(entityUpdateExpression.getRuntimeContext()), updateSql, entityUpdateExpression.getParameters());
        }

        return 0;
    }

    @Override
    public ExpressionUpdatable<T> set(boolean condition, Property<T, ?> column, Object val) {
        sqlColumnSetter.set(true,column,val);
        return this;
    }

    @Override
    public  ExpressionUpdatable<T> setSelfColumn(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        sqlColumnSetter.set(true,column1,column2);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> withVersion(boolean condition, Object versionValue) {
        if(condition){
            entityUpdateExpression.getExpressionContext().setVersion(versionValue);
        }
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
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, entityUpdateExpression, entityUpdateExpression.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ExpressionUpdatable<T> whereId(boolean condition, Object id) {

        if(condition){

            PredicateSegment where = entityUpdateExpression.getWhere();
            EntityTableExpression table = entityUpdateExpression.getTable(0);
            Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
            if(keyProperties.isEmpty()){
                throw new EasyQueryException("对象:"+ ClassUtil.getSimpleName(clazz)+"未找到主键信息");
            }
            if(keyProperties.size()>1){
                throw new EasyQueryException("对象:"+ ClassUtil.getSimpleName(clazz)+"存在多个主键");
            }
            String keyProperty = keyProperties.iterator().next();
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SqlPredicateCompareEnum.EQ, entityUpdateExpression));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    @Override
    public ExpressionUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityUpdateExpression.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public String toSql() {
        return null;
    }
}
