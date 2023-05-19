package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;

import java.util.Map;

/**
 * create time 2023/5/19 10:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLRewriteUnitImpl implements SQLRewriteUnit{
    private final Map<TableAvailable,String> tableNameRewriteMap;
    public SQLRewriteUnitImpl(Map<TableAvailable,String> tableNameRewriteMap){

        this.tableNameRewriteMap = tableNameRewriteMap;
    }
    @Override
    public void rewriteTableName(EntityTableSQLExpression entityTableSQLExpression) {
        if(tableNameRewriteMap==null){
            return;
        }
        String actualName = tableNameRewriteMap.get(entityTableSQLExpression.getEntityTable());
        if(actualName==null){
            return;
        }
        entityTableSQLExpression.setTableNameAs(o->actualName);
    }
}
