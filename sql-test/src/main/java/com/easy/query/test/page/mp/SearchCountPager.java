package com.easy.query.test.page.mp;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pager;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.executor.PageAble;
import com.easy.query.test.common.MyPageResult;
import com.easy.query.test.common.PageResult;

import java.util.List;

/**
 * create time 2025/2/19 10:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SearchCountPager<TEntity> implements Pager<TEntity, PageResult<TEntity>> {
    private final long pageIndex;
    private final long pageSize;
    private final boolean searchCount;

    public SearchCountPager(long pageIndex, long pageSize, boolean searchCount) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.searchCount = searchCount;
    }

    @Override
    public PageResult<TEntity> toResult(PageAble<TEntity> query) {
        if (searchCount) {
            EasyPageResult<TEntity> pageResult = query.toPageResult(pageIndex, pageSize);
            return new MyPageResult<>(pageResult.getTotal(), pageResult.getData());
        }
        //设置每次获取多少条
        long take = pageSize <= 0 ? 0 : pageSize;
        //设置当前页码最小1
        long index = pageIndex <= 0 ? 1 : pageIndex;
        //需要跳过多少条
        long offset = (index - 1) * take;
        List<TEntity> list = ((Query<TEntity>) query).limit(offset, take).toList();
        return new MyPageResult<>(-1, list);
    }

    public static <TEntity> SearchCountPager<TEntity> of(long pageIndex, long pageSize, boolean search){
        return new SearchCountPager<>(pageIndex,pageSize,search);
    }
}
