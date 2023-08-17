package com.easy.query.api4j.select.extension.queryable2;

import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression2;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregatable2<T1,T2> extends ClientQueryable2Available<T1,T2>{

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().sumBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().sumOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().maxOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().maxOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().minOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().minOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgFloatOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().avgBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable2().avgFloatOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression2<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2));
        }, def, resultClass);
    }
}
