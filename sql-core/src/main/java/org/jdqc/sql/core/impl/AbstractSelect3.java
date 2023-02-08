package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.Select3;

/**
 * @FileName: AbstractSelect3.java
 * @Description: 文件说明
 * @Date: 2023/2/8 23:13
 * @Created by xuejiaming
 */
public abstract class AbstractSelect3<T1,T2,T3,TR> extends AbstractSelect0<T1,TR, Select3<T1,T2,T3,TR>> implements Select3<T1,T2,T3,TR> {
    public AbstractSelect3(SelectContext selectContext) {
        super(selectContext);
    }
}
