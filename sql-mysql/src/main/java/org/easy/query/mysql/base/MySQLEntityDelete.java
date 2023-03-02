package org.easy.query.mysql.base;

import org.easy.query.core.expression.context.DeleteContext;
import org.easy.query.core.basic.api.delete.AbstractEntityDelete;
import org.easy.query.mysql.util.MySQLUtil;

import java.util.Collection;

/**
 * @FileName: MySQLEntityDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:57
 * @Created by xuejiaming
 */
public class MySQLEntityDelete<T> extends AbstractEntityDelete<T> {
    public MySQLEntityDelete(Collection<T> entities, DeleteContext deleteContext) {
        super(entities, deleteContext);
    }

    @Override
    public String toSql() {
        return MySQLUtil.toEntityDeleteSql(deleteContext,table);
    }
}
