package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnAsSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.enums.SelectTableInfoTypeEnum;
import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.List;

/**
 * @FileName: MySqlSelects.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:05
 * @Created by xuejiaming
 */
public class MySqlSelect2<T1,T2> extends AbstractSelect2<T1,T2> {
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
    public T1 firstOrNull() {
        return null;
    }

    @Override
    public T1 firstOrNull(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return null;
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        return null;
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        return null;
    }

    @Override
    public List<T1> toList() {
        System.out.println(toSql());
        System.out.println(getSelectContext().getParams());
        return null;
    }

    @Override
    public List<T1> toList(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return null;
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        return null;
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
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

    @Override
    public String toSql(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return null;
    }

    @Override
    public <TR> String toSql(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        return null;
    }

}
