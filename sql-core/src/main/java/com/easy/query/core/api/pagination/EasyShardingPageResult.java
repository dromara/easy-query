package com.easy.query.core.api.pagination;

import java.util.List;

/**
 * create time 2023/5/15 10:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyShardingPageResult<T> extends EasyPageResult<T>{
    List<Long> getTotalLines();
}
