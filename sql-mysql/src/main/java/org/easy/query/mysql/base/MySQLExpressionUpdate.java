package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.update.AbstractExpressionUpdate;
import org.easy.query.core.query.SqlEntityUpdateExpression;

/**
 * @FileName: MySQLExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 21:22
 * @Created by xuejiaming
 */
public class MySQLExpressionUpdate<T> extends AbstractExpressionUpdate<T> {
    public MySQLExpressionUpdate(Class<T> clazz, SqlEntityUpdateExpression sqlEntityUpdateExpression) {
        super(clazz, sqlEntityUpdateExpression);
    }

    @Override
    public String toSql() {
        return sqlEntityUpdateExpression.toSql();
    }
}
