package com.easy.query.core.api.pagination;

import com.easy.query.core.basic.api.select.Query;

import java.util.List;

/**
 * create time 2023/9/19 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingPagination<T> {
    private final Query<T> query;
    private final long pageIndex;
    private final long pageSize;
    private final List<Long> totalLines;

    public ShardingPagination(Query<T> query, long pageIndex, long pageSize, List<Long> totalLines){

        this.query = query;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalLines = totalLines;
    }
    public <TPageResult> TPageResult toResult(Pager<T,TPageResult> pager){
        return pager.toResult(query,pageIndex,pageSize,totalLines);
    }
}
