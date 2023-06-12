package com.easy.query.core.enums.conn;

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
    ShareConnection("MASTER_SHARE"),
    /**
     * 用于分库分表下需要聚合的查询查询完成后直接释放主库
     */
    IndependentConnectionMaster("MASTER_IND"),
    /**
     *  用于分库分表下需要聚合的查询查询完成后直接释放从库
     *  如果当前策略获取不到则会采用IndependentConnectionMaster模式获取
     */
    IndependentConnectionReplica("REPLICA");
    private final String name;

    ConnectionStrategyEnum(String name){

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
