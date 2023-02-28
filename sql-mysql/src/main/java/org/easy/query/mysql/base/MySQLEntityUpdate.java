package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.update.AbstractEntityUpdate;
import org.easy.query.core.basic.api.context.UpdateContext;
import org.easy.query.mysql.util.MySQLUtil;

import java.util.Collection;

/**
 * @FileName: MySQLEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 11:30
 * @Created by xuejiaming
 */
public class MySQLEntityUpdate<T> extends AbstractEntityUpdate<T> {

    public MySQLEntityUpdate(Collection<T> entities, UpdateContext updateContext) {
        super(entities, updateContext);
    }

    @Override
    public String toSql() {
        return MySQLUtil.toUpdateEntitySql(updateContext,table);
    }
}
