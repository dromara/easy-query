package com.easy.query.test.dto.autotest;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.basic.extension.navigate.NavigateBuilder;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.dto.autotest.proxy.RoleJoinProxy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * create time 2024/5/15 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
@Getter
@Setter
@EntityProxy
@Table("sys_role_join")
public class RoleJoin implements ProxyEntityAvailable<RoleJoin, RoleJoinProxy> {

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
        public SQLActionExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder) {
//            // 获取使用这个注解的类
//            Class<?> entityClass = builder.getNavigateOption().getEntityMetadata().getEntityClass();
//            // 获取使用注解的字段属性名
//            String propertyName = builder.getNavigateOption().getPropertyName();
//
//            if (Objects.equals(Resource.class, entityClass)) {
//                if (propertyName.equals("roles")) {
//                    return o -> o.eq("type", RoleJoinEnum.type.res.name());
//                }
//            }
//            if (Objects.equals(Route.class, entityClass)) {
//                if (propertyName.equals("roles")) {
//                    return o -> o.eq("type", RoleJoinEnum.type.route.name());
//                }
//            }
//            if (Objects.equals(User.class, entityClass)) {
//                if (propertyName.equals("roles")) {
//                    return o -> o.eq("type", RoleJoinEnum.type.user.name());
//                }
//            }
//            if (Objects.equals(Role.class, entityClass)) {
//                if (propertyName.equals("resources")) {
//                    return o -> o.eq("type", RoleJoinEnum.type.res.name());
//                }
//                if (propertyName.equals("routes")) {
//                    return o -> o.eq("type", RoleJoinEnum.type.route.name());
//                }
//                if (propertyName.equals("users")) {
//                    return o -> o.eq("type", RoleJoinEnum.type.user.name());
//                }
//            }
//            throw new BizException("未配置多对多的关联关系");
            return o->{};
        }

        @Override
        public SQLActionExpression1<WherePredicate<?>> getPredicateMappingClassFilterExpression(NavigateBuilder builder) {
            return null;
        }
    }

}
