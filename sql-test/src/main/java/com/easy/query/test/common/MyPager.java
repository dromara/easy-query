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
public class MyPager<TEntity> implements Pager<TEntity,PageResult<TEntity>> {
    private final long pageIndex;
    private final long pageSize;
    private final long pageTotal;

    public MyPager(long pageIndex, long pageSize){
        this(pageIndex,pageSize,-1);
    }
    public MyPager(long pageIndex, long pageSize, long pageTotal){

        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
    }
    @Override
    public PageResult<TEntity> toResult(PageAble<TEntity> query) {

        if(pageSize<0){
            List<TEntity> list = ((Query<TEntity>) query).toList();
            return new MyPageResult<>(-1,list);
        }
        EasyPageResult<TEntity> pageResult = query.toPageResult(pageIndex, pageSize,pageTotal);
        return new MyPageResult<>(pageResult.getTotal(),pageResult.getData());
    }
}
