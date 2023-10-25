package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/10/22 12:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyTreeable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientProxyQueryableAvailable<T1>, Proxy1Available<T1Proxy, T1> {

    default ProxyQueryable<T1Proxy, T1> asTreeCTE(SQLColumn<T1Proxy, ?> codeProperty, SQLColumn<T1Proxy, ?> parentCodeProperty) {
        return asTreeCTE(codeProperty, parentCodeProperty, o -> {
        });
    }

    default ProxyQueryable<T1Proxy, T1> asTreeCTE(SQLColumn<T1Proxy, ?> codeProperty, SQLColumn<T1Proxy, ?> parentCodeProperty, SQLExpression1<TreeCTEConfigurer> treeExpression) {
        ClientQueryable<T1> clientQueryable = getClientQueryable().asTreeCTE(codeProperty.value(), parentCodeProperty.value(), treeExpression);
        return new EasyProxyQueryable<>(get1Proxy(), clientQueryable);
    }
}
