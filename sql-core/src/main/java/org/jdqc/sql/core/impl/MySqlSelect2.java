package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.enums.SelectTableInfoTypeEnum;

/**
 * @FileName: MySqlSelects.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:05
 * @Created by xuejiaming
 */
public class MySqlSelect2<T1,T2> extends AbstractSelect2<T1,T2> {
    public MySqlSelect2(Class<T1> t1Class,Class<T2> t2Class, SelectContext selectContext, SelectTableInfoTypeEnum selectTableInfoType) {
        super(t1Class,t2Class, selectContext, selectTableInfoType);
    }


    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean any() {
        return false;
    }
}
