package com.easy.query.core.proxy.grouping;

import com.easy.query.api.proxy.extension.casewhen.PredicateCaseWhenBuilder;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContextImpl;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.NumberTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.impl.StringTypeExpressionImpl;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.math.BigDecimal;

/**
 * create time 2025/3/5 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLGroupQueryable<TProxy> implements SQLGroupQueryable<TProxy> {
    protected final SQLActionExpression1<TProxy> predicate;
    protected final TProxy groupTable;
    protected final EntitySQLContext entitySQLContext;
    protected final GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext;
    protected boolean distinct = false;

    public DefaultSQLGroupQueryable(TProxy groupTable, EntitySQLContext entitySQLContext, SQLActionExpression1<TProxy> predicate) {
        this.groupTable = groupTable;
        this.entitySQLContext = entitySQLContext;
        this.predicate = predicate;
        PredicateSegment predicateSegment = predicate == null ? null : EasySQLExpressionUtil.resolve(entitySQLContext.getRuntimeContext(), entitySQLContext.getExpressionContext(), filter -> {
            entitySQLContext._where(filter, () -> {
                predicate.apply(groupTable);
            });
        });
        this.groupJoinPredicateSegmentContext=new GroupJoinPredicateSegmentContextImpl(predicateSegment);
    }

    public GroupJoinPredicateSegmentContext getGroupJoinPredicateSegmentContext() {
        return groupJoinPredicateSegmentContext;
    }

    @Override
    public SQLGroupQueryable<TProxy> distinct(boolean useDistinct) {
        this.distinct = useDistinct;
        return this;
    }

    @Override
    public NumberTypeExpression<Long> count() {
        if (predicate == null) {
            return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), null, null, fx -> {
                return fx.count(x -> {
                }).distinct(distinct);
            }, Long.class);
        } else {

            PropTypeColumn<?> preColumn = new PredicateCaseWhenBuilder(this.getEntitySQLContext(), groupJoinPredicateSegmentContext).then(1).elseEnd(null);
//            PropTypeColumn<?> preColumn = new PredicateCaseWhenBuilder(this.getEntitySQLContext(), predicateSegment).then(1).elseEnd(null, Long.class);
            return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), preColumn.getTable(), preColumn.getValue(), fx -> {
                return fx.count(x -> {
                    PropTypeColumn.columnFuncSelector(x, preColumn);
                }).distinct(distinct);
            }, Long.class);
        }
    }
    @Override
    public <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector) {
        PropTypeColumn<TMember> column = columnSelector.apply(groupTable);
        PropTypeColumn<?> preColumn = predicate == null ? column : new PredicateCaseWhenBuilder(this.getEntitySQLContext(), groupJoinPredicateSegmentContext).then(column).elseEnd(null, Long.class);
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            }).distinct(distinct);
        }, Long.class);
    }

    @Override
    public <TMember> NumberTypeExpression<Integer> intCount(SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector) {
        return count(columnSelector).asAnyType(Integer.class);
    }

    @Override
    public NumberTypeExpression<Integer> intCount() {
        return count().asAnyType(Integer.class);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<TMember> sum(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnNumberFunctionAvailable<TMember> column = columnSelector.apply(groupTable);
        PropTypeColumn<?> preColumn = predicate == null ? column : new PredicateCaseWhenBuilder(this.getEntitySQLContext(), groupJoinPredicateSegmentContext).then(column).elseEnd(0, column.getPropertyType());
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.sum(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            }).distinct(distinct);
        }, column.getPropertyType());
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        NumberTypeExpression<TMember> sum = sum(columnSelector);
        sum._setPropertyType(BigDecimal.class);
        return EasyObjectUtil.typeCastNullable(sum);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<Integer> sumInt(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        NumberTypeExpression<TMember> sum = sum(columnSelector);
        sum._setPropertyType(Integer.class);
        return EasyObjectUtil.typeCastNullable(sum);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<Long> sumLong(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        NumberTypeExpression<TMember> sum = sum(columnSelector);
        sum._setPropertyType(Long.class);
        return EasyObjectUtil.typeCastNullable(sum);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<TProxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        ColumnNumberFunctionAvailable<TMember> column = columnSelector.apply(groupTable);
        PropTypeColumn<?> preColumn = predicate == null ? column : new PredicateCaseWhenBuilder(this.getEntitySQLContext(), groupJoinPredicateSegmentContext).then(column).elseEnd(null, column.getPropertyType());
        return new NumberTypeExpressionImpl<>(this.getEntitySQLContext(), preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.avg(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            }).distinct(distinct);
        }, BigDecimal.class);
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> max(SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector) {
        PropTypeColumn<TMember> column = columnSelector.apply(groupTable);
        PropTypeColumn<?> preColumn = predicate == null ? column : new PredicateCaseWhenBuilder(this.getEntitySQLContext(), groupJoinPredicateSegmentContext).then(column).elseEnd(null, column.getPropertyType());
        return new AnyTypeExpressionImpl<>(this.getEntitySQLContext(), preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.max(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            });
        }, column.getPropertyType());
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> min(SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector) {
        PropTypeColumn<TMember> column = columnSelector.apply(groupTable);
        PropTypeColumn<?> preColumn = predicate == null ? column : new PredicateCaseWhenBuilder(this.getEntitySQLContext(), groupJoinPredicateSegmentContext).then(column).elseEnd(null, column.getPropertyType());
        return new AnyTypeExpressionImpl<>(this.getEntitySQLContext(), preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.min(x -> {
                PropTypeColumn.columnFuncSelector(x, preColumn);
            });
        }, column.getPropertyType());
    }

    @Override
    public <TMember> StringTypeExpression<String> joining(SQLFuncExpression1<TProxy, PropTypeColumn<TMember>> columnSelector, String delimiter) {
        PropTypeColumn<TMember> column = columnSelector.apply(groupTable);
        PropTypeColumn<?> preColumn = predicate == null ? column : new PredicateCaseWhenBuilder(this.getEntitySQLContext(), groupJoinPredicateSegmentContext).then(column).elseEnd(null, column.getPropertyType());
        return new StringTypeExpressionImpl<>(this.getEntitySQLContext(), preColumn.getTable(), preColumn.getValue(), fx -> {
            return fx.joining(x -> {
                x.value(delimiter);
                PropTypeColumn.columnFuncSelector(x, preColumn);
            }, distinct);
        }, column.getPropertyType());
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }


}
