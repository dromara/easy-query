package com.easy.query.api4kt.sql.scec;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtColumnConstExpressionContext<T1> {
    SQLKtColumnConstExpressionContext<T1> expression(KProperty1<T1,?> property);
   <T2> SQLKtColumnConstExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, KProperty1<T2,?> property);
    SQLKtColumnConstExpressionContext<T1> value(Object val);
    SQLKtColumnConstExpressionContext<T1> setAlias(String alias);
}
