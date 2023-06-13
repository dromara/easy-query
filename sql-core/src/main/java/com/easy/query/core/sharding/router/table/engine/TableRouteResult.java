package com.easy.query.core.sharding.router.table.engine;

import com.easy.query.core.sharding.router.table.TableRouteUnit;

import java.util.List;

/**
 * create time 2023/4/5 22:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableRouteResult {
    private final List<TableRouteUnit> replaceTables;
    private final boolean isEmpty;
    public TableRouteResult(List<TableRouteUnit> replaceTables){
        this.replaceTables=replaceTables;
        isEmpty=replaceTables.size()==0;
    }

    public List<TableRouteUnit> getReplaceTables() {
        return replaceTables;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
