package com.easy.query.core.basic.api.delete.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.delete.ExpressionDeletable;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.DefaultSQLPredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 * @author xuejiaming
 */
public abstract   class AbstractExpressionDeletable<T> extends AbstractSQLExecuteRows<ExpressionDeletable<T>> implements ExpressionDeletable<T> {
    protected final Class<T> clazz;
    protected final EntityTableExpressionBuilder table;
    protected final EntityDeleteExpressionBuilder entityDeleteExpressionBuilder;

    public AbstractExpressionDeletable(Class<T> clazz, EntityDeleteExpressionBuilder entityDeleteExpressionBuilder){
        super(entityDeleteExpressionBuilder);
        this.entityDeleteExpressionBuilder = entityDeleteExpressionBuilder;

        this.clazz = clazz;
        QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, 0, MultiTableTypeEnum.NONE, runtimeContext);
        this.entityDeleteExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return entityDeleteExpressionBuilder.getExpressionContext();
    }

    @Override
    public long executeRows() {
        QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
        EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
        return entityExpressionExecutor.executeRows(ExecutorContext.create(entityDeleteExpressionBuilder.getRuntimeContext(),false, ExecuteMethodEnum.DELETE), entityDeleteExpressionBuilder);
    }

//    @Override
//    public void executeRows(long expectRows, String msg, String code) {
//        long rows = executeRows();
//        if(rows!=expectRows){
//            throw new EasyQueryConcurrentException(msg,code);
//        }
//    }

    @Override
    public ExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLWherePredicate<T>> whereExpression) {
        if(condition){
            DefaultSQLPredicate<T> sqlPredicate = new DefaultSQLPredicate<>(0, entityDeleteExpressionBuilder, entityDeleteExpressionBuilder.getWhere());
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<T> withVersion(boolean condition, Object versionValue) {
        if(condition){
            entityDeleteExpressionBuilder.getExpressionContext().setVersion(versionValue);
        }
        return this;
    }

    @Override
    public Deletable<T, ExpressionDeletable<T>> whereById(boolean condition,Object id) {

        if(condition){
            PredicateSegment where = entityDeleteExpressionBuilder.getWhere();
            String keyProperty = getSingleKeyPropertyName();
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(table.getEntityTable(), keyProperty, id, SQLPredicateCompareEnum.EQ, entityDeleteExpressionBuilder.getRuntimeContext()));
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

    private Collection<?> extractIds(Object... ids){
        if(ids==null||ids.length==0){
            return Collections.emptyList();
        }
        return Arrays.asList(ids);
    }
    @Override
    public Deletable<T, ExpressionDeletable<T>> whereByIds(boolean condition,Object... ids) {
        if(condition){

            Collection<?> extractIds = extractIds(ids);
            return whereByIdCollection(true,extractIds);
        }
        return this;
    }

    @Override
    public <TProperty> Deletable<T, ExpressionDeletable<T>> whereByIdCollection(boolean condition, Collection<TProperty> ids) {

        if(condition){
            PredicateSegment where = entityDeleteExpressionBuilder.getWhere();
            String keyProperty = getSingleKeyPropertyName();
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            andPredicateSegment
                    .setPredicate(new ColumnCollectionPredicate(table.getEntityTable(), keyProperty, ids, SQLPredicateCompareEnum.IN, entityDeleteExpressionBuilder.getRuntimeContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    @Override
    public ExpressionDeletable<T> useLogicDelete(boolean enable) {
        entityDeleteExpressionBuilder.setLogicDelete(enable);
        return this;
    }

    @Override
    public ExpressionDeletable<T> allowDeleteStatement(boolean allow) {
        entityDeleteExpressionBuilder.getExpressionContext().deleteThrow(!allow);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asTable(Function<String, String> tableNameAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ExpressionDeletable<T> asAlias(String alias) {
        entityDeleteExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return toSQLWithParam(toSQLContext);
    }
    private String toSQLWithParam(ToSQLContext toSQLContext){
        return entityDeleteExpressionBuilder.toExpression().toSQL(toSQLContext);
    }
}
