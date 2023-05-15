package com.easy.query.core.api.pagination;

import com.easy.query.core.sharding.manager.SequenceCountLine;

/**
 * create time 2023/5/15 10:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyShardingPageResult<T> extends EasyPageResult<T>{
    SequenceCountLine getSequenceCountLine();
}
