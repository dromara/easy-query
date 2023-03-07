package org.easy.query.mysql.base;

import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.query.EasySqlUpdateExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlExpressionContext;

/**
 * @FileName: MySQLUpdateExpression.java
 * @Description: mysql数据库更新表达式
 * @Date: 2023/3/4 17:11
 * @Created by xuejiaming
 */
public class MySQLUpdateExpression extends EasySqlUpdateExpression {
    private final boolean isExpressionUpdate;

    public MySQLUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext);
        this.isExpressionUpdate = isExpressionUpdate;
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
        if(isExpressionUpdate){
            return expressionUpdate();
        }else{
            return entityUpdate();
        }
    }
    private String expressionUpdate(){

        if (!hasSetColumns()) {
            throw new EasyQueryException("更新需要设置更新列");
        }
        if (!hasWhere()) {
            throw new EasyQueryException("更新需要设置条件");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        SqlEntityTableExpression table = getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" SET ").append(getSetColumns().toSql());
        sql.append(" WHERE ").append(getWhere().toSql());
        return sql.toString();
    }
    private String entityUpdate(){
        SqlEntityTableExpression table = getTable(0);

        if (!hasWhereColumns()) {
            throw new EasyQueryException("更新需要指定条件列");
        }
        String tableName = table.getEntityMetadata().getTableName();
        return "UPDATE " + tableName + " SET " + getSetColumns().toSql() + " WHERE " +
                getWhereColumns().toSql();
    }
}
