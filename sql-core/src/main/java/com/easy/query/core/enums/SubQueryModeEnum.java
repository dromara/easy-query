package com.easy.query.core.enums;

/**
 * create time 2025/7/31 15:53
 * 文件说明
 *
 * @author xuejiaming
 */
public enum SubQueryModeEnum {
    /**
     * 不处理
     */
    DEFAULT,
    /**
     * 子查询
     */
    SUB_QUERY_ONLY,
    /**
     * 子查询转groupJoin
     */
    GROUP_JOIN
}
