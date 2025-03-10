package com.easy.query.core.expression.parser.core.base.many;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2025/3/9 22:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyColumnImpl implements ManyColumn {
    private final TableAvailable table;
    private final String navValue;

    public ManyColumnImpl(TableAvailable table,String navValue){
        this.table = table;
        this.navValue = navValue;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getNavValue() {
        return navValue;
    }
}
