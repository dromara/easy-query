package com.easy.query.api4kt.select.extension.queryable6;

import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

import java.math.BigDecimal;

/**
 * create time 2023/8/17 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAggregatable6<T1,T2,T3,T4,T5,T6> extends ClientKtQueryable6Available<T1,T2,T3,T4,T5,T6> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression) {
        return getClientQueryable6().sumBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable6().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression) {
        return getClientQueryable6().sumOrNull((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable6().sumOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression) {
        return getClientQueryable6().maxOrNull((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable6().maxOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression) {
        return getClientQueryable6().minOrNull((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable6().minOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression) {
        return getClientQueryable6().avgOrNull((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression) {
        return getClientQueryable6().avgBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression) {
        return getClientQueryable6().avgFloatOrNull((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable6().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable6().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable6().avgFloatOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable6().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5), new SQLKtColumnResultSelectorImpl<>(selector6));
        }, def, resultClass);
    }




    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression) {
        return getClientQueryable6().sumBigDecimalOrNullMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable6().sumBigDecimalOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression) {
        return getClientQueryable6().sumOrNullMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable6().sumOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression) {
        return getClientQueryable6().maxOrNullMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable6().maxOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression) {
        return getClientQueryable6().minOrNullMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable6().minOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression) {
        return getClientQueryable6().avgOrNullMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression) {
        return getClientQueryable6().avgBigDecimalOrNullMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression) {
        return getClientQueryable6().avgFloatOrNullMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable6().avgOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable6().avgBigDecimalOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable6().avgFloatOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple6<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>, SQLKtColumnResultSelector<T6, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable6().avgOrDefaultMerge((tuple6) -> {
            columnSelectorExpression.apply(new Tuple6<>(new SQLKtColumnResultSelectorImpl<>(tuple6.t1()), new SQLKtColumnResultSelectorImpl<>(tuple6.t2()), new SQLKtColumnResultSelectorImpl<>(tuple6.t3()), new SQLKtColumnResultSelectorImpl<>(tuple6.t4()), new SQLKtColumnResultSelectorImpl<>(tuple6.t5()),new SQLKtColumnResultSelectorImpl<>(tuple6.t6())));
        }, def, resultClass);
    }
}
