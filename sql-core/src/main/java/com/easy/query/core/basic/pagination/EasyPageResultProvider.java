package com.easy.query.core.basic.pagination;

import com.easy.query.core.api.pagination.EasyPageResult;

import java.util.List;

/**
 * create time 2023/3/29 15:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyPageResultProvider {
    <T> EasyPageResult<T> createPageResult(long total, List<T> data);
}
