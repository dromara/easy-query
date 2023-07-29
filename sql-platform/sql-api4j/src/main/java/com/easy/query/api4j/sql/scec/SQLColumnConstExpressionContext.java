package com.easy.query.api4j.sql.scec;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnConstExpressionContext<T1> {
    SQLColumnConstExpressionContext<T1> expression(Property<T1,?> property);
   <T2> SQLColumnConstExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, Property<T2,?> property);
    SQLColumnConstExpressionContext<T1> value(Object val);
    SQLColumnConstExpressionContext<T1> setAlias(String alias);
}
