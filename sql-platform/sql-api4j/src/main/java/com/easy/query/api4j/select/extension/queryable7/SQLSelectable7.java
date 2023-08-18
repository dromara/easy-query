package com.easy.query.api4j.select.extension.queryable7;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;

/**
 * create time 2023/8/18 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectable7<T1, T2, T3, T4, T5, T6, T7> extends ClientQueryable7Available<T1, T2, T3, T4, T5, T6, T7> {

    default <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression7<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>, SQLColumnAsSelector<T5, TR>, SQLColumnAsSelector<T6, TR>, SQLColumnAsSelector<T7, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable7().select(resultClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(t), new SQLColumnAsSelectorImpl<>(t1), new SQLColumnAsSelectorImpl<>(t2), new SQLColumnAsSelectorImpl<>(t3), new SQLColumnAsSelectorImpl<>(t4), new SQLColumnAsSelectorImpl<>(t5), new SQLColumnAsSelectorImpl<>(t6));
        });
        return new EasyQueryable<>(select);
    }

    default <TR> Queryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple7<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>, SQLColumnAsSelector<T5, TR>, SQLColumnAsSelector<T6, TR>, SQLColumnAsSelector<T7, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}
