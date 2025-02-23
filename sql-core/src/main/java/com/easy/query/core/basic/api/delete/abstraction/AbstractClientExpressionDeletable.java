package com.easy.query.core.basic.api.delete.abstraction;

import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurerImpl;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyColumnSegmentUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:30
 */
public abstract class AbstractClientExpressionDeletable<T> extends AbstractSQLExecuteRows<ClientExpressionDeletable<T>> implements ClientExpressionDeletable<T> {
    protected final Class<T> clazz;
    protected final EntityTableExpressionBuilder table;
    protected final EntityDeleteExpressionBuilder entityDeleteExpressionBuilder;

    public AbstractClientExpressionDeletable(Class<T> clazz, EntityDeleteExpressionBuilder entityDeleteExpressionBuilder) {
        super(entityDeleteExpressionBuilder);
        this.entityDeleteExpressionBuilder = entityDeleteExpressionBuilder;

        this.clazz = clazz;
        QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.NONE, runtimeContext);
        this.entityDeleteExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public EntityDeleteExpressionBuilder getDeleteExpressionBuilder() {
        return entityDeleteExpressionBuilder;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return entityDeleteExpressionBuilder.getExpressionContext();
    }

    @Override
    public long executeRows() {
        QueryRuntimeContext runtimeContext = entityDeleteExpressionBuilder.getRuntimeContext();
        EntityExpressionExecutor entityExpressionExecutor = runtimeContext.getEntityExpressionExecutor();
        return entityExpressionExecutor.executeRows(ExecutorContext.create(entityDeleteExpressionBuilder.getExpressionContext(), false, ExecuteMethodEnum.DELETE), entityDeleteExpressionBuilder);
    }

//    @Override
//    public void executeRows(long expectRows, String msg, String code) {
//        long rows = executeRows();
//        if(rows!=expectRows){
//            throw new EasyQueryConcurrentException(msg,code);
//        }
//    }

    @Override
    public ClientExpressionDeletable<T> where(boolean condition, SQLExpression1<WherePredicate<T>> whereExpression) {
        if (condition) {
            WherePredicateImpl<T> sqlPredicate = new WherePredicateImpl<>(table.getEntityTable(), new FilterContext(new FilterImpl(entityDeleteExpressionBuilder.getRuntimeContext(), entityDeleteExpressionBuilder.getExpressionContext(), entityDeleteExpressionBuilder.getWhere(), false, AnyValueFilter.DEFAULT)));
            whereExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            entityDeleteExpressionBuilder.getExpressionContext().setVersion(versionValue);
        }
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> ignoreVersion(boolean ignored) {

        if (ignored) {
            entityDeleteExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.IGNORE_VERSION);
        } else {
            entityDeleteExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.IGNORE_VERSION);
        }
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> whereById(boolean condition, Object id) {

        if (condition) {
            PredicateSegment where = entityDeleteExpressionBuilder.getWhere();
            String keyProperty = EasySQLExpressionUtil.getSingleKeyPropertyName(table.getEntityTable());
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            ColumnMetadata columnMetadata = table.getEntityTable().getEntityMetadata().getColumnNotNull(keyProperty);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table.getEntityTable(), columnMetadata, entityDeleteExpressionBuilder.getExpressionContext());
            ColumnValue2Segment compareValue2Segment = EasyColumnSegmentUtil.createColumnCompareValue2Segment(table.getEntityTable(), columnMetadata, entityDeleteExpressionBuilder.getExpressionContext(), id, SQLPredicateCompareEnum.EQ.isLike());
            andPredicateSegment
                    .setPredicate(new ColumnValuePredicate(column2Segment, compareValue2Segment, SQLPredicateCompareEnum.EQ));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    @Override
    public <TProperty> ClientExpressionDeletable<T> whereByIds(boolean condition, Collection<TProperty> ids) {

        if (condition) {
            PredicateSegment where = entityDeleteExpressionBuilder.getWhere();
            String keyProperty = EasySQLExpressionUtil.getSingleKeyPropertyName(table.getEntityTable());
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
            ColumnMetadata columnMetadata = table.getEntityTable().getEntityMetadata().getColumnNotNull(keyProperty);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table.getEntityTable(), columnMetadata, getExpressionContext());
            List<ColumnValue2Segment> columnValue2Segments =  EasyCollectionUtil.select(ids,(o,i) -> EasyColumnSegmentUtil.createColumnCompareValue2Segment(table.getEntityTable(), columnMetadata, getExpressionContext(), o));
            andPredicateSegment
                    .setPredicate(new ColumnCollectionPredicate(column2Segment, columnValue2Segments, SQLPredicateCompareEnum.IN, entityDeleteExpressionBuilder.getExpressionContext()));
            where.addPredicateSegment(andPredicateSegment);
        }
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> useLogicDelete(boolean enable) {
        entityDeleteExpressionBuilder.setLogicDelete(enable);
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> allowDeleteStatement(boolean allow) {
        entityDeleteExpressionBuilder.getExpressionContext().deleteThrow(!allow);
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> asTable(Function<String, String> tableNameAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> asSchema(Function<String, String> schemaAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setSchemaAs(schemaAs);
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> asAlias(String alias) {
        entityDeleteExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> asTableLink(Function<String, String> linkAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableLinkAs(linkAs);
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityDeleteExpressionBuilder.getRecentlyTable().setTableSegmentAs(segmentAs);
        return this;
    }

    @Override
    public ClientExpressionDeletable<T> configure(SQLExpression1<ContextConfigurer> configurer) {
        if(configurer!=null){
            configurer.apply(new ContextConfigurerImpl(entityDeleteExpressionBuilder.getExpressionContext()));
        }
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return toSQLWithParam(toSQLContext);
    }

    private String toSQLWithParam(ToSQLContext toSQLContext) {
        return entityDeleteExpressionBuilder.toExpression().toSQL(toSQLContext);
    }
}
