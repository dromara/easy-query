package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.config.JDQCConfiguration;
import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.query.builder.SelectTableInfo;

import java.util.ArrayList;

/**
 * @FileName: MySQLSelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/9 23:21
 * @Created by xuejiaming
 */
public class MySQLSelectContext extends SelectContext{
    public MySQLSelectContext(JDQCConfiguration jdqcConfiguration) {
        super(jdqcConfiguration);
    }
    public MySQLSelectContext(JDQCConfiguration jdqcConfiguration,String alias){
        super(jdqcConfiguration,alias);
    }

    @Override
    public SelectContext copy() {

        MySQLSelectContext mySQLSelectContext = new MySQLSelectContext(getJdqcConfiguration(), getAlias());
        mySQLSelectContext.setSkip(getSkip());
        mySQLSelectContext.setTake(getTake());
        mySQLSelectContext.getTables().addAll(getTables());
        mySQLSelectContext.getParams().addAll(getParams());
        mySQLSelectContext.getWhere().append(getWhere());
        mySQLSelectContext.getSelect().append(getSelect());
        mySQLSelectContext.getGroup().append(getGroup());
        mySQLSelectContext.getOrder().append(getOrder());
        return mySQLSelectContext;
    }

    public String toSql(){

        if (getSelect().length() == 0) {
            throw new JDQCException("not found select fields");
        }
        StringBuilder sql = new StringBuilder("SELECT " + getSelect());
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new JDQCException("未找到查询表信息");
        }
        for (int i = 0; i < tableCount - 1; i++) {
            SelectTableInfo table = getTable(i);

            sql.append(table.getSelectTableSource()).append(table.getTable().getTableType().getSimpleName()).append(" ").append(table.getAlias());
            if (table.getOn().length() == 0) {
                break;
            }
            SelectTableInfo table1 = getTable(i + 1);
            sql.append(table1.getSelectTableSource()).append(" ").append(table.getTable().getTableType().getSimpleName()).append(" ").append(table1.getAlias()).append(" ON ").append(table.getOn());
        }
        if (getWhere().length() > 0) {
            sql.append(" WHERE").append(getWhere());
        }
        if (getGroup().length() > 0) {
            sql.append(" GROUP BY ").append(getGroup());
        }
        return sql.toString();
    }
}
