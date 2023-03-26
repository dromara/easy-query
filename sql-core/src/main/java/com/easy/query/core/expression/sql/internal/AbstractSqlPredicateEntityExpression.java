package com.easy.query.core.expression.sql.internal;

import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.basic.plugin.interceptor.EasyPredicateFilterInterceptor;
import com.easy.query.core.expression.sql.AnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.SqlEntityTableExpression;
import com.easy.query.core.expression.sql.SqlExpressionContext;
import com.easy.query.core.util.ArrayUtil;

import java.util.List;

/**
 * @FileName: AbstractSqlPredicateEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/15 21:58
 * @author xuejiaming
 */
public abstract class AbstractSqlPredicateEntityExpression extends AbstractSqlEntityExpression {
    public AbstractSqlPredicateEntityExpression(SqlExpressionContext sqlExpressionContext) {
        super(sqlExpressionContext);
    }

    protected PredicateSegment sqlPredicateFilter(SqlEntityTableExpression table, PredicateSegment originalPredicate) {
        if (!(table instanceof AnonymousEntityTableExpression)) {

            EntityMetadata entityMetadata = table.getEntityMetadata();
            boolean useLogicDelete = sqlExpressionContext.isUseLogicDelete() && entityMetadata.enableLogicDelete();
            boolean useInterceptor = getSqlExpressionContext().isUseInterceptor() && ArrayUtil.isNotEmpty(entityMetadata.getPredicateFilterInterceptors());
            if (useLogicDelete || useInterceptor) {
                PredicateSegment predicateSegment = new AndPredicateSegment(true);
                EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
                SqlPredicate<Object> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);

                SqlExpression<SqlPredicate<Object>> logicDeleteQueryFilterExpression = table.getLogicDeleteQueryFilterExpression();
                logicDeleteQueryFilterExpression.apply(sqlPredicate);
                if (useInterceptor) {
                    List<String> predicateFilterInterceptors = entityMetadata.getPredicateFilterInterceptors();
                    EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
                    for (String predicateFilterInterceptor : predicateFilterInterceptors) {
                        EasyPredicateFilterInterceptor globalSelectInterceptorStrategy = (EasyPredicateFilterInterceptor) easyQueryConfiguration.getGlobalInterceptor(predicateFilterInterceptor);
                        if (globalSelectInterceptorStrategy != null) {
                            globalSelectInterceptorStrategy.configure(table.entityClass(), this, sqlPredicate);
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
        }
        return originalPredicate;
    }
}
