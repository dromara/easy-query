package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable10;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.api.proxy.entity.select.EntityQueryable8;
import com.easy.query.api.proxy.entity.select.EntityQueryable9;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2024/5/28 21:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityMultiable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> from(Class<T2> from2Class);
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>,T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> from(Class<T2> from2Class,Class<T3> from3Class);
    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>,
            T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>,
            T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4, T4Proxy>>
    EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>,
            T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>,
            T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4, T4Proxy>,
            T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5, T5Proxy>>
    EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>,
            T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>,
            T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4, T4Proxy>,
            T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5, T5Proxy>,
            T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6, T6Proxy>>
    EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>,
            T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>,
            T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4, T4Proxy>,
            T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5, T5Proxy>,
            T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6, T6Proxy>,
            T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>>
    EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>,
            T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>,
            T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4, T4Proxy>,
            T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5, T5Proxy>,
            T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6, T6Proxy>,
            T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>,
            T8Proxy extends ProxyEntity<T8Proxy, T8>, T8 extends ProxyEntityAvailable<T8, T8Proxy>>
    EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>,
            T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>,
            T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4, T4Proxy>,
            T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5, T5Proxy>,
            T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6, T6Proxy>,
            T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>,
            T8Proxy extends ProxyEntity<T8Proxy, T8>, T8 extends ProxyEntityAvailable<T8, T8Proxy>,
            T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9, T9Proxy>>
    EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class, Class<T9> from9Class);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2, T2Proxy>,
            T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>,
            T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4, T4Proxy>,
            T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5, T5Proxy>,
            T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6, T6Proxy>,
            T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>,
            T8Proxy extends ProxyEntity<T8Proxy, T8>, T8 extends ProxyEntityAvailable<T8, T8Proxy>,
            T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9, T9Proxy>,
            T10Proxy extends ProxyEntity<T10Proxy, T10>, T10 extends ProxyEntityAvailable<T10, T10Proxy>>
    EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class, Class<T9> from9Class, Class<T10> from10Class);

}
