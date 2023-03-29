package com.easy.query.core.basic.pagination;

import com.easy.query.core.api.pagination.DefaultEasyPageResult;
import com.easy.query.core.api.pagination.EasyPageResult;

import java.util.List;

/**
 * create time 2023/3/29 15:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPageResultProvider implements EasyPageResultProvider{
    @Override
    public <T> EasyPageResult<T> createPageResult(long total, List<T> data) {
        return new DefaultEasyPageResult<>(total,data);
    }
}
