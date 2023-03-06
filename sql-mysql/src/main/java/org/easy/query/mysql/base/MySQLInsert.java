package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.insert.AbstractInsertable;
import org.easy.query.core.query.SqlEntityInsertExpression;

/**
 * @FileName: MySQLInsert.java
 * @Description: 文件说明
 * @Date: 2023/2/23 22:28
 * @Created by xuejiaming
 */
public class MySQLInsert<T> extends AbstractInsertable<T> {
    public MySQLInsert(Class<T> clazz, SqlEntityInsertExpression sqlEntityExpression) {
        super(clazz, sqlEntityExpression);
    }


    @Override
    public String toSql() {
        return sqlEntityInsertExpression.toSql();
    }
}
