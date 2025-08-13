package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.expression.segment.GroupJoinColumnSegmentImpl;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.impl.DefaultSQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;


/**
 * create time 2025/7/26 19:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateCaseWhenBuilder {
    private final EntitySQLContext entitySQLContext;
    private final GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext;
    private  Object then;

    public PredicateCaseWhenBuilder(EntitySQLContext entitySQLContext, GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext){
        this.entitySQLContext = entitySQLContext;
        this.groupJoinPredicateSegmentContext = groupJoinPredicateSegmentContext;
    }

    public <TV> PredicateCaseWhenBuilder then(TV then) {
        this.then=then;

        return this;
    }
    public <TV,TProperty> AnyTypeExpression<TProperty> elseEnd(TV elseValue) {
        return EasyObjectUtil.typeCastNullable(elseEnd(elseValue, Object.class));
    }
    public <TV, TProperty> AnyTypeExpression<TProperty> elseEnd(TV elseValue, Class<TProperty> resultClass) {
        ParamExpression thenParamExpression = EasyProxyParamExpressionUtil.getParamExpression(entitySQLContext, then);
        ParamExpression elseEndParamExpression = EasyProxyParamExpressionUtil.getParamExpression(entitySQLContext, elseValue);

        DefaultSQLSegment thenSegment = new DefaultSQLSegment(toSQLContext -> EasySQLExpressionUtil.parseParamExpression(entitySQLContext.getExpressionContext(), thenParamExpression, toSQLContext).toString(), visitor -> thenParamExpression.accept(visitor));
        DefaultSQLSegment elseSegment = new DefaultSQLSegment(toSQLContext -> EasySQLExpressionUtil.parseParamExpression(entitySQLContext.getExpressionContext(), elseEndParamExpression, toSQLContext).toString(), visitor -> elseEndParamExpression.accept(visitor));
        GroupJoinColumnSegmentImpl groupJoinColumnSegment = new GroupJoinColumnSegmentImpl(groupJoinPredicateSegmentContext, thenSegment, elseSegment);
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> f.anySQLFunction("{0}",c->{
            c.sql(groupJoinColumnSegment);
        }), resultClass);
    }
}
