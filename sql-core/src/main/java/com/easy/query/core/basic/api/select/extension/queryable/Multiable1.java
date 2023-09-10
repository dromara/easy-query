package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable9;

/**
 * create time 2023/9/9 19:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Multiable1<T1> {
    <T2> ClientQueryable2<T1, T2> from(Class<T2> from2Class);

    <T2, T3> ClientQueryable3<T1, T2, T3> from(Class<T2> from2Class, Class<T3> from3Class);

    <T2, T3, T4> ClientQueryable4<T1, T2, T3, T4> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class);
    <T2, T3, T4,T5> ClientQueryable5<T1, T2, T3, T4,T5> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class);
    <T2, T3, T4,T5,T6> ClientQueryable6<T1, T2, T3, T4,T5,T6> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class);
    <T2, T3, T4,T5,T6,T7> ClientQueryable7<T1, T2, T3, T4,T5,T6,T7> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class);
    <T2, T3, T4,T5,T6,T7,T8> ClientQueryable8<T1, T2, T3, T4,T5,T6,T7,T8> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class);
    <T2, T3, T4,T5,T6,T7,T8,T9> ClientQueryable9<T1, T2, T3, T4,T5,T6,T7,T8,T9> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class, Class<T9> from9Class);
    <T2, T3, T4,T5,T6,T7,T8,T9,T10> ClientQueryable10<T1, T2, T3, T4,T5,T6,T7,T8,T9,T10> from(Class<T2> from2Class, Class<T3> from3Class, Class<T4> from4Class, Class<T5> from5Class, Class<T6> from6Class, Class<T7> from7Class, Class<T8> from8Class, Class<T9> from9Class, Class<T10> from10Class);

}
