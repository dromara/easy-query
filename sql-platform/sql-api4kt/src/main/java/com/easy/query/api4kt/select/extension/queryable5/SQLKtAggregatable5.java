package com.easy.query.api4kt.select.extension.queryable5;

import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;

import java.math.BigDecimal;

/**
 * create time 2023/8/17 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAggregatable5<T1,T2,T3,T4,T5> extends ClientKtQueryable5Available<T1,T2,T3,T4,T5> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression) {
        return getClientQueryable5().sumBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable5().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression) {
        return getClientQueryable5().sumOrNull((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable5().sumOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression) {
        return getClientQueryable5().maxOrNull((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable5().maxOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression) {
        return getClientQueryable5().minOrNull((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable5().minOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression) {
        return getClientQueryable5().avgOrNull((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression) {
        return getClientQueryable5().avgBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression) {
        return getClientQueryable5().avgFloatOrNull((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable5().avgOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable5().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable5().avgFloatOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable5().avgOrDefault((selector1, selector2, selector3, selector4, selector5) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4), new SQLKtColumnResultSelectorImpl<>(selector5));
        }, def, resultClass);
    }




    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression) {
        return getClientQueryable5().sumBigDecimalOrNullMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable5().sumBigDecimalOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression) {
        return getClientQueryable5().sumOrNullMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable5().sumOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression) {
        return getClientQueryable5().maxOrNullMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable5().maxOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression) {
        return getClientQueryable5().minOrNullMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable5().minOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression) {
        return getClientQueryable5().avgOrNullMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression) {
        return getClientQueryable5().avgBigDecimalOrNullMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression) {
        return getClientQueryable5().avgFloatOrNullMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable5().avgOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable5().avgBigDecimalOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable5().avgFloatOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple5<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>, SQLKtColumnResultSelector<T5, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable5().avgOrDefaultMerge((tuple5) -> {
            columnSelectorExpression.apply(new Tuple5<>(new SQLKtColumnResultSelectorImpl<>(tuple5.t1()), new SQLKtColumnResultSelectorImpl<>(tuple5.t2()), new SQLKtColumnResultSelectorImpl<>(tuple5.t3()), new SQLKtColumnResultSelectorImpl<>(tuple5.t4()),new SQLKtColumnResultSelectorImpl<>(tuple5.t5())));
        }, def, resultClass);
    }
}
