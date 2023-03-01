package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.context.DeleteContext;
import org.easy.query.core.basic.api.delete.AbstractExpressionDelete;
import org.easy.query.mysql.util.MySQLUtil;

/**
 * @FileName: MySQLExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 22:41
 * @Created by xuejiaming
 */
public class MySQLExpressionDelete<T> extends AbstractExpressionDelete<T> {
    public MySQLExpressionDelete(Class<T> clazz, DeleteContext deleteContext) {
        super(clazz, deleteContext);
    }

    @Override
    public String toSql() {
        return MySQLUtil.toExpressionDeleteSql(deleteContext,table);
    }
}
