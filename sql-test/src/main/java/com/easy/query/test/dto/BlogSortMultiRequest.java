package com.easy.query.test.dto;

import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/22 15:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class BlogSortMultiRequest implements ObjectSort {
    private List<SortConfig> orders=new ArrayList<>();
    @Override
    public void configure(ObjectSortBuilder builder) {
        for (SortConfig order : orders) {
            builder.orderBy(order.getProperty(),order.getAsc());
        }
    }

    @Data
    public static class SortConfig{
        private String property;
        private Boolean asc;
    }
}
