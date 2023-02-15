package org.easy.query.mysql.util;

import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;
import org.easy.query.core.util.StringUtil;

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
     * @param selectContext
     * @param select
     * @return
     */
    public static String toSql(SelectContext selectContext, String select){

        int tableCount = selectContext.getTables().size();
        if (tableCount == 0) {
            throw new JDQCException("未找到查询表信息");
        }
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < tableCount; i++) {
            SelectTableInfo table = selectContext.getTable(i);
            if(i==0){
                sql.append(StringUtil.isEmpty(select)?table.getAlias()+".*":select);
            }

            sql.append(table.getSelectTableSource()).append(table.getEntityMetadata().getTableName()).append(" ").append(table.getAlias());
            if (table.getOn().isEmpty()) {
                continue;
            }
            sql.append(" ON ").append(table.getOn().getSql());
        }
        if (!selectContext.getWhere().isEmpty()) {
            sql.append(" WHERE ").append(selectContext.getWhere().getSql());
        }
        if (!selectContext.getGroup().isEmpty()) {
            sql.append(" GROUP BY ").append(selectContext.getGroup().toSql());
        }
        if (!selectContext.getOrder().isEmpty()) {
            sql.append(" ORDER BY ").append(selectContext.getOrder().toSql());
        }
        return sql.toString();
    }
}
