package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.insert.AbstractInsert;
import org.easy.query.core.expression.context.InsertContext;
import org.easy.query.mysql.util.MySQLUtil;

/**
 * @FileName: MySQLInsert.java
 * @Description: 文件说明
 * @Date: 2023/2/23 22:28
 * @Created by xuejiaming
 */
public class MySQLInsert<T> extends AbstractInsert<T> {
    public MySQLInsert(Class<T> clazz, InsertContext insertContext) {
        super(clazz, insertContext);
    }


    @Override
    public String toSql() {
        return MySQLUtil.toInsertSql(insertContext);
    }
}
