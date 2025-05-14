package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
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
    ClientQueryable<T1> asTreeCTE(SQLActionExpression1<TreeCTEConfigurer> treeExpression);
    default ClientQueryable<T1> asTreeCTECustom(String codeProperty,String parentCodeProperty){
        return asTreeCTECustom(codeProperty,parentCodeProperty,o->{});
    }
    ClientQueryable<T1> asTreeCTECustom(String codeProperty, String parentCodeProperty, SQLActionExpression1<TreeCTEConfigurer> treeExpression);
}
