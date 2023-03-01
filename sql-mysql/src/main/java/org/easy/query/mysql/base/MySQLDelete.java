package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.context.DeleteContext;
import org.easy.query.core.basic.api.delete.AbstractEntityDelete;
import org.easy.query.mysql.util.MySQLUtil;

import java.util.Collection;

/**
 * @FileName: MySQLDelete.java
 * @Description: 文件说明
 * @Date: 2023/3/1 12:56
 * @Created by xuejiaming
 */
public class MySQLDelete<T1> extends AbstractEntityDelete<T1> {
    public MySQLDelete(Collection<T1> entities, DeleteContext deleteContext) {
        super(entities, deleteContext);
    }

    @Override
    public String toSql() {
        return MySQLUtil.toEntityDeleteSql(deleteContext,table);
    }
}
