package org.jdqc.sql.core.abstraction.sql.base;

import java.io.Serializable;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:47
 * @Created by xuejiaming
 */
@FunctionalInterface
public interface SFunction<T1,T2> extends Serializable {

    void apply(T1 p1,T2 p2);
}