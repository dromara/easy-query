package com.easy.query.core.api.dynamic;

import com.easy.query.core.enums.dynamic.DynamicModeEnum;

import java.util.Objects;

/**
 * @FileName: EasyDynamicStrategy.java
 * @Description: 文件说明
 * create time 2023/3/24 08:12
 * @author xuejiaming
 */
public interface DynamicQueryStrategy {
    /**
     * 是否是严格模式
     * @return
     */
    default boolean useStrictMode(){
        return Objects.equals(DynamicModeEnum.STRICT,dynamicMode());
    }
    /**
     * 动态策略
     * @return
     */
    default DynamicModeEnum dynamicMode(){
        return DynamicModeEnum.STRICT;
    }
}
