package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.client.JQDCClient;
import org.jdqc.sql.core.abstraction.sql.Select1;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: DefaultJQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:28
 * @Created by xuejiaming
 */
public class DefaultJQDCClient implements JQDCClient {
    @Override
    public <T1> Select1<T1,T1> select(Class<T1> clazz) {
        return new SelectImpl<T1,T1>(new SelectContext<>(clazz,clazz));
    }

    @Override
    public <T1, TR> Select1<T1, TR> select(Class<T1> clazz, Class<TR> trClass) {
        return new SelectImpl<T1,TR>(new SelectContext<>(clazz,trClass));
    }

}
