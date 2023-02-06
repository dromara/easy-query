package org.jdqc.sql.core;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: DefaultJQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:28
 * @Created by xuejiaming
 */
public class DefaultJQDCClient implements JQDCClient{
    @Override
    public <T1> Select<T1,T1> query(Class<T1> clazz) {
        return new SelectImpl<T1,T1>(new SelectContext<>(clazz,clazz));
    }

    @Override
    public <T1, TR> Select<T1, TR> query(Class<T1> clazz, Class<TR> trClass) {
        return new SelectImpl<T1,TR>(new SelectContext<>(clazz,trClass));
    }
}
