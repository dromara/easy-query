package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/29 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAliasNativeExpressionContext extends SQLNativeExpressionContext{
    SQLAliasNativeExpressionContext expressionAlias(String property);

    @Override
    SQLAliasNativeExpressionContext expression(TableAvailable table, String property);
    @Override
    <TEntity> SQLAliasNativeExpressionContext expression(Query<TEntity> subQuery);
    @Override
    SQLAliasNativeExpressionContext value(Object val);

    @Override
    SQLAliasNativeExpressionContext format(Object formatVal);

    /**
     * 别名 column_name
     * @param alias
     * @return
     */
    @Override
    SQLAliasNativeExpressionContext setAlias(String alias);
}
