package com.easy.query.core.proxy.extension.functions;

import com.easy.query.api.proxy.extension.window.NextSelector;
import com.easy.query.api.proxy.extension.window.OverExpression;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.filter.NumberFilterTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.extension.functions.type.filter.impl.NumberFilterTypeExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * create time 2023/12/24 00:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnObjectFunctionAvailable<TProperty, TChain> extends SQLSelectAsExpression, PropTypeColumn<TProperty> {
    TChain createChainExpression(Function<SQLFunc, SQLFunction> func, Class<?> propType);

    default NumberFilterTypeExpression<Long> count() {
        return count(false);
    }

    default NumberFilterTypeExpression<Long> count(boolean distinct) {
        return new NumberFilterTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this, this.getTable(), this.getValue(), (self, fx) -> {
            return fx.count(x -> {
                PropTypeColumn.columnFuncSelector(x, self);
            }).distinct(distinct);
        }, Long.class);
    }

    default NumberFilterTypeExpression<Integer> intCount() {
        return intCount(false);
    }

    default NumberFilterTypeExpression<Integer> intCount(boolean distinct) {
        return count(distinct).asAnyType(Integer.class);
    }

    default TChain max() {
        return createChainExpression(fx -> {
            return fx.max(s -> {
                PropTypeColumn.acceptAnyValue(s, this);
            });
        }, getPropertyType());
    }

    default TChain min() {
        return createChainExpression(fx -> {
            return fx.min(s -> {
                PropTypeColumn.acceptAnyValue(s, this);
            });
        }, getPropertyType());
    }

    default TChain nullOrDefault(TProperty value) {
        return nullOrDefault(o -> o.value(_toFunctionSerializeValue(value)));
    }
    default TChain nullOrDefaultFormat(Object value) {
        return nullOrDefault(o ->o.format(value));
    }

    default TChain nullOrDefault(PropTypeColumn<TProperty> propTypeColumn) {
        return nullOrDefault(x -> {
            PropTypeColumn.columnFuncSelector(x.getColumnFuncSelector(), propTypeColumn);
        });
    }

    default TChain nullOrDefault(SQLActionExpression1<ProxyColumnFuncSelector> selector) {
        return createChainExpression(fx -> {
            return fx.nullOrDefault(o -> {
                PropTypeColumn.acceptAnyValue(o, this);
                selector.apply(new ProxyColumnFuncSelectorImpl(o));
            });
        }, getPropertyType());
    }

    default NextSelector<TProperty, TChain> offset() {
        return offset(o -> {});
    }

    default NextSelector<TProperty, TChain> offset(SQLActionExpression1<OverExpression> orderByExpression) {
        Class<?> propertyType = getPropertyType();
        return new NextSelector<>(offset -> {
            return createChainExpression(fx -> {

                ArrayList<SQLFunction> sorts = new ArrayList<>();
                ArrayList<PropTypeColumn<?>> partitions = new ArrayList<>();
                OverExpression overExpression = new OverExpression(fx, sorts, partitions);
                orderByExpression.apply(overExpression);
                int i = 1;
                StringBuilder nextSQL = new StringBuilder(offset.getSQLFunction());
                nextSQL.append("({0}");
                int offsetVal = offset.getOffset();
                if (offsetVal >= 0) {
                    nextSQL.append(", ");
                    nextSQL.append(offsetVal);
                }
                boolean defaultValueIsNull = offset.getDefaultValue() == null && offset.getDefaultColumn() == null;
                if (!defaultValueIsNull) {
                    nextSQL.append(", ").append("{").append(i++).append("}");
                }
                nextSQL.append(") OVER (");
                if (EasyCollectionUtil.isNotEmpty(partitions)) {
                    nextSQL.append("PARTITION BY ");
                    nextSQL.append("{").append(i++).append("}");
                    for (int i1 = 0; i1 < partitions.size() - 1; i1++) {
                        nextSQL.append(",{").append(i++).append("}");
                    }
                }
                if (EasyCollectionUtil.isEmpty(sorts)) {
                    EntityQueryExpressionBuilder entityQueryExpressionBuilder = (EntityQueryExpressionBuilder) getEntitySQLContext().getEntityExpressionBuilder();
                    OrderBySQLBuilderSegment order = entityQueryExpressionBuilder.getOrder();
                    if (EasySQLSegmentUtil.isEmpty(order)) {
                        throw new EasyQueryInvalidOperationException("In a PARTITION BY clause, the ORDER BY expression must be explicitly specified; otherwise, referencing the expression is not supported. plz confirm expression has ORDER BY clause");
                    }
                }
                nextSQL.append("ORDER BY ");
                nextSQL.append("{").append(i++).append("}");
                for (int i1 = 0; i1 < sorts.size() - 1; i1++) {
                    nextSQL.append(",{").append(i++).append("}");
                }
                nextSQL.append(")");

                return fx.anySQLFunction(nextSQL.toString(), x -> {
                    PropTypeColumn.acceptAnyValue(x, this);
                    if (!defaultValueIsNull) {
                        if (offset.getDefaultValue() != null) {
                            PropTypeColumn.acceptAnyValue(x, offset.getDefaultValue());
                        } else if (offset.getDefaultColumn() != null) {
                            PropTypeColumn.acceptAnyValue(x, offset.getDefaultColumn());
                        }
                    }
                    for (PropTypeColumn<?> partition : partitions) {
                        PropTypeColumn.acceptAnyValue(x, partition);
                    }
                    if (EasyCollectionUtil.isEmpty(sorts)) {
                        EntityQueryExpressionBuilder entityQueryExpressionBuilder = (EntityQueryExpressionBuilder) getEntitySQLContext().getEntityExpressionBuilder();
                        OrderBySQLBuilderSegment order = entityQueryExpressionBuilder.getOrder();
                        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
                        order.copyTo(orderBySQLBuilderSegment);
                        PropTypeColumn.acceptAnyValue(x, orderBySQLBuilderSegment);
                    } else {
                        for (SQLFunction sqlFunction : sorts) {
                            PropTypeColumn.acceptAnyValue(x, sqlFunction);
                        }
                    }
                });

            }, propertyType);
        });
    }

    /**
     * 请使用{@link Expression#valueOf(SQLActionExpression)}
     * please use {@link Expression#valueOf(SQLActionExpression)}
     *
     * @param value
     * @return
     */
    @Deprecated
    default BooleanTypeExpression<Boolean> equalsWith(TProperty value) {
        return equalsWith(x -> x.value(_toFunctionSerializeValue(value)));
    }

    /**
     * 请使用{@link Expression#valueOf(SQLActionExpression)}
     * please use {@link Expression#valueOf(SQLActionExpression)}
     *
     * @param propTypeColumn
     * @return
     */
    @Deprecated
    default BooleanTypeExpression<Boolean> equalsWith(PropTypeColumn<TProperty> propTypeColumn) {
        return equalsWith(x -> {
            PropTypeColumn.columnFuncSelector(x.getColumnFuncSelector(), propTypeColumn);
        });
    }

    /**
     * 请使用{@link Expression#valueOf(SQLActionExpression)}
     * please use {@link Expression#valueOf(SQLActionExpression)}
     *
     * @param selector
     * @return
     */
    @Deprecated
    default BooleanTypeExpression<Boolean> equalsWith(SQLActionExpression1<ProxyColumnFuncSelector> selector) {
        return new BooleanTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), fx -> {
            return fx.equalsWith(o -> {
                PropTypeColumn.columnFuncSelector(o, this);
                selector.apply(new ProxyColumnFuncSelectorImpl(o));
            });
        }, Boolean.class);
    }

}
