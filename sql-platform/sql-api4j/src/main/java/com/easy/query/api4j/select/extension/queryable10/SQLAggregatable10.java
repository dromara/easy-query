package com.easy.query.api4j.select.extension.queryable10;

import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;

import java.math.BigDecimal;

/**
 * create time 2023/8/17 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregatable10<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> extends ClientQueryable10Available<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression) {
        return getClientQueryable10().sumBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable10().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression) {
        return getClientQueryable10().sumOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable10().sumOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression) {
        return getClientQueryable10().maxOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable10().maxOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression) {
        return getClientQueryable10().minOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable10().minOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression) {
        return getClientQueryable10().avgOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression) {
        return getClientQueryable10().avgBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression) {
        return getClientQueryable10().avgFloatOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable10().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable10().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable10().avgFloatOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable10().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9, selector10) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9), new SQLColumnResultSelectorImpl<>(selector10));
        }, def, resultClass);
    }




    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression) {
        return getClientQueryable10().sumBigDecimalOrNullMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable10().sumBigDecimalOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression) {
        return getClientQueryable10().sumOrNullMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable10().sumOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression) {
        return getClientQueryable10().maxOrNullMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable10().maxOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression) {
        return getClientQueryable10().minOrNullMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable10().minOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression) {
        return getClientQueryable10().avgOrNullMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression) {
        return getClientQueryable10().avgBigDecimalOrNullMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression) {
        return getClientQueryable10().avgFloatOrNullMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable10().avgOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable10().avgBigDecimalOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable10().avgFloatOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple10<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>, SQLColumnResultSelector<T10, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable10().avgOrDefaultMerge((tuple10) -> {
            columnSelectorExpression.apply(new Tuple10<>(new SQLColumnResultSelectorImpl<>(tuple10.t()), new SQLColumnResultSelectorImpl<>(tuple10.t1()), new SQLColumnResultSelectorImpl<>(tuple10.t2()), new SQLColumnResultSelectorImpl<>(tuple10.t3()), new SQLColumnResultSelectorImpl<>(tuple10.t4()), new SQLColumnResultSelectorImpl<>(tuple10.t5()), new SQLColumnResultSelectorImpl<>(tuple10.t6()), new SQLColumnResultSelectorImpl<>(tuple10.t7()), new SQLColumnResultSelectorImpl<>(tuple10.t8()), new SQLColumnResultSelectorImpl<>(tuple10.t9())));
        }, def, resultClass);
    }
}
