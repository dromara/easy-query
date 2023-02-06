package org.jdqc.sql.core.abstraction.sql.base;

import org.jdqc.sql.core.abstraction.lambda.Property;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 * @Created by xuejiaming
 */
public interface SqlColumnSelector0<T1,TChain> {
    TChain column(Property<T1,?> column);
}
