package com.easy.query.core.sharding.enums;

/**
 * create time 2023/4/13 22:02
 * 文件说明
 *
 * @author xuejiaming
 */
public enum ConnectionModeEnum {

    /**
     * 内存限制模式最小化内存聚合 流式聚合 同时会有多个链接
     */
    MEMORY_STRICTLY,
    /**
     * 连接数限制模式最小化并发连接数 内存聚合 连接数会有限制
     */
    CONNECTION_STRICTLY,
    /**
     * 系统自动选择内存还是流式聚合
     */
    SYSTEM_AUTO
}
