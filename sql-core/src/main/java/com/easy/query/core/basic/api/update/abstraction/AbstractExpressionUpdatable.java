package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.insert.Insertable;
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
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityUpdateExpression;
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
public abstract class AbstractExpressionUpdatable<T> extends AbstractSqlExecuteRows implements ExpressionUpdatable<T> {
    protected final Class<T> clazz;
    protected final  EntityMetadata entityMetadata;
    protected final SqlEntityUpdateExpression sqlEntityUpdateExpression;
    protected final SqlColumnSetter<T> sqlColumnSetter;

    public AbstractExpressionUpdatable(Class<T> clazz, SqlEntityUpdateExpression sqlEntityUpdateExpression) {
        super(sqlEntityUpdateExpression);

        this.clazz = clazz;
        this.sqlEntityUpdateExpression = sqlEntityUpdateExpression;

         entityMetadata = this.sqlEntityUpdateExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        SqlEntityTableExpression table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.sqlEntityUpdateExpression.addSqlEntityTableExpression(table);
        sqlColumnSetter = new DefaultSqlColumnSetter<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getSetColumns());
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
    public  ExpressionUpdatable<T> setSelfColumn(boolean condition, Property<T, ?> column1, Property<T, ?> column2) {
        sqlColumnSetter.set(true,column1,column2);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> withVersion(boolean condition, Object versionValue) {
        if(condition){
            sqlEntityUpdateExpression.getSqlExpressionContext().setVersion(versionValue);
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
            DefaultSqlPredicate<T> sqlPredicate = new DefaultSqlPredicate<>(0, sqlEntityUpdateExpression, sqlEntityUpdateExpression.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ExpressionUpdatable<T> whereId(boolean condition, Object id) {

        if(condition){

            PredicateSegment where = sqlEntityUpdateExpression.getWhere();
            SqlEntityTableExpression table = sqlEntityUpdateExpression.getTable(0);
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
                    .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SqlPredicateCompareEnum.EQ, sqlEntityUpdateExpression));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    @Override
    public ExpressionUpdatable<T> asTable(Function<String, String> tableNameAs) {
        sqlEntityUpdateExpression.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public String toSql() {
        return null;
    }
}
