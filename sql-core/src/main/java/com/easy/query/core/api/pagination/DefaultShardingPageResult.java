package com.easy.query.core.api.pagination;

import com.easy.query.core.sharding.manager.SequenceCountLine;

import java.util.List;

/**
 * create time 2023/5/15 11:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultShardingPageResult<T> implements EasyShardingPageResult<T> {
    private final long total;
    private final List<T> data;
    private final SequenceCountLine sequenceCountLine;

    public DefaultShardingPageResult(long total, List<T> data,SequenceCountLine sequenceCountLine) {
        this.total = total;

        this.data = data;
        this.sequenceCountLine = sequenceCountLine;
    }

    public long getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public SequenceCountLine getSequenceCountLine() {
        return sequenceCountLine;
    }
}
