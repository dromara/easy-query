package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: JQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:27
 * @Created by xuejiaming
 */
public interface JQDCClient {
    <T1> Select<T1,T1> query(Class<T1> clazz);
    <T1,TR> Select<T1,TR> query(Class<T1> clazz,Class<TR> trClass);
}
