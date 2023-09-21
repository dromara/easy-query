package com.easy.query.test.common;

import java.util.List;

/**
 * create time 2023/9/19 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PageResult<T> {
    /**
     * 返回总数
     * @return
     */
    long getTotalCount();

    /**
     * 结果内容
     * @return
     */
    List<T> getList();
}
