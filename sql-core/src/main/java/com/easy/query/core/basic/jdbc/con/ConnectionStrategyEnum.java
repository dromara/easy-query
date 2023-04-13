package com.easy.query.core.basic.jdbc.con;

/**
 * create time 2023/4/13 10:53
 * 文件说明
 *
 * @author xuejiaming
 */
public enum ConnectionStrategyEnum {
    /**
     * 用于分库分表时的单一操作比如写入或者无需聚合的查询
     * 只使用主库
     */
    ShareConnection,
    /**
     * 用于分库分表下需要聚合的查询查询完成后直接释放主库
     */
    IndependentConnectionMaster,
    /**
     *  用于分库分表下需要聚合的查询查询完成后直接释放从库
     */
    IndependentConnectionReplica
}
