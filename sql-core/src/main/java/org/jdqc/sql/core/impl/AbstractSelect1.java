package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.Select1;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: AbstractSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @Created by xuejiaming
 */
public abstract class AbstractSelect1<T1,TR> extends AbstractSelect0<T1,TR,Select1<T1,TR>> implements Select1<T1,TR> {

    public AbstractSelect1(SelectContext selectContext) {
        super(selectContext);
    }
}
