package com.easy.query.core.proxy.extra;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;

/**
 * create time 2025/4/16 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityExtraAutoIncludeConfigure<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends ExtraAutoIncludeConfigure {

    /**
     * 额外配置比如将子查询转成隐式group
     * @param queryableConfigureExpression
     * @return
     */
    EntityExtraAutoIncludeConfigure<TProxy,TEntity> configure(SQLActionExpression1<EntityQueryable<TProxy,TEntity>> queryableConfigureExpression);

    /**
     * 不接受属性在实体上的配置比如:orderByProps、limit、offset等
     * @return
     */
    EntityExtraAutoIncludeConfigure<TProxy,TEntity> ignoreNavigateConfigure();

    /**
     * 额外的筛选
     * @param whereExpression
     * @return
     */
    EntityExtraAutoIncludeConfigure<TProxy,TEntity> where(SQLActionExpression1<TProxy> whereExpression);

    /**
     * 额外的查询
     * @param selectExpression
     * @return
     */
    EntityExtraAutoIncludeConfigure<TProxy,TEntity> select(SQLFuncExpression1<TProxy, SQLSelectAsExpression> selectExpression);
}
