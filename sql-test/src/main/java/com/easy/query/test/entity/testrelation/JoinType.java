package com.easy.query.test.entity.testrelation;

import com.easy.query.core.basic.extension.navigate.NavigateBuilder;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.util.Objects;

/**
 * create time 2024/3/8 11:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class JoinType implements NavigateExtraFilterStrategy {
    @Override
    public SQLActionExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder) {
        return null;
    }

    @Override
    public SQLActionExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression(NavigateBuilder builder) {

        if(Objects.equals(builder.getNavigateOption().getEntityMetadata().getEntityClass(),TestUserEntity.class)&&Objects.equals("roles",builder.getNavigateOption().getPropertyName())){
            return (p)->p.eq("type",1);
        }
        if(Objects.equals(builder.getNavigateOption().getEntityMetadata().getEntityClass(),TestRoleEntity.class)&&Objects.equals("routes",builder.getNavigateOption().getPropertyName())){
            return (p)->p.eq("type",2);
        }
        throw new UnsupportedOperationException();
    }
}
