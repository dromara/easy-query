package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.basic.plugin.interceptor.EasyPredicateFilterInterceptor;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyUtil;

import java.util.Objects;

/**
 * @author xuejiaming
 * @FileName: AbstractSqlPredicateEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/15 21:58
 */
public abstract class AbstractPredicateEntityExpressionBuilder extends AbstractEntityExpressionBuilder implements LambdaEntityExpressionBuilder {
    public AbstractPredicateEntityExpressionBuilder(ExpressionContext sqlExpressionContext) {
        super(sqlExpressionContext);
    }

    protected boolean useLogicDelete(EntityMetadata entityMetadata) {
        return sqlExpressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE) && entityMetadata.enableLogicDelete();
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
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            SqlWherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);

            if (useLogicDelete(entityMetadata)) {
                SqlExpression<SqlWherePredicate<Object>> logicDeleteQueryFilterExpression = table.getLogicDeleteQueryFilterExpression();
                logicDeleteQueryFilterExpression.apply(sqlPredicate);
            }

            if (entityMetadata.hasVersionColumn()) {
                VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(versionMetadata.getPropertyName());
                if (isExpression()) {
                    Object version = sqlExpressionContext.getVersion();
                    if (Objects.nonNull(version)) {
                        FastBean fastBean = BeanUtil.getFastBean(table.getEntityClass());
                        sqlPredicate.eq(fastBean.getBeanGetter(columnMetadata.getProperty()), version);
                    }
                } else {
                    AndPredicateSegment versionPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table.getEntityTable(), versionMetadata.getPropertyName(), this.getRuntimeContext()));
                    predicateSegment.addPredicateSegment(versionPredicateSegment);
                }
            }
            //如果当前对象是存在拦截器的那么就通过stream获取剩余的拦截器
            if (EasyCollectionUtil.isNotEmpty(entityMetadata.getPredicateFilterInterceptors())) {
                EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
                sqlExpressionContext.getInterceptorFilter(entityMetadata.getPredicateFilterInterceptors())
                        .forEach(interceptor -> {
                            EasyPredicateFilterInterceptor globalSelectInterceptorStrategy = (EasyPredicateFilterInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor.getName());
                            if (globalSelectInterceptorStrategy != null) {
                                globalSelectInterceptorStrategy.configure(entityMetadata.getEntityClass(), this, sqlPredicate);
                            }
                        });
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
