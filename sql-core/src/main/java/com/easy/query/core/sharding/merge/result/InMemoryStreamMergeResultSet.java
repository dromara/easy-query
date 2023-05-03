package com.easy.query.core.sharding.merge.result;

import com.easy.query.core.sharding.merge.abstraction.StreamResultSet;

/**
 * create time 2023/5/2 23:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface InMemoryStreamMergeResultSet extends StreamResultSet {
    int getReallyCount();
}
