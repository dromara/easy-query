package com.easy.query.core.expression.sql;

import com.easy.query.core.exception.EasyQueryTableNotInSQLContextException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Map;

/**
 * create time 2023/8/11 09:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class SingleToTableContext implements ToTableContext{
    private final TableAvailable table;
    private final String alias;
    private final String defaultAlias;
    private final Map<TableAvailable, TableAliasSchema> lazyAliasMapping;

    public SingleToTableContext(TableAvailable table, String alias,String defaultAlias, Map<TableAvailable, TableAliasSchema> lazyAliasMapping){
        this.table = table;

        this.alias = alias;
        this.defaultAlias = defaultAlias;
        this.lazyAliasMapping = lazyAliasMapping;
    }

    @Override
    public int getTableSize() {
        return 1;
    }

    @Override
    public String getAlias(TableAvailable table) {
        if (!this.table.equals(table)) {
            TableAliasSchema lazyTableSchema = lazyAliasMapping.get(table);
            if(lazyTableSchema!=null){
                return lazyTableSchema.getTableAlias(defaultAlias);
            }
//            if(table.getAlias()!=null){
//                return table.getAlias();
//            }
            throw new EasyQueryTableNotInSQLContextException("not found table:[" + EasyClassUtil.getSimpleName(table.getEntityClass()) + ":"+table+"] in sql context");
        }
        return alias;
    }
}
