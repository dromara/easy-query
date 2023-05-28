package com.easy.query.core.expression.sql;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyClassUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create time 2023/5/27 08:08
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TableContext {
    private final LinkedHashMap<TableAvailable, TableAliasSchema> aliasMapping = new LinkedHashMap<>();

    public void extract(TableContext otherTableContext) {
        for (Map.Entry<TableAvailable, TableAliasSchema> aliasSchemaEntry : otherTableContext.aliasMapping.entrySet()) {
            addTable(aliasSchemaEntry.getKey());
        }
    }

    public void addTable(TableAvailable table) {
        int size = aliasMapping.size();
        aliasMapping.put(table, new TableAliasSchema(table, size));
    }

    public boolean isEmpty() {
        return aliasMapping.isEmpty();
    }

    public void copyTo(TableContext tableContext){
        for (Map.Entry<TableAvailable, TableAliasSchema> aliasSchemaEntry : aliasMapping.entrySet()) {
            tableContext.addTable(aliasSchemaEntry.getKey());
        }
    }

    public ToTableContext getToTableContext(String alias){
        int mappingSize = aliasMapping.size();
        HashMap<TableAvailable, String> result = new HashMap<>();
        int i=0;
        boolean firstHasAlias=false;
        for (Map.Entry<TableAvailable, TableAliasSchema> aliasSchemaEntry : aliasMapping.entrySet()) {

            TableAvailable table = aliasSchemaEntry.getKey();
            TableAliasSchema tableAliasSchema = aliasSchemaEntry.getValue();
            String tableAlias = tableAliasSchema.getTableAlias(alias);
            if (i == 0) {
                if (tableAliasSchema.getTable().isAnonymous()||tableAliasSchema.getTable().hasAlias()) {
                    firstHasAlias=true;
                }
            }
            result.put(table,tableAlias);
            i++;
        }
        return new ToTableContext(result,mappingSize,firstHasAlias);
    }
}
