package com.easy.query.test.entity.relation;

import com.easy.query.core.basic.extension.navigate.NavigateBuilder;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterCancelable;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.EntityMetadata;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * create time 2024/3/1 17:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class BookNavigateExtraFilterStrategy implements NavigateExtraFilterStrategy, NavigateExtraFilterCancelable {
    @Override
    public SQLExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder) {
        //parentType
        EntityMetadata entityMetadata = builder.getNavigateOption().getEntityMetadata();
        //导航属性类型
        Class<?> navigatePropertyType = builder.getNavigateOption().getNavigatePropertyType();
        //导航属性名称
        String propertyName = builder.getNavigateOption().getPropertyName();
        if(Objects.equals(RelationUser.class,entityMetadata.getEntityClass())||Objects.equals(MyRelationUser.class,entityMetadata.getEntityClass())){
            if(Objects.equals("historyBooks",propertyName)){
                LocalDateTime histroy = LocalDateTime.of(2022, 1, 1, 0, 0);
                return o->o.le("createTime",histroy);
            }
            if(Objects.equals("teachers",propertyName)){
                return o->o.eq("name","12345");
            }
            return o->o.eq("bookType",1);
        }
        return o->o.eq("bookType",2);
    }

    @Override
    public SQLExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression(NavigateBuilder builder) {
        Class<?> mappingClass = builder.getNavigateOption().getMappingClass();
        //导航属性名称
        String propertyName = builder.getNavigateOption().getPropertyName();
        if(Objects.equals(RelationRoute.class,mappingClass)){
            if(Objects.equals("teachers",propertyName)){
                return o->o.eq("type",1);
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "BookNavigateExtraFilterStrategy";
    }
}
