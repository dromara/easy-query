package com.easy.query.logicdel;

import com.easy.query.core.basic.plugin.logicdel.abstraction.AbstractEasyLogicDeleteStrategy;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteBuilder;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * create time 2023/4/1 22:51
 * 文件说明
 *
 * @author xuejiaming
 */
//@Component //如果是spring
public class MyLogicDelStrategy extends AbstractEasyLogicDeleteStrategy {
    /**
     * 允许datetime类型的属性
     */
    private final Set<Class<?>> allowTypes=new HashSet<>(Arrays.asList(LocalDateTime.class));
    @Override
    protected SqlExpression<SqlWherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, Property<Object, ?> lambdaProperty) {
        return o->o.isNull(lambdaProperty);
    }

    @Override
    protected SqlExpression<SqlColumnSetter<Object>> getDeletedSqlExpression(LogicDeleteBuilder builder, Property<Object, ?> lambdaProperty) {
//        LocalDateTime now = LocalDateTime.now();
//        return o->o.set(lambdaProperty,now);
        //上面的是错误用法,将now值获取后那么这个now就是个固定值而不是动态值
        Property<Object, ?> deletedUserProperty = BeanUtil.getFastBean(builder.getEntityMetadata().getEntityClass()).getBeanGetter("deletedUser", String.class);
        return o->o.set(lambdaProperty,LocalDateTime.now())
                .set(deletedUserProperty,CurrentUserHelper.getUserId());
    }

    @Override
    public String getStrategy() {
        return "MyLogicDelStrategy";
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowTypes;
    }
}
