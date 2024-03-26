package com.easy.query.core.basic.api.flat.provider;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;

/**
 * create time 2024/3/26 16:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapTable {
    TableAvailable getTable(int tableIndex);
    default EntitySQLTableOwner<?> getTableOwner(int tableIndex){
        return new SimpleEntitySQLTableOwner<>(getTable(tableIndex));
    }
}
