package org.jdqc.sql.core.abstraction.lambda;

import java.io.Serializable;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SFunction.java
 * @Description: 文件说明
 * @Date: 2023/2/4 22:16
 * @Created by xuejiaming
 */
@FunctionalInterface
public interface Property<T, R> extends Serializable {
    R apply(T t);
}

