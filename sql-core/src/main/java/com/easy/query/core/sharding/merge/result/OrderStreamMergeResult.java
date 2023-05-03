package com.easy.query.core.sharding.merge.result;

import java.util.List;

/**
 * create time 2023/4/27 12:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderStreamMergeResult extends StreamResultSet,Comparable<OrderStreamMergeResult> {
    List<Comparable<?>> compareValues();
}
