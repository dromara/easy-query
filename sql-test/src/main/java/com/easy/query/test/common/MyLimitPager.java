package com.easy.query.test.common;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pager;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.executor.PageAble;

import java.util.List;

/**
 * create time 2023/9/19 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyLimitPager<TEntity> implements Pager<TEntity, PageResult<TEntity>> {
    private final long pageIndex;
    private final long pageSize;
    private final boolean searchCount;

    public MyLimitPager(long pageIndex, long pageSize,boolean searchCount) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.searchCount = searchCount;
    }


    @Override
    public PageResult<TEntity> toResult(PageAble<TEntity> query) {

        if (pageSize < 0 || !searchCount) {
            List<TEntity> list = ((Query<TEntity>) query).toList();
            return new MyPageResult<>(-1, list);
        }
        EasyPageResult<TEntity> pageResult = query.toPageResult(pageIndex, pageSize, -1);
        return new MyPageResult<>(pageResult.getTotal(), pageResult.getData());
    }
}
