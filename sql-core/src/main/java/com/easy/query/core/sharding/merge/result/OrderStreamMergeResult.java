package com.easy.query.core.sharding.merge.result;

import com.easy.query.core.sharding.merge.abstraction.StreamResult;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/27 12:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderStreamMergeResult extends StreamResult,Comparable<OrderStreamMergeResult> {
    List<Comparable<?>> compareValues();
}
