package com.easy.query.test.common;

import java.util.List;

/**
 * create time 2023/9/19 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PageResult<T> {
    long getTotalCount();

    List<T> getList();
}
