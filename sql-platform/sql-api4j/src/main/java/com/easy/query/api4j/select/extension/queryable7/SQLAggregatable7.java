package com.easy.query.api4j.select.extension.queryable7;

import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

import java.math.BigDecimal;

/**
 * create time 2023/8/17 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAggregatable7<T1,T2,T3,T4,T5,T6,T7> extends ClientQueryable7Available<T1,T2,T3,T4,T5,T6,T7> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression) {
        return getClientQueryable7().sumBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable7().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression) {
        return getClientQueryable7().sumOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable7().sumOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression) {
        return getClientQueryable7().maxOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable7().maxOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression) {
        return getClientQueryable7().minOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable7().minOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression) {
        return getClientQueryable7().avgOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression) {
        return getClientQueryable7().avgBigDecimalOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression) {
        return getClientQueryable7().avgFloatOrNull((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable7().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable7().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable7().avgFloatOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable7().avgOrDefault((selector1, selector2, selector3, selector4, selector5, selector6, selector7) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3), new SQLColumnResultSelectorImpl<>(selector4), new SQLColumnResultSelectorImpl<>(selector5), new SQLColumnResultSelectorImpl<>(selector6), new SQLColumnResultSelectorImpl<>(selector7));
        }, def, resultClass);
    }




    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression) {
        return getClientQueryable7().sumBigDecimalOrNullMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable7().sumBigDecimalOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression) {
        return getClientQueryable7().sumOrNullMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable7().sumOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression) {
        return getClientQueryable7().maxOrNullMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable7().maxOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression) {
        return getClientQueryable7().minOrNullMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable7().minOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression) {
        return getClientQueryable7().avgOrNullMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression) {
        return getClientQueryable7().avgBigDecimalOrNullMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression) {
        return getClientQueryable7().avgFloatOrNullMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable7().avgOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable7().avgBigDecimalOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable7().avgFloatOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple7<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>, SQLColumnResultSelector<T4, TMember>, SQLColumnResultSelector<T5, TMember>, SQLColumnResultSelector<T6, TMember>, SQLColumnResultSelector<T7, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable7().avgOrDefaultMerge((tuple7) -> {
            columnSelectorExpression.apply(new Tuple7<>(new SQLColumnResultSelectorImpl<>(tuple7.t()), new SQLColumnResultSelectorImpl<>(tuple7.t1()), new SQLColumnResultSelectorImpl<>(tuple7.t2()), new SQLColumnResultSelectorImpl<>(tuple7.t3()), new SQLColumnResultSelectorImpl<>(tuple7.t4()), new SQLColumnResultSelectorImpl<>(tuple7.t5()),new SQLColumnResultSelectorImpl<>(tuple7.t6())));
        }, def, resultClass);
    }
}
