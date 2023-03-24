package com.easy.query.core.enums;

/**
 * @FileName: UpdateStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/20 10:33
 * @author xuejiaming
 */
public enum UpdateStrategyEnum {
    /**
     * 默认策略全部更新
     */
    DEFAULT,
    /**
     * 只更新非null的列
     */
    ONLY_NOT_NULL_COLUMNS,
    /**
     * 只更新null值列
     */
    ONLY_NULL_COLUMNS
}
