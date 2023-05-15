package com.easy.query.core.basic.pagination;

import com.easy.query.core.api.pagination.DefaultPageResult;
import com.easy.query.core.api.pagination.DefaultShardingPageResult;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.sharding.manager.SequenceCountLine;

import java.util.List;

/**
 * create time 2023/3/29 15:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPageResultProvider implements EasyPageResultProvider{
    @Override
    public <T> EasyPageResult<T> createPageResult(long pageIndex, long pageSize,long total, List<T> data) {
        return new DefaultPageResult<>(total,data);
    }

    @Override
    public <T> EasyPageResult<T> createShardingPageResult(long pageIndex, long pageSize,long total, List<T> data,SequenceCountLine sequenceCountLine) {
        return new DefaultShardingPageResult<>(total,data,sequenceCountLine);
    }
}
