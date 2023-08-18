package com.easy.query.api4kt.select.extension.queryable2;

import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAggregatable2<T1, T2> extends ClientKtQueryable2Available<T1, T2> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().sumBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().sumOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().sumOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().maxOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().maxOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().minOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().minOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgBigDecimalOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression) {
        return getClientQueryable2().avgFloatOrNull((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().avgBigDecimalOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable2().avgFloatOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable2().avgOrDefault((selector1, selector2) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2));
        }, def, resultClass);
    }


    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression) {
        return getClientQueryable2().sumBigDecimalOrNullMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().sumBigDecimalOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression) {
        return getClientQueryable2().sumOrNullMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().sumOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression) {
        return getClientQueryable2().maxOrNullMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().maxOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression) {
        return getClientQueryable2().minOrNullMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable2().minOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression) {
        return getClientQueryable2().avgOrNullMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression) {
        return getClientQueryable2().avgBigDecimalOrNullMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression) {
        return getClientQueryable2().avgFloatOrNullMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable2().avgOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable2().avgBigDecimalOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable2().avgFloatOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple2<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable2().avgOrDefaultMerge((tuple2) -> {
            columnSelectorExpression.apply(new Tuple2<>(new SQLKtColumnResultSelectorImpl<>(tuple2.t1()), new SQLKtColumnResultSelectorImpl<>(tuple2.t2())));
        }, def, resultClass);
    }
}
