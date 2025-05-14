package com.easy.query.core.basic.extension.navigate;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2024/3/1 16:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultNavigateExtraFilterStrategy implements NavigateExtraFilterStrategy{
    @Override
    public SQLActionExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder) {
        return null;
    }

    @Override
    public SQLActionExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression(NavigateBuilder builder) {
        return null;
    }
}
