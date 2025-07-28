package com.easy.query.core.proxy.grouping;

import com.easy.query.api.proxy.extension.casewhen.PredicateCaseWhenBuilder;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContextImpl;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.flat.casewhen.FlatElementCaseWhenEntityBuilder;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2025/3/5 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class FlatElementSQLAnyQueryable {
    private final SQLPredicateExpression predicate;
    private final EntitySQLContext entitySQLContext;
    private final GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext;

    public FlatElementSQLAnyQueryable(EntitySQLContext entitySQLContext, SQLPredicateExpression predicate) {
        this.entitySQLContext = entitySQLContext;
        this.predicate = predicate;
        PredicateSegment predicateSegment = EasySQLExpressionUtil.resolve(entitySQLContext.getRuntimeContext(), entitySQLContext.getExpressionContext(), filter -> {
            predicate.accept(filter);
        });
        this.groupJoinPredicateSegmentContext=new GroupJoinPredicateSegmentContextImpl(predicateSegment);
    }


    public GroupJoinPredicateSegmentContext getGroupJoinPredicateSegmentContext() {
        return groupJoinPredicateSegmentContext;
    }

    public NumberTypeExpression<Long> count() {

        PropTypeColumn<?> preColumn = new PredicateCaseWhenBuilder(this.entitySQLContext, groupJoinPredicateSegmentContext).then(1).elseEnd(null, Long.class);
        return new NumberTypeExpressionImpl<>(this.entitySQLContext, preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            }).distinct(false);
        }, Long.class);
    }
}
