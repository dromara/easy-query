package com.easy.query.core.proxy.sql.include;


import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;

/**
 * create time 2025/3/3 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeManyAvailable<TProxy> extends MappingPath, EntitySQLContextAvailable {
    default void include() {
    }
    default void includeFilter( SQLExpression1<TProxy> whereExpression) {
    }
    default void includeFilter(boolean condition, SQLExpression1<TProxy> whereExpression) {
        if (condition) {
        }
    }
}
