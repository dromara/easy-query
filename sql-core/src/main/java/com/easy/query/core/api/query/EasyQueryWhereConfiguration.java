package com.easy.query.core.api.query;

/**
 * @FileName: QueryEntityMapping.java
 * @Description: 文件说明
 * @Date: 2023/3/13 12:13
 * @Created by xuejiaming
 */
public interface EasyQueryWhereConfiguration<TObject> {
    void configure(ObjectQueryBuilder<TObject> objectQueryBuilder);
}
