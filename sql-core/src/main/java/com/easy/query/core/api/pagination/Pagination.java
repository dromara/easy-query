package com.easy.query.core.api.pagination;

import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/9/19 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class Pagination<T> {
    private final Query<T> query;
    private final long pageIndex;
    private final long pageSize;
    private final long pageTotal;

    public Pagination(Query<T> query, long pageIndex, long pageSize, long pageTotal){

        this.query = query;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
    }
    public <TPageResult> TPageResult toResult(Pager<T,TPageResult> pager){
        return pager.toResult(query,pageIndex,pageSize,pageTotal);
    }
}
