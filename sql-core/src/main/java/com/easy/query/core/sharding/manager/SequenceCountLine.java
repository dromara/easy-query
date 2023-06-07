package com.easy.query.core.sharding.manager;

import java.util.List;

/**
 * create time 2023/5/15 10:29
 * 顺序查询的时间线 本次查询如果是顺序分页的情况下通过设置SequenceCountNode节点可以保证数据高性能分页
 * 如果节点total为负数那么表示需要进行查询对应节点的数据count
 *
 * @author xuejiaming
 */
public interface SequenceCountLine {
    List<Long> getTotalLines();

    void addCountResult(long total, boolean init);
}
