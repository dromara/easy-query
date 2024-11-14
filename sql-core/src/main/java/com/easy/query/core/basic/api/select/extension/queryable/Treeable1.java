package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;

/**
 * create time 2023/10/22 12:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Treeable1<T1> {

    default ClientQueryable<T1> asTreeCTE(){
        return asTreeCTE(o->{});
    }
    ClientQueryable<T1> asTreeCTE(SQLExpression1<TreeCTEConfigurer> treeExpression);
}
