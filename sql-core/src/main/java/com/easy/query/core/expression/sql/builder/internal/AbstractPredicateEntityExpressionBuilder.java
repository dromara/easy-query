package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @Date: 2023/3/15 21:58
 */
public abstract class AbstractPredicateEntityExpressionBuilder extends AbstractEntityExpressionBuilder implements LambdaEntityExpressionBuilder {
    public AbstractPredicateEntityExpressionBuilder(ExpressionContext expressionContext) {
        super(expressionContext);
    }

    protected boolean useLogicDelete(EntityMetadata entityMetadata) {
        return expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE) && entityMetadata.enableLogicDelete();
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
            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(table.getIndex(), this, predicateSegment);

            if (useLogicDelete(entityMetadata)) {
                SQLExpression1<WherePredicate<Object>> logicDeleteQueryFilterExpression = table.getLogicDeleteQueryFilterExpression();
                logicDeleteQueryFilterExpression.apply(sqlPredicate);
            }

            if (entityMetadata.hasVersionColumn()) {
                VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                if (isExpression()) {
                    Object version = expressionContext.getVersion();
                    if (Objects.nonNull(version)) {
                        sqlPredicate.eq(versionMetadata.getPropertyName(), version);
                    }
                } else {
                    AndPredicateSegment versionPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table.getEntityTable(), versionMetadata.getPropertyName(), this.getRuntimeContext()));
                    predicateSegment.addPredicateSegment(versionPredicateSegment);
                }
            }
            //如果当前对象是存在拦截器的那么就通过stream获取剩余的拦截器
            List<PredicateFilterInterceptor> predicateFilterInterceptors = entityMetadata.getPredicateFilterInterceptors();
            if (EasyCollectionUtil.isNotEmpty(predicateFilterInterceptors)) {
                Predicate<Interceptor> interceptorFilter = expressionContext.getInterceptorFilter();
                for (PredicateFilterInterceptor predicateFilterInterceptor : predicateFilterInterceptors) {
                    if(interceptorFilter.test(predicateFilterInterceptor)){
                        predicateFilterInterceptor.configure(entityMetadata.getEntityClass(), this, sqlPredicate);
                    }
                }
            }

            if (predicateSegment.isNotEmpty()) {
                if (originalPredicate != null && originalPredicate.isNotEmpty()) {
                    predicateSegment.addPredicateSegment(originalPredicate);
                }
                return predicateSegment;
            }
        }
        return originalPredicate;
    }
}
