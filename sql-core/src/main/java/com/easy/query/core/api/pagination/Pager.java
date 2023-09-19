package com.easy.query.core.api.pagination;

import com.easy.query.core.basic.api.select.Query;

import java.util.List;

/**
 * create time 2023/9/19 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Pager<TEntity,TPageResult> {
    TPageResult toResult(Query<TEntity> query,long pageIndex,long pageSize, long pageTotal);
    TPageResult toResult(Query<TEntity> query,long pageIndex,long pageSize, List<Long> totalLines);

}
