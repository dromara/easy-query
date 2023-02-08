package org.jdqc.sql.core.abstraction.client;

import org.jdqc.sql.core.abstraction.sql.Select1;

/**
 *
 * @FileName: JQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:27
 * @Created by xuejiaming
 */
public interface JQDCClient {
    <T1> Select1<T1,T1> select(Class<T1> clazz);
    <T1> Select1<T1,T1> select(Class<T1> clazz,String alias);
    <T1,TR> Select1<T1,TR> select(Class<T1> clazz, Class<TR> trClass);
    <T1,TR> Select1<T1,TR> select(Class<T1> clazz, Class<TR> trClass,String alias);
}
