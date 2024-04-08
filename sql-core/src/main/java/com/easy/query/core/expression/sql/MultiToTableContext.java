package com.easy.query.core.expression.sql;

import com.easy.query.core.exception.EasyQueryTableNotInSQLContextException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyClassUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/5/27 11:25
 * 文件说明
 *
 * @author xuejiaming
 */
public final class MultiToTableContext implements ToTableContext {
    private final HashMap<TableAvailable, String> aliasMapping;
    private final boolean firstHasAlias;
    private final String defaultAlias;
    private final Map<TableAvailable, TableAliasSchema> lazyAliasMapping;

    public MultiToTableContext(HashMap<TableAvailable, String> aliasMapping,  boolean firstHasAlias,String defaultAlias, Map<TableAvailable, TableAliasSchema> lazyAliasMapping) {

        this.aliasMapping = aliasMapping;
        this.firstHasAlias = firstHasAlias;
        this.defaultAlias = defaultAlias;
        this.lazyAliasMapping = lazyAliasMapping;
    }

    @Override
    public String getAlias(TableAvailable table) {
        if (!aliasMapping.containsKey(table)) {
            TableAliasSchema lazyTableSchema = lazyAliasMapping.get(table);
            if(lazyTableSchema!=null){
                return lazyTableSchema.getTableAlias(defaultAlias);
            }
//            if(table.getAlias()!=null){
//                return table.getAlias();
//            }
//            return "a";
        throw new EasyQueryTableNotInSQLContextException("not found table:[" + EasyClassUtil.getSimpleName(table.getEntityClass()) + "] in sql context");
        }
//        if (tableCount == 1) {
//            if (firstHasAlias) {
//                return aliasMapping.get(table);
//            }
//            return null;
//        }
        return aliasMapping.get(table);
    }
}
