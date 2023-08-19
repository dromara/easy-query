package com.easy.query.api4j.select.extension.queryable2;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectable2<T1,T2> extends ClientQueryable2Available<T1,T2> {

   default  <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>> selectExpression){
       ClientQueryable<TR> select = getClientQueryable2().select(resultClass, (t1, t2) -> {
           selectExpression.apply(new SQLColumnAsSelectorImpl<>(t1),new SQLColumnAsSelectorImpl<>(t2));
       });
       return new EasyQueryable<>(select);
   }

    default <TR> Queryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple2<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>>> selectExpression) {
        return select(resultClass, (t1, t2) -> {
            selectExpression.apply(new Tuple2<>(t1, t2));
        });
    }
}
