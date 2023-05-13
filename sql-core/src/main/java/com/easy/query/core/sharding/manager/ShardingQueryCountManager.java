package com.easy.query.core.sharding.manager;

import java.util.List;

/**
 * create time 2023/5/12 20:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ShardingQueryCountManager {
    void begin();
    boolean isBegin();
    void addCountResult(QueryCountResult queryCountResult);
    List<QueryCountResult> getCountResult();
    void clear();

}
