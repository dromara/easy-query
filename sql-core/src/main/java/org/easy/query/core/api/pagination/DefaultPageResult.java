package org.easy.query.core.api.pagination;

import java.util.List;

/**
 * @FileName: DefaultPageResult.java
 * @Description: 文件说明
 * @Date: 2023/2/22 10:58
 * @Created by xuejiaming
 */
public class DefaultPageResult<T> implements PageResult<T> {
    private final long total;
    private final List<T> data;

    public DefaultPageResult(long total, List<T> data){
        this.total = total;

        this.data = data;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public List<T> getData() {
        return data;
    }
}
