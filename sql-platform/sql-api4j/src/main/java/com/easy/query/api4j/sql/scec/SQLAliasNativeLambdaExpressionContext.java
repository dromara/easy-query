package com.easy.query.api4j.sql.scec;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAliasNativeLambdaExpressionContext<T1,TR> extends SQLNativeLambdaExpressionChain<T1,SQLAliasNativeLambdaExpressionContext<T1,TR>>{

    SQLAliasNativeLambdaExpressionContext<T1,TR> expressionAlias(Property<TR, ?> property);
    SQLAliasNativeLambdaExpressionContext<T1,TR> setPropertyAlias(Property<TR, ?> property);

}
