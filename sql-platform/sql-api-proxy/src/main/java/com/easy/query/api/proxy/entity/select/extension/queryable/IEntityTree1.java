package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.extension.tree.EntityTreeCTEConfigurer;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2024/1/13 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IEntityTree1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

    default EntityQueryable<T1Proxy, T1> asTreeCTE() {
        return asTreeCTE(o -> {
        });
    }

    EntityQueryable<T1Proxy, T1> asTreeCTE(SQLActionExpression1<EntityTreeCTEConfigurer<T1Proxy,T1>> treeExpression);

    default EntityQueryable<T1Proxy, T1> asTreeCTECustom(SQLFuncExpression1<T1Proxy,SQLColumn<T1Proxy, ?>> codePropertyExpression, SQLFuncExpression1<T1Proxy,SQLColumn<T1Proxy, ?>> parentCodePropertyExpression) {
        return asTreeCTECustom(codePropertyExpression, parentCodePropertyExpression, o -> {
        });
    }

    EntityQueryable<T1Proxy, T1> asTreeCTECustom(SQLFuncExpression1<T1Proxy,SQLColumn<T1Proxy, ?>> codePropertyExpression, SQLFuncExpression1<T1Proxy,SQLColumn<T1Proxy, ?>> parentCodePropertyExpression, SQLActionExpression1<TreeCTEConfigurer> treeExpression);

}
