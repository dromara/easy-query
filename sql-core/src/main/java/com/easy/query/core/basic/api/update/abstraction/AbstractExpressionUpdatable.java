package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnSetter;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.DefaultSQLPredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.impl.TableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 * @author xuejiaming
 */
public abstract class AbstractExpressionUpdatable<T> extends AbstractSQLExecuteRows<ExpressionUpdatable<T>> implements ExpressionUpdatable<T> {
    protected final Class<T> clazz;
    protected final  EntityMetadata entityMetadata;
    protected final EntityUpdateExpressionBuilder entityUpdateExpressionBuilder;
    protected final SQLColumnSetter<T> sqlColumnSetter;
    protected final TableAvailable table;

    public AbstractExpressionUpdatable(Class<T> clazz, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder) {
        super(entityUpdateExpressionBuilder);

        this.clazz = clazz;
        this.entityUpdateExpressionBuilder = entityUpdateExpressionBuilder;
        QueryRuntimeContext runtimeContext = entityUpdateExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        EntityTableExpressionBuilder table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, 0, null, MultiTableTypeEnum.NONE, runtimeContext);
this.table=table.getEntityTable();
        this.entityUpdateExpressionBuilder.addSQLEntityTableExpression(table);
        sqlColumnSetter = new DefaultSQLColumnSetter<>(0, entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getSetColumns());
    }

    @Override
    public long executeRows() {
        QueryRuntimeContext runtimeContext = entityUpdateExpressionBuilder.getRuntimeContext();
        EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
        return entityExpressionExecutor.executeRows(ExecutorContext.create(entityUpdateExpressionBuilder.getRuntimeContext(),false, ExecuteMethodEnum.UPDATE), entityUpdateExpressionBuilder);
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
            entityUpdateExpressionBuilder.getExpressionContext().setVersion(versionValue);
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
    public ExpressionUpdatable<T> where(boolean condition, SQLExpression1<SQLWherePredicate<T>> whereExpression) {
        if (condition) {
            DefaultSQLPredicate<T> sqlPredicate = new DefaultSQLPredicate<>(0, entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ExpressionUpdatable<T> whereById(boolean condition, Object id) {

        if(condition){

            PredicateSegment where = entityUpdateExpressionBuilder.getWhere();
            String keyProperty = getSingleKeyPropertyName();
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SQLPredicateCompareEnum.EQ, entityUpdateExpressionBuilder.getRuntimeContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    private String getSingleKeyPropertyName(){
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if(EasyCollectionUtil.isEmpty(keyProperties)){
            throw new EasyQueryException("对象:"+ EasyClassUtil.getSimpleName(clazz)+"未找到主键信息");
        }
        if(EasyCollectionUtil.isNotSingle(keyProperties)){
            throw new EasyQueryException("对象:"+ EasyClassUtil.getSimpleName(clazz)+"存在多个主键");
        }
        return EasyCollectionUtil.first(keyProperties);
    }
    @Override
    public ExpressionUpdatable<T> whereByIds(boolean condition, Object... ids) {
        if (condition) {
            Collection<?> extractIds = extractIds(ids);
            return whereByIds(true, extractIds);
        }
        return this;
    }

    @Override
    public <TProperty> ExpressionUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            String keyProperty = getSingleKeyPropertyName();
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            PredicateSegment where = entityUpdateExpressionBuilder.getWhere();
            andPredicateSegment
                    .setPredicate(new ColumnCollectionPredicate(table, keyProperty, ids, SQLPredicateCompareEnum.IN, entityUpdateExpressionBuilder.getRuntimeContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }


    private Collection<?> extractIds(Object... ids) {
        if (ids == null || ids.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(ids);
    }

    @Override
    public ExpressionUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext){
        return entityUpdateExpressionBuilder.toExpression().toSQL(toSQLContext);
    }
}
