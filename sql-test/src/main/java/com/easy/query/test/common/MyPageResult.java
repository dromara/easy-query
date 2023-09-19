package com.easy.query.test.common;

import java.util.List;

/**
 * create time 2023/9/19 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyPageResult<TEntity> implements PageResult<TEntity> {
    private final long total;
    private final List<TEntity> list;

    public MyPageResult(long total, List<TEntity> list){

        this.total = total;
        this.list = list;
    }
    @Override
    public long getTotalCount() {
        return total;
    }

    @Override
    public List<TEntity> getList() {
        return list;
    }
}
