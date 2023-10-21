package com.easy.query.core.api.pagination;

import com.easy.query.core.basic.api.select.executor.PageAble;

/**
 * create time 2023/9/19 21:57
 * 自定义分页器
 *
 * @author xuejiaming
 */
public interface Pager<TEntity,TPageResult> {
    /**
     * 返回分页结果
     * @param query 分页表达式
     * @return 返回自定义分页结果
     */
    TPageResult toResult(PageAble<TEntity> query);

}
