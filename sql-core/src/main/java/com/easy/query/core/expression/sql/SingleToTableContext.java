package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/8/11 09:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class SingleToTableContext implements ToTableContext{
    private final String alias;
    public static final ToTableContext DEFAULT=new  SingleToTableContext(null);

    public SingleToTableContext(String alias){

        this.alias = alias;
    }
    @Override
    public String getAlias(TableAvailable table) {
        return alias;
    }
}
