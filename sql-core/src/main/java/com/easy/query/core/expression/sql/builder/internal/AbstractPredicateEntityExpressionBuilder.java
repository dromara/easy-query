package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.RelationTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnEqualsPropertyPredicate;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.SQLExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyColumnSegmentUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * create time 2023/3/15 21:58
 */
public abstract class AbstractPredicateEntityExpressionBuilder extends AbstractEntityExpressionBuilder implements LambdaEntityExpressionBuilder {
    public AbstractPredicateEntityExpressionBuilder(ExpressionContext expressionContext,Class<?> queryClass) {
        super(expressionContext,queryClass);
    }

    protected boolean useLogicDelete(EntityMetadata entityMetadata) {
        return entityMetadata.enableLogicDelete() && expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE);
    }

    /**
     * 存在问题 update必须要总的predicate但是如果在这边导致我手动指定where也会有这个version
     *
     * @param table
     * @param originalPredicate
     * @return
     */
    protected PredicateSegment sqlPredicateFilter(EntityTableExpressionBuilder table, PredicateSegment originalPredicate) {
        if (!(table instanceof AnonymousEntityTableExpressionBuilder)) {

            EntityMetadata entityMetadata = table.getEntityMetadata();
            PredicateSegment predicateSegment = new AndPredicateSegment(true);
            SQLExpressionInvokeFactory easyQueryLambdaFactory = getRuntimeContext().getSQLExpressionInvokeFactory();
            TableAvailable entityTable = table.getEntityTable();
            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(entityTable, this, predicateSegment);

            if (useLogicDelete(entityMetadata)) {
                invokeTableLogicDelete(table,sqlPredicate);
            }
            if(!isQuery()){
                if (entityMetadata.hasVersionColumn()) {
                    boolean ignoreVersion = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.IGNORE_VERSION);
                    if(!ignoreVersion){
                        VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                        if (isExpression()) {
                            Object version = expressionContext.getVersion();
                            if (Objects.nonNull(version)) {
                                sqlPredicate.eq(versionMetadata.getPropertyName(), version);
                            }else {
                                throw new EasyQueryInvalidOperationException("entity:"+ EasyClassUtil.getSimpleName(table.getEntityClass())+" has version expression not found version");
                            }
                        } else {
                            ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(versionMetadata.getPropertyName());
                            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(entityTable, columnMetadata, this.getExpressionContext());
                            ColumnValue2Segment columnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(entityTable, columnMetadata, this.getExpressionContext(), null);
                            AndPredicateSegment versionPredicateSegment = new AndPredicateSegment(new ColumnEqualsPropertyPredicate(column2Segment,columnValue2Segment));
                            predicateSegment.addPredicateSegment(versionPredicateSegment);
                        }
                    }
                }
            }
//            //如果当前对象是存在拦截器的那么就通过stream获取剩余的拦截器
//            List<PredicateFilterInterceptor> predicateFilterInterceptors = entityMetadata.getPredicateFilterInterceptors();
//            if (EasyCollectionUtil.isNotEmpty(predicateFilterInterceptors)) {
//                Predicate<Interceptor> interceptorFilter = expressionContext.getInterceptorFilter();
//                for (PredicateFilterInterceptor predicateFilterInterceptor : predicateFilterInterceptors) {
//                    if (interceptorFilter.test(predicateFilterInterceptor)) {
//                        predicateFilterInterceptor.configure(entityMetadata.getEntityClass(), this, sqlPredicate);
//                    }
//                }
//            }
            EasySQLExpressionUtil.invokeInterceptors(entityMetadata,this,expressionContext,sqlPredicate);

            if (predicateSegment.isNotEmpty()) {
                if (originalPredicate != null && originalPredicate.isNotEmpty()) {
                    predicateSegment.addPredicateSegment(originalPredicate);
                }
                return predicateSegment;
            }
        }
        return originalPredicate;
    }



    private Boolean relationLogicDelete(RelationTableAvailable relationTable) {
        if (!this.expressionContext.hasRelationLogicDelete()) {
            return null;
        }
        return this.expressionContext.getRelationLogicDelete().apply(relationTable.getEntityClass());
    }

    private void invokeTableLogicDelete(EntityTableExpressionBuilder table,WherePredicate<Object> sqlPredicate){
        TableAvailable entityTable = table.getEntityTable();
        //是否存在关联关系的逻辑删除
        if(expressionContext.hasRelationLogicDelete()){
            if(entityTable instanceof RelationTableAvailable){
                RelationTableAvailable relationTable = (RelationTableAvailable) entityTable;
                //如果存在着现货区存在的判定结果
                Boolean relationLogicDel = relationLogicDelete(relationTable);
                //如果结果为false那么表示不使用逻辑删除
                if(relationLogicDel != null && !relationLogicDel){
                    return;
                }
            }
        }
        SQLActionExpression1<WherePredicate<Object>> logicDeleteQueryFilterExpression = table.getLogicDeleteQueryFilterExpression();
        if(logicDeleteQueryFilterExpression!=null){
            logicDeleteQueryFilterExpression.apply(sqlPredicate);
        }
    }


    protected SQLExpression toTableExpressionSQL(EntityTableExpressionBuilder entityTableExpressionBuilder, boolean onlySingleAnonymousTable) {
        if (entityTableExpressionBuilder instanceof AnonymousEntityTableExpressionBuilder) {

            EntityQueryExpressionBuilder sqlEntityQueryExpression = ((AnonymousEntityTableExpressionBuilder) entityTableExpressionBuilder).getEntityQueryExpressionBuilder();
            //如果只有单匿名表且未对齐select那么嵌套表需要被展开
            //todo 如果对其进行order 或者 where了呢怎么办
            return onlySingleAnonymousTable ? sqlEntityQueryExpression.toExpression() : entityTableExpressionBuilder.toExpression();
        }
        return entityTableExpressionBuilder.toExpression();
    }

    protected PredicateSegment getTableOnWithQueryFilter(EntityTableExpressionBuilder table) {
        return sqlPredicateFilter(table, table.hasOn() ? table.getOn() : null);
    }
    protected void addRelationTables(EntitySQLExpression entitySQLExpression){

        if(hasRelationTables()){
            for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> relationTableKV : getRelationTables().entrySet()) {
                EntityTableExpressionBuilder value = relationTableKV.getValue();
                EntityTableSQLExpression tableExpression = (EntityTableSQLExpression) toTableExpressionSQL(value, false);
                entitySQLExpression.getTables().add(tableExpression);
                PredicateSegment on = getTableOnWithQueryFilter(value);
                if (on != null && on.isNotEmpty()) {
                    tableExpression.setOn(on);
                }
            }
        }
    }
}
