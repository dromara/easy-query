package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/10/11 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLColumnOwner implements SQLColumnOwner{
    private final SQLTableOwner sqlTableOwner;
    private final String propertyName;

    public DefaultSQLColumnOwner(SQLTableOwner sqlTableOwner, String propertyName){

        this.sqlTableOwner = sqlTableOwner;
        this.propertyName = propertyName;
    }
    @Override
    public String getProperty() {
        return propertyName;
    }

    @Override
    public TableAvailable getTable() {
        return EasyObjectUtil.getValueOrNull(sqlTableOwner, SQLTableOwner::getTable);
    }
}
