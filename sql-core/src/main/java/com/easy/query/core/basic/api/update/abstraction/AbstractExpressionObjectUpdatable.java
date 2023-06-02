package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.api.update.ExpressionObjectUpdatable;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.impl.ColumnSetterImpl;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 */
public abstract class AbstractExpressionObjectUpdatable<T> extends AbstractSQLExecuteRows<ExpressionObjectUpdatable<T>> implements ExpressionObjectUpdatable<T> {
    protected final Class<T> clazz;
    protected final EntityMetadata entityMetadata;
    protected final EntityUpdateExpressionBuilder entityUpdateExpressionBuilder;
    protected final ColumnSetterImpl<T> columnSetter;
    protected final TableAvailable table;

    public AbstractExpressionObjectUpdatable(Class<T> clazz, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder) {
        super(entityUpdateExpressionBuilder);

        this.clazz = clazz;
        this.entityUpdateExpressionBuilder = entityUpdateExpressionBuilder;
        QueryRuntimeContext runtimeContext = entityUpdateExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        EntityTableExpressionBuilder table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, 0, MultiTableTypeEnum.NONE, runtimeContext);
        this.table = table.getEntityTable();
        this.entityUpdateExpressionBuilder.addSQLEntityTableExpression(table);
        columnSetter = new ColumnSetterImpl<>(0, entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getSetColumns());
    }

    @Override
    public long executeRows() {
        QueryRuntimeContext runtimeContext = entityUpdateExpressionBuilder.getRuntimeContext();
        EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
        return entityExpressionExecutor.executeRows(ExecutorContext.create(entityUpdateExpressionBuilder.getRuntimeContext(), false, ExecuteMethodEnum.UPDATE), entityUpdateExpressionBuilder);
    }

    @Override
    public ExpressionObjectUpdatable<T> set(boolean condition, String property, Object val) {
        columnSetter.set(true, property, val);
        return this;
    }

    @Override
    public ExpressionObjectUpdatable<T> setSelfColumn(boolean condition, String property1, String property2) {
        columnSetter.setWithColumn(true, property1, property2);
        return this;
    }

    @Override
    public ExpressionObjectUpdatable<T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            entityUpdateExpressionBuilder.getExpressionContext().setVersion(versionValue);
        }
        return this;
    }

    @Override
    public ExpressionObjectUpdatable<T> setIncrementNumber(boolean condition, String property, Number val) {
        columnSetter.setIncrementNumber(true, property, val);
        return this;
    }

    @Override
    public ExpressionObjectUpdatable<T> setDecrementNumber(boolean condition, String property, Number val) {
        columnSetter.setDecrementNumber(true, property, val);
        return this;
    }


    @Override
    public ExpressionObjectUpdatable<T> where(boolean condition, SQLExpression1<WherePredicate<T>> whereExpression) {
        if (condition) {
            WherePredicateImpl<T> sqlPredicate = new WherePredicateImpl<>(0, entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ExpressionObjectUpdatable<T> whereById(boolean condition, Object id) {

        if (condition) {

            PredicateSegment where = entityUpdateExpressionBuilder.getWhere();
            String keyProperty = getSingleKeyPropertyName();
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(table, keyProperty, id, SQLPredicateCompareEnum.EQ, entityUpdateExpressionBuilder.getRuntimeContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    private String getSingleKeyPropertyName() {
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if (EasyCollectionUtil.isEmpty(keyProperties)) {
            throw new EasyQueryException("对象:" + EasyClassUtil.getSimpleName(clazz) + "未找到主键信息");
        }
        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryException("对象:" + EasyClassUtil.getSimpleName(clazz) + "存在多个主键");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    @Override
    public ExpressionObjectUpdatable<T> whereByIds(boolean condition, Object... ids) {
        if (condition) {
            Collection<?> extractIds = extractIds(ids);
            return whereByIds(true, extractIds);
        }
        return this;
    }

    @Override
    public <TProperty> ExpressionObjectUpdatable<T> whereByIds(boolean condition, Collection<TProperty> ids) {

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
    public ExpressionContext getExpressionContext() {
        return entityUpdateExpressionBuilder.getExpressionContext();
    }

    @Override
    public ExpressionObjectUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ExpressionObjectUpdatable<T> asAlias(String alias) {
        entityUpdateExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return entityUpdateExpressionBuilder.toExpression().toSQL(toSQLContext);
    }
}
