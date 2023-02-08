package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.sql.core.exception.JDQCException;
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
        System.out.println(toSql());
        System.out.println(getSelectContext().getParams());
        return null;
    }

    @Override
    public String toSql() {
        StringBuilder sql = new StringBuilder("SELECT " + getSelectContext().getSelect());
        int tableCount = getSelectContext().getTables().size();
        if(tableCount==0){
            throw new JDQCException("未找到查询表信息");
        }
        for (int i = 0; i < tableCount-1; i++) {
            SelectTableInfo table = getSelectContext().getTable(i);

            sql.append(table.getSelectTableSource()).append(table.getTable().getTableType().getSimpleName()).append(" ").append(table.getAlias());
            if(table.getOn().length()==0){
                break;
            }
            SelectTableInfo table1 = getSelectContext().getTable(i+1);
            sql.append(table1.getSelectTableSource()).append(" ").append(table.getTable().getTableType().getSimpleName()).append(" ").append(table1.getAlias()).append(" ON ").append(table.getOn());
        }
        if(getSelectContext().getWhere().length()>0){
            sql.append(" WHERE").append(getSelectContext().getWhere());
        }
        if(getSelectContext().getGroup().length()>0){
            sql.append(" GROUP BY ").append(getSelectContext().getGroup());
        }
        return sql.toString();
    }

}
