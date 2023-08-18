package com.easy.query.api4kt.select.extension.queryable8;

import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;

import java.math.BigDecimal;

/**
 * create time 2023/8/17 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAggregatable8<T1,T2,T3,T4,T5,T6,T7,T8> extends ClientKtQueryable8Available<T1,T2,T3,T4,T5,T6,T7,T8> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression) {
        return getClientQueryable8().sumBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable8().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression) {
        return getClientQueryable8().sumOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable8().sumOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression) {
        return getClientQueryable8().maxOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable8().maxOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression) {
        return getClientQueryable8().minOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable8().minOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression) {
        return getClientQueryable8().avgOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression) {
        return getClientQueryable8().avgBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression) {
        return getClientQueryable8().avgFloatOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable8().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable8().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable8().avgFloatOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable8().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7, selector8) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6), new SQLKtColumnResultSelectorImpl<>(selector7), new SQLKtColumnResultSelectorImpl<>(selector8));
        }, def, resultClass);
    }




    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression) {
        return getClientQueryable8().sumBigDecimalOrNullMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable8().sumBigDecimalOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression) {
        return getClientQueryable8().sumOrNullMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable8().sumOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression) {
        return getClientQueryable8().maxOrNullMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable8().maxOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression) {
        return getClientQueryable8().minOrNullMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable8().minOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression) {
        return getClientQueryable8().avgOrNullMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression) {
        return getClientQueryable8().avgBigDecimalOrNullMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression) {
        return getClientQueryable8().avgFloatOrNullMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable8().avgOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable8().avgBigDecimalOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable8().avgFloatOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple8<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>, SQLKtColumnResultSelector<T7, TMember>, SQLKtColumnResultSelector<T8, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable8().avgOrDefaultMerge((tuple8) -> {
            columnSelectorExpression.apply(new Tuple8<>(new SQLKtColumnResultSelectorImpl<>(tuple8.t1()), new SQLKtColumnResultSelectorImpl<>(tuple8.t2()), new SQLKtColumnResultSelectorImpl<>(tuple8.t3()), new SQLKtColumnResultSelectorImpl<>(tuple8.t4()), new SQLKtColumnResultSelectorImpl<>(tuple8.t5()), new SQLKtColumnResultSelectorImpl<>(tuple8.t6()), new SQLKtColumnResultSelectorImpl<>(tuple8.t7()),new SQLKtColumnResultSelectorImpl<>(tuple8.t8())));
        }, def, resultClass);
    }
}
