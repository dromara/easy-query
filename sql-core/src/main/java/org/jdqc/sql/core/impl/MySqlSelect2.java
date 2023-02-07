package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.List;

/**
 * @FileName: MySqlSelects.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:05
 * @Created by xuejiaming
 */
public class MySqlSelect2<T1,T2,TR> extends AbstractSelect2<T1,T2,TR> {
    public MySqlSelect2(Class<T2> t2Class, SelectContext selectContext, SelectTableInfoTypeEnum selectTableInfoType) {
        super(t2Class, selectContext, selectTableInfoType);
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
    public List<TR> toList() {
        System.out.println(getSelectContext().getSql());
        System.out.println("where的表达式:"+getSelectContext().getWhere());
        for (SelectTableInfo table : getSelectContext().getTables()) {

            System.out.println("on的表达式:"+table.getOn());
        }
        return null;
    }

    @Override
    public String toSql() {
        return null;
    }

}
