package com.easy.query.core.enums;

/**
 * create time 2025/7/4 19:31
 * 文件说明
 *
 * @author xuejiaming
 */
public enum PartitionOrderEnum {
    /**
     * 默认抛出错误
     */
    THROW,
    /**
     * 忽略
     */
    IGNORE,
    /**
     * 使用导航属性的order
     */
    NAVIGATE,
    /**
     * 使用key asc
     */
    KEY_ASC,
    /**
     * 使用key desc
     */
    KEY_DESC
}
