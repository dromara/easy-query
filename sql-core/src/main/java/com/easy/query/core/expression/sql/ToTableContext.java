package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.HashMap;

/**
 * create time 2023/5/27 11:25
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ToTableContext {
    private final HashMap<TableAvailable, String> aliasMapping;
    private final int tableCount;
    private final boolean firstHasAlias;

    public ToTableContext(HashMap<TableAvailable,String> aliasMapping,int tableCount,boolean firstHasAlias){

        this.aliasMapping = aliasMapping;
        this.tableCount = tableCount;
        this.firstHasAlias = firstHasAlias;
    }

    public String getAlias(TableAvailable table){
        if (tableCount == 1) {
            if (firstHasAlias) {
                return aliasMapping.get(table);
            }
            return null;
        }
        return aliasMapping.get(table);
    }

    public boolean isEmpty(){
        return aliasMapping.isEmpty();
    }
}
