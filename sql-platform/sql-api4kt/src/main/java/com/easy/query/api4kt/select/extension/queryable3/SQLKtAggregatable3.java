package com.easy.query.api4kt.select.extension.queryable3;

import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAggregatable3<T1, T2, T3> extends ClientKtQueryable3Available<T1, T2, T3> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().sumBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().sumOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().sumOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().maxOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().maxOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().minOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().minOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgBigDecimalOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression) {
        return getClientQueryable3().avgFloatOrNull((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().avgBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable3().avgFloatOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable3().avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3));
        }, def, resultClass);
    }


    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression) {
        return getClientQueryable3().sumBigDecimalOrNullMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().sumBigDecimalOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression) {
        return getClientQueryable3().sumOrNullMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().sumOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression) {
        return getClientQueryable3().maxOrNullMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().maxOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression) {
        return getClientQueryable3().minOrNullMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable3().minOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression) {
        return getClientQueryable3().avgOrNullMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression) {
        return getClientQueryable3().avgBigDecimalOrNullMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression) {
        return getClientQueryable3().avgFloatOrNullMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable3().avgOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable3().avgBigDecimalOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable3().avgFloatOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple3<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable3().avgOrDefaultMerge((tuple3) -> {
            columnSelectorExpression.apply(new Tuple3<>(new SQLKtColumnResultSelectorImpl<>(tuple3.t()), new SQLKtColumnResultSelectorImpl<>(tuple3.t1()),new SQLKtColumnResultSelectorImpl<>(tuple3.t2())));
        }, def, resultClass);
    }
}
