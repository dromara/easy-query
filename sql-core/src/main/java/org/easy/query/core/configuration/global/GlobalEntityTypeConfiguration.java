package org.easy.query.core.configuration.global;

import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;

/**
 * @FileName: GlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:44
 * @Created by xuejiaming
 */
public interface GlobalEntityTypeConfiguration {
    LogicDeleteStrategyEnum getStrategy();
    void configure(GlobalEntityTypeBuilder builder,String propertyName,Class<?> propertyType);
}
