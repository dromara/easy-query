package org.jdqc.sql.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectImpl.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:29
 * @Created by xuejiaming
 */
public class SelectImpl<T1, TR> extends SelectBase<T1, TR,Select<T1, TR>> implements Select<T1, TR> {

    public SelectImpl(SelectContext<T1, TR>  selectContext) {
        super(selectContext);
    }

    @Override
    public Select<T1, TR> select(Property<T1, ?>... columns) {
        return null;
    }

    @Override
    public Select<T1, TR> eq(boolean condition, Property<T1, ?> column, Object val) {
        return null;
    }
}
