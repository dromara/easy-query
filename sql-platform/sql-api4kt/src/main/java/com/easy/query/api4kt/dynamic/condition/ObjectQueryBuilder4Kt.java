package com.easy.query.api4kt.dynamic.condition;

import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/11 09:40
 * 文件说明
 *
 * @author xuejiaming
 */

/**
 * 映射查询对象和数据库对象的属性
 * @param <TObject> 查询对象
 * @param <TEntity> query结果对象
 */
public interface ObjectQueryBuilder4Kt<TObject,TEntity> {
    ObjectQueryBuilder4Kt<TObject,TEntity> property(KProperty1<TEntity,?> entityProperty, KProperty1<TObject,?> property);
    ObjectQueryBuilder4Kt<TObject,TEntity> property(KProperty1<TEntity,?> entityProperty, KProperty1<TObject,?> property1, KProperty1<TObject,?> property2);
}

