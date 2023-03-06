package org.easy.query.core.configuration.global;

import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.util.EasyUtil;

/**
 * @FileName: ClassGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @Created by xuejiaming
 */
public class BoolGlobalEntityTypeConfiguration implements GlobalEntityTypeConfiguration {
    @Override
    public LogicDeleteStrategyEnum getStrategy() {
        return LogicDeleteStrategyEnum.BOOLEAN;
    }

    @Override
    public void configure(GlobalEntityTypeBuilder builder, String propertyName, Class<?> propertyType) {
        Property functionField = EasyUtil.getFunctionField(builder.getEntityClass(), propertyName, propertyType);
        builder.configLogicDelete(o -> {
            o.eq(functionField, false);
        }, o -> {
            o.set(functionField, true);
        });
    }
}
