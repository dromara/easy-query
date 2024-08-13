package com.easy.query.test.logicdel;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.abstraction.AbstractLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

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
public class MyLogicDelStrategy extends AbstractLogicDeleteStrategy {
    /**
     * 允许datetime类型的属性
     */
    private final Set<Class<?>> allowTypes=new HashSet<>(Arrays.asList(LocalDateTime.class));


    @Override
    protected SQLExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName) {
        return o ->o.isNull(propertyName);
    }

    @Override
    protected SQLExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName) {
//        LocalDateTime now = LocalDateTime.now();
//        return o->o.set(lambdaProperty,now);
        //上面的是错误用法,将now值获取后那么这个now就是个固定值而不是动态值
        return o -> o.set(propertyName, LocalDateTime.now())
                .set("deletedUser", CurrentUserHelper.getUserId());
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
