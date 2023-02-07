package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.Select2;
import org.jdqc.sql.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.List;

/**
 * @FileName: MySqlSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:04
 * @Created by xuejiaming
 */
public class MySqlSelect1<T1,TR> extends AbstractSelect1<T1,TR> {

    public MySqlSelect1(Class<T1> t1Class, SelectContext selectContext) {
        super(t1Class, selectContext);
    }

    @Override
    protected <T2> AbstractSelect2<T1, T2, TR> createSelect2(Class<T2> joinClass, SelectContext selectContext,SelectTableInfoTypeEnum selectTableInfoType) {
        return new MySqlSelect2<>(joinClass,selectContext,selectTableInfoType);
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
