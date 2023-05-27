package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/5/27 08:08
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TableAliasSchema {
    private final TableAvailable table;
    private final int aliasIndex;

    public TableAliasSchema(TableAvailable table, int aliasIndex){

        this.table = table;
        this.aliasIndex = aliasIndex;
    }

    public TableAvailable getTable() {
        return table;
    }
    public String getTableAlias(String alias){
        if(alias==null||aliasIndex==0){
            return alias;
        }
        return alias+aliasIndex;
    }
}
