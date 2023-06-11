package com.easy.query.api4kt.dynamic;

import com.easy.query.api4kt.dynamic.sort.ObjectSortBuilder4Kt;
import com.easy.query.api4kt.dynamic.sort.impl.ObjectSortBuilder4KtImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;

/**
 * create time 2023/6/11 11:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ObjectSort4Kt<BlogEntity> extends ObjectSort {
    @Override
    default void configure(ObjectSortBuilder builder){
        configure(new ObjectSortBuilder4KtImpl<>(builder));
    }
    void configure(ObjectSortBuilder4Kt<BlogEntity> builder);
}
