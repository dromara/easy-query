package com.easy.query.test.entity.navf;


import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.extension.navigate.NavigateBuilder;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.navf.proxy.RoleJoinProxy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 角色关联表
 */
@Getter
@Setter
@EntityProxy
@FieldNameConstants
@Table("sys_role_join")
public class RoleJoin implements ProxyEntityAvailable<RoleJoin , RoleJoinProxy> {

    @Column("role_id")
    private Long roleId;

    @Column("biz_id")
    private Long bizId;

    // 业务类型{1:user,2:res,3:route}
    @Column("type")
    private String type;


    @Component
    public static class RoleJoinType implements NavigateExtraFilterStrategy {

        @Override
        public SQLExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder) {
            // 获取使用这个注解的类
            Class<?> entityClass = builder.getNavigateOption().getEntityMetadata().getEntityClass();
            // 获取使用注解的字段属性名
            String propertyName = builder.getNavigateOption().getPropertyName();
            if (Objects.equals(Resource.class, entityClass)) {
                if (propertyName.equals(Resource.Fields.roles)) {
                    return o -> o.eq(Fields.type, RoleJoinEnum.type.res.name());
                }
            }
            if (Objects.equals(Route.class, entityClass)) {
                if (propertyName.equals(Route.Fields.roles)) {
                    return o -> o.eq(Fields.type, RoleJoinEnum.type.route.name());
                }
            }
            if (Objects.equals(User.class, entityClass)) {
                if (propertyName.equals(User.Fields.roles)) {
                    return o -> o.eq(Fields.type, RoleJoinEnum.type.user.name());
                }
            }

            if (Objects.equals(Role.class, entityClass)) {
                if (propertyName.equals(Role.Fields.resources)) {
                    return o -> o.eq(Fields.type, RoleJoinEnum.type.res.name());
                }
                if (propertyName.equals(Role.Fields.routes)) {
                    return o -> o.eq(Fields.type, RoleJoinEnum.type.route.name());
                }
                if (propertyName.equals(Role.Fields.users)) {
                    return o -> o.eq(Fields.type, RoleJoinEnum.type.user.name());
                }
            }
            throw new RuntimeException("未配置多对多的关联关系");
        }

        @Override
        public SQLExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression(NavigateBuilder builder) {
            return null;
        }
    }

}
