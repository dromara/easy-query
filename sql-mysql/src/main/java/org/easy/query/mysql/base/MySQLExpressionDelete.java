package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.delete.AbstractExpressionDelete;
import org.easy.query.core.query.SqlEntityDeleteExpression;

/**
 * @FileName: MySQLExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:41
 * @Created by xuejiaming
 */
public class MySQLExpressionDelete<T> extends AbstractExpressionDelete<T> {
    public MySQLExpressionDelete(Class<T> clazz, SqlEntityDeleteExpression sqlEntityDeleteExpression) {
        super(clazz, sqlEntityDeleteExpression);
    }

    @Override
    public String toSql() {
        return sqlEntityDeleteExpression.toSql();
    }
}
