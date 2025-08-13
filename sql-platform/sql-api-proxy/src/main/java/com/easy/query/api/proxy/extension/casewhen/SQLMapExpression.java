package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.tuple.Tuple2;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLMapExpression {
    private final SQLCaseWhenBuilder caseWhenBuilder;
    private final EntitySQLContext entitySQLContext;
    private final Expression expression;
    private final List<Tuple2<PropTypeColumn<?>, PropTypeColumn<?>>> mapValues;

    public SQLMapExpression(EntitySQLContext entitySQLContext, Expression expression) {
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        Objects.requireNonNull(entityExpressionBuilder, "CaseWhenEntityBuilder entitySQLContext.getEntityExpressionBuilder() is null");
        this.caseWhenBuilder = entitySQLContext.getRuntimeContext().getSQLCaseWhenBuilderFactory().create(entityExpressionBuilder.getExpressionContext());
        this.entitySQLContext = entitySQLContext;
        this.expression = expression;
        this.mapValues = new ArrayList<>();
    }

    public <TKey, TValue> SQLMapExpression put(TKey key, TValue val) {
        this.mapValues.add(new Tuple2<>(
                objectValueToTypeColumn(key),
                objectValueToTypeColumn(val)
        ));
        return this;
    }

    private PropTypeColumn<?> objectValueToTypeColumn(Object val) {
        if (val == null) {
            return expression.constantOfNull();
        }
        if (val instanceof PropTypeColumn) {
            return (PropTypeColumn<?>) val;
        }
        return expression.constant(val, Object.class);
    }

    public <TProperty, TValue> AnyTypeExpression<TProperty> getOrDefault(PropTypeColumn<?> column, TValue value) {
        if (EasyCollectionUtil.isEmpty(mapValues)) {
            throw new EasyQueryInvalidOperationException("newMap expression item is empty.");
        }
        Iterator<Tuple2<PropTypeColumn<?>, PropTypeColumn<?>>> iterator = mapValues.iterator();
        Tuple2<PropTypeColumn<?>, PropTypeColumn<?>> first = iterator.next();
        CaseWhenEntityBuilder caseWhenEntityBuilder = expression.caseWhen(() -> {
            expression.sql("{0} = {1}",c->c.expression(column).expression(first.getValue1()));
        }).then(first.getValue2());
        while (iterator.hasNext()) {
            Tuple2<PropTypeColumn<?>, PropTypeColumn<?>> next = iterator.next();
            caseWhenEntityBuilder.caseWhen(() -> {
                expression.sql("{0} = {1}",c->c.expression(column).expression(next.getValue1()));
            }).then(next.getValue2());
        }
        return caseWhenEntityBuilder.elseEnd(value);
    }


    //    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
    public <TV, TProperty> AnyTypeExpression<TProperty> elseEnd(TV elseValue) {
        return EasyObjectUtil.typeCastNullable(elseEnd(elseValue, Object.class));
    }

    public <TV, TProperty> AnyTypeExpression<TProperty> elseEnd(TV elseValue, Class<TProperty> resultClass) {
        ParamExpression paramExpression = EasyProxyParamExpressionUtil.getParamExpression(entitySQLContext, elseValue);
        SQLFunction sqlFunction = caseWhenBuilder.elseEnd(paramExpression);
        return new AnyTypeExpressionImpl<>(entitySQLContext, null, null, f -> sqlFunction, resultClass);
    }
}