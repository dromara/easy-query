package org.easy.query.mysql.util;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.impl.InsertContext;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.UpdateContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.StringUtil;

import java.util.List;

/**
 * @FileName: MySQLUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/10 08:28
 * @Created by xuejiaming
 */
public class MySQLUtil {
    private MySQLUtil() {
        throw new UnsupportedOperationException();
    }


    /**
     * 生成mysql语句
     *
     * @param selectContext
     * @param select
     * @return
     */
    public static String toSelectSql(SelectContext selectContext, String select) {

        int tableCount = selectContext.getTables().size();
        if (tableCount == 0) {
            throw new JDQCException("未找到查询表信息");
        }
        //将条件参数清空
        if (!selectContext.getParameters().isEmpty()) {
            selectContext.getParameters().clear();
        }
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < tableCount; i++) {
            SqlTableInfo table = selectContext.getTable(i);
            if (i == 0) {
                if (StringUtil.isEmpty(select)) {
                    if (selectContext.getGroup().isEmpty()) {
                        sql.append(table.getAlias()).append(".*");
                    } else {
                        sql.append(selectContext.getGroup().toSql());
                    }
                } else {
                    sql.append(select);
                }
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
        if (!selectContext.getHaving().isEmpty()) {
            sql.append(" HAVING ").append(selectContext.getHaving().getSql());
        }
        if (!selectContext.getOrder().isEmpty()) {
            sql.append(" ORDER BY ").append(selectContext.getOrder().toSql());
        }
        if (selectContext.getRows() > 0) {
            sql.append(" LIMIT ");
            if (selectContext.getOffset() > 0) {
                sql.append(selectContext.getOffset()).append(" OFFSET ").append(selectContext.getRows());
            } else {
                sql.append(selectContext.getRows());
            }
        }
        return sql.toString();
    }

    public static String toInsertSql(InsertContext insertContext) {

        int tableCount = insertContext.getTables().size();
        if (tableCount == 0) {
            throw new JDQCException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new JDQCException("找到多张表信息");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        SqlTableInfo table = insertContext.getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" (").append(insertContext.getColumns().toSql()).append(") VALUES (");
        int size = insertContext.getColumns().getSqlSegments().size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                sql.append("?");
            } else {
                sql.append(",?");
            }
        }
        sql.append(") ");
        return sql.toString();
    }
    public static String toUpdateEntitySql(UpdateContext updateContext){

        int tableCount = updateContext.getTables().size();
        if (tableCount == 0) {
            throw new JDQCException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new JDQCException("找到多张表信息");
        }
        if(updateContext.getWhereColumns().isEmpty()){
            throw new JDQCException("更新需要指定条件列");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        SqlTableInfo table = updateContext.getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" SET ").append(updateContext.getSetColumns().toSql());
        sql.append(" WHERE ");
        List<SqlSegment> whereColumnSegments = updateContext.getWhereColumns().getSqlSegments();
        int i =0;
        for (SqlSegment whereColumnSegment : whereColumnSegments) {
            if(i!=0){
                sql.append(" ").append(SqlKeywordEnum.AND.getSql()).append(" ");
            }
            sql.append(whereColumnSegment.getSql()).append(" = ?");
            i++;
        }
        return sql.toString();
    }
    public static String toUpdateExpressionSql(UpdateContext updateContext){

        int tableCount = updateContext.getTables().size();
        if (tableCount == 0) {
            throw new JDQCException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new JDQCException("找到多张表信息");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        SqlTableInfo table = updateContext.getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" SET ").append(updateContext.getSetColumns().toSql());
        sql.append(" WHERE ").append(updateContext.getWhere().getSql());
        return sql.toString();
    }
}
