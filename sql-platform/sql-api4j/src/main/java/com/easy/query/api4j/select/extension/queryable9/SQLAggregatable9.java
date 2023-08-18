package com.easy.query.api4j.select.extension.queryable9;

import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

import java.math.BigDecimal;

/**
 * create time 2023/8/17 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregatable9<T1,T2,T3,T4,T5,T6,T7,T8,T9> extends ClientQueryable9Available<T1,T2,T3,T4,T5,T6,T7,T8,T9> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression) {
        return getClientQueryable9().sumBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable9().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression) {
        return getClientQueryable9().sumOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable9().sumOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression) {
        return getClientQueryable9().maxOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable9().maxOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression) {
        return getClientQueryable9().minOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable9().minOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression) {
        return getClientQueryable9().avgOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression) {
        return getClientQueryable9().avgBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression) {
        return getClientQueryable9().avgFloatOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable9().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable9().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable9().avgFloatOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable9().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8, selector9) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7), new SQLColumnResultSelectorImpl<>(selector8), new SQLColumnResultSelectorImpl<>(selector9));
        }, def, resultClass);
    }




    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression) {
        return getClientQueryable9().sumBigDecimalOrNullMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable9().sumBigDecimalOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression) {
        return getClientQueryable9().sumOrNullMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable9().sumOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression) {
        return getClientQueryable9().maxOrNullMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable9().maxOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression) {
        return getClientQueryable9().minOrNullMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable9().minOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression) {
        return getClientQueryable9().avgOrNullMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression) {
        return getClientQueryable9().avgBigDecimalOrNullMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression) {
        return getClientQueryable9().avgFloatOrNullMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable9().avgOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable9().avgBigDecimalOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable9().avgFloatOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple9<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>, SQLColumnResultSelector<T8, TMember>, SQLColumnResultSelector<T9, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable9().avgOrDefaultMerge((tuple9) -> {
            columnSelectorExpression.apply(new Tuple9<>(new SQLColumnResultSelectorImpl<>(tuple9.t()), new SQLColumnResultSelectorImpl<>(tuple9.t1()), new SQLColumnResultSelectorImpl<>(tuple9.t2()), new SQLColumnResultSelectorImpl<>(tuple9.t3()), new SQLColumnResultSelectorImpl<>(tuple9.t4()), new SQLColumnResultSelectorImpl<>(tuple9.t5()), new SQLColumnResultSelectorImpl<>(tuple9.t6()), new SQLColumnResultSelectorImpl<>(tuple9.t7()), new SQLColumnResultSelectorImpl<>(tuple9.t8())));
        }, def, resultClass);
    }
}
