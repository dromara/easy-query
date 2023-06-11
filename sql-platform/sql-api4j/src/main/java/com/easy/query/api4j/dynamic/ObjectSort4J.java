package com.easy.query.api4j.dynamic;

import com.easy.query.api4j.dynamic.sort.ObjectSortBuilder4J;
import com.easy.query.api4j.dynamic.sort.impl.ObjectSortBuilder4JImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;

/**
 * create time 2023/6/11 11:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ObjectSort4J<BlogEntity> extends ObjectSort {
    @Override
    default void configure(ObjectSortBuilder builder){
        configure(new ObjectSortBuilder4JImpl<>(builder));
    }
    void configure(ObjectSortBuilder4J<BlogEntity> builder);
}
