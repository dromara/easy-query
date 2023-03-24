package com.easy.query.core.api.pagination;

import java.util.List;

/**
 * @FileName: IPageResult.java
 * @Description: 文件说明
 * @Date: 2023/2/22 10:51
 * @author xuejiaming
 */
public class PageResult<T> {
    private final long total;
    private final List<T> data;

    public PageResult(long total, List<T> data) {
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
