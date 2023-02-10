package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.query.builder.SelectTableInfo;
import org.jdqc.sql.core.util.StringKit;

/**
 * @FileName: MySQLUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/10 08:28
 * @Created by xuejiaming
 */
public class MySQLUtil {
    private MySQLUtil(){throw new UnsupportedOperationException();}

    /**
     * 生成mysql语句
     * @param mySQLSelectContext
     * @param select
     * @return
     */
    public static String toSql(MySQLSelectContext mySQLSelectContext,String select){

        int tableCount = mySQLSelectContext.getTables().size();
        if (tableCount == 0) {
            throw new JDQCException("未找到查询表信息");
        }
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < tableCount; i++) {
            SelectTableInfo table = mySQLSelectContext.getTable(i);
            if(i==0){
                sql.append(StringKit.isEmpty(select)?table.getAlias()+".*":select);
            }

            sql.append(table.getSelectTableSource()).append(table.getTable().getTableType().getSimpleName()).append(" ").append(table.getAlias());
            if (table.getOn().length() == 0) {
                continue;
            }
            sql.append(" ON ").append(table.getOn());
        }
        if (mySQLSelectContext.getWhere().length() > 0) {
            sql.append(" WHERE").append(mySQLSelectContext.getWhere());
        }
        if (mySQLSelectContext.getGroup().length() > 0) {
            sql.append(" GROUP BY ").append(mySQLSelectContext.getGroup());
        }
        return sql.toString();
    }
}
