package org.jdqc.mysql.base;

import org.jdqc.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.core.impl.AbstractSelect2;
import org.jdqc.mysql.util.MySQLUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: MySqlSelects.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:05
 * @Created by xuejiaming
 */
public class MySQLSelect2<T1,T2> extends AbstractSelect2<T1,T2> {
    private final MySQLSelectContext selectContext;

    public MySQLSelect2(Class<T1> t1Class, Class<T2> t2Class, MySQLSelectContext selectContext, SelectTableInfoTypeEnum selectTableInfoType) {
        super(t1Class,t2Class, selectContext, selectTableInfoType);
        this.selectContext = selectContext;
    }


    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean any() {
        return false;
    }

    @Override
    protected List<T1> toInternalList(String columns) {
        String s = toSql(columns);
        System.out.println(s);
        return new ArrayList<>();
    }

    @Override
    public String toSql(String columns) {
        return MySQLUtil.toSql(selectContext,columns);
    }

}
