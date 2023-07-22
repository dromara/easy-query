package com.easy.query.test.dto;

import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import com.easy.query.core.util.EasyStringUtil;
import lombok.Data;

/**
 * create time 2023/7/22 15:18
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class BlogSortRequest implements ObjectSort {
    private String sort;
    private Boolean asc;
    @Override
    public void configure(ObjectSortBuilder builder) {
        if(EasyStringUtil.isNotBlank(sort)&&asc!=null){
            builder.orderBy(sort,asc);
        }
    }
}
