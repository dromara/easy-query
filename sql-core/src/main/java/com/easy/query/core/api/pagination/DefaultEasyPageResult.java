package com.easy.query.core.api.pagination;

import java.util.List;

/**
 * create time 2023/3/29 15:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPageResult<T> implements EasyPageResult<T> {
    private final long total;
    private final List<T> data;

    public DefaultEasyPageResult(long total, List<T> data) {
        this.total = total;

        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }
}
