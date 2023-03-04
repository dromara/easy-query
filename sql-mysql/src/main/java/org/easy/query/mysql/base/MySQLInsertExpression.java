package org.easy.query.mysql.base;

import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.query.EasySqlInsertExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlExpressionContext;

/**
 * @FileName: MySQLInsertExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:51
 * @Created by xuejiaming
 */
public class MySQLInsertExpression  extends EasySqlInsertExpression {
    public MySQLInsertExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
    }

    @Override
    public String toSql() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        int insertColumns = getColumns().getSqlSegments().size();
        if (insertColumns == 0) {
            throw new EasyQueryException("插入至少确定一个列");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        SqlEntityTableExpression table = getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" (").append(getColumns().toSql()).append(") VALUES (");
        sql.append("?");
        for (int i = 0; i < insertColumns - 1; i++) {
            sql.append(",?");
        }
        sql.append(") ");
        return sql.toString();
    }

}
