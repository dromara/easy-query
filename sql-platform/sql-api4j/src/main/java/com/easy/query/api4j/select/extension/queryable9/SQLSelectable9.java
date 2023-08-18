package com.easy.query.api4j.select.extension.queryable9;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression9<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>, SQLColumnAsSelector<T5, TR>, SQLColumnAsSelector<T6, TR>, SQLColumnAsSelector<T7, TR>, SQLColumnAsSelector<T8, TR>, SQLColumnAsSelector<T9, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable9().select(resultClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(t1), new SQLColumnAsSelectorImpl<>(t2), new SQLColumnAsSelectorImpl<>(t3), new SQLColumnAsSelectorImpl<>(t4), new SQLColumnAsSelectorImpl<>(t5), new SQLColumnAsSelectorImpl<>(t6), new SQLColumnAsSelectorImpl<>(t7), new SQLColumnAsSelectorImpl<>(t8), new SQLColumnAsSelectorImpl<>(t9));
        });
        return new EasyQueryable<>(select);
    }

    default <TR> Queryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple9<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>, SQLColumnAsSelector<T5, TR>, SQLColumnAsSelector<T6, TR>, SQLColumnAsSelector<T7, TR>, SQLColumnAsSelector<T8, TR>, SQLColumnAsSelector<T9, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
