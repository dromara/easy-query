package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryableAvailable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEConfigurer;

/**
 * create time 2023/10/22 12:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLTreeable1<T1> extends ClientQueryableAvailable<T1>, QueryableAvailable<T1>{

    default Queryable<T1> asTreeCTE(Property<T1,?> codeProperty, Property<T1,?> parentCodeProperty){
        return asTreeCTE(codeProperty,parentCodeProperty,o->{});
    }
   default Queryable<T1> asTreeCTE(Property<T1,?> codeProperty,Property<T1,?> parentCodeProperty,SQLExpression1<TreeCTEConfigurer> treeExpression){
       ClientQueryable<T1> clientQueryable = getClientQueryable().asTreeCTE(EasyLambdaUtil.getPropertyName(codeProperty), EasyLambdaUtil.getPropertyName(parentCodeProperty), treeExpression);
       return new EasyQueryable<>(clientQueryable);
   }
}
