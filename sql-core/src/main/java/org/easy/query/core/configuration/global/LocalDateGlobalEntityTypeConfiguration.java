package org.easy.query.core.configuration.global;

import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.util.EasyUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @FileName: ClassGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @Created by xuejiaming
 */
public class LocalDateGlobalEntityTypeConfiguration implements GlobalEntityTypeConfiguration {
    @Override
    public LogicDeleteStrategyEnum getStrategy() {
        return LogicDeleteStrategyEnum.LOCAL_DATE;
    }

    @Override
    public void configure(GlobalEntityTypeBuilder builder, String propertyName, Class<?> propertyType) {
        Property functionField = EasyUtil.getFunctionField(builder.getEntityClass(), propertyName, propertyType);
        builder.configLogicDelete(o -> {
            o.isNull(functionField);
        }, o -> {
            o.set(functionField, LocalDate.now());
        });
    }
}
