package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/10/22 12:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtTreeable1<T1> extends ClientKtQueryableAvailable<T1>, KtQueryableAvailable<T1> {

    default KtQueryable<T1> asTreeCTE() {
        return asTreeCTE(o -> {});
    }

    default KtQueryable<T1> asTreeCTE(SQLExpression1<TreeCTEConfigurer> treeExpression) {
        ClientQueryable<T1> clientQueryable = getClientQueryable().asTreeCTE(treeExpression);
        return new EasyKtQueryable<>(clientQueryable);
    }
}
