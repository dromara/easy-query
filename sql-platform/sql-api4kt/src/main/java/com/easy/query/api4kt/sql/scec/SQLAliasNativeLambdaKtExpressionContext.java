package com.easy.query.api4kt.sql.scec;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAliasNativeLambdaKtExpressionContext<T1,TR> extends SQLNativeLambdaKtExpressionChain<T1,SQLAliasNativeLambdaKtExpressionContext<T1,TR>> {

    SQLAliasNativeLambdaKtExpressionContext<T1,TR> expressionAlias(KProperty1<? super TR, ?> property);
    SQLAliasNativeLambdaKtExpressionContext<T1,TR> setPropertyAlias(KProperty1<? super TR, ?> property);
}
