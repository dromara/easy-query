package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/10/12 17:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class SimpleEntitySQLTableOwner<TEntity> implements EntitySQLTableOwner<TEntity> {
    private final TableAvailable table;

    public SimpleEntitySQLTableOwner(TableAvailable table){

        this.table = table;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }
}
