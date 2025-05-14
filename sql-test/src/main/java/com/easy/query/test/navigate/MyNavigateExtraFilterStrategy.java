package com.easy.query.test.navigate;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.extension.navigate.NavigateBuilder;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2024/3/1 16:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyNavigateExtraFilterStrategy implements NavigateExtraFilterStrategy {
    @Override
    public @Nullable SQLActionExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder) {
        return null;
    }

    @Override
    public SQLActionExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression(NavigateBuilder builder) {
        return null;
    }
}
