package com.easy.query.core.expression.sql;

import com.easy.query.core.exception.EasyQueryTableNotInSQLContextException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2023/8/11 09:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class SingleToTableContext implements ToTableContext{
    private final TableAvailable table;
    private final String alias;

    public SingleToTableContext(TableAvailable table,String alias){
        this.table = table;

        this.alias = alias;
    }
    @Override
    public String getAlias(TableAvailable table) {
        if (!this.table.equals(table)) {
//            if(table.getAlias()!=null){
//                return table.getAlias();
//            }
            throw new EasyQueryTableNotInSQLContextException("not found table:[" + EasyClassUtil.getSimpleName(table.getEntityClass()) + ":"+table+"] in sql context");
        }
        return alias;
    }
}
