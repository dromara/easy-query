package com.easy.query.test.common;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.api.pagination.Pager;
import com.easy.query.core.basic.api.select.Query;

import java.util.List;

/**
 * create time 2023/9/19 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyPager<TEntity> implements Pager<TEntity,PageResult<TEntity>> {
    @Override
    public PageResult<TEntity> toResult(Query<TEntity> query, long pageIndex, long pageSize, long pageTotal) {
        EasyPageResult<TEntity> pageResult = query.toPageResult(pageIndex, pageSize,pageTotal);
        return new MyPageResult<>(pageResult.getTotal(),pageResult.getData());
    }

    @Override
    public PageResult<TEntity> toResult(Query<TEntity> query, long pageIndex, long pageSize, List<Long> totalLines) {
        EasyPageResult<TEntity> pageResult = query.toShardingPageResult(pageIndex, pageSize,totalLines);
        return new MyPageResult<>(pageResult.getTotal(),pageResult.getData());
    }
}
