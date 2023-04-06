package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.util.ArrayUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/5 22:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableRouteResult {
    private final Set<TableRouteUnit> replaceTables;
    private final boolean hasDifferentTail;
    private final boolean isEmpty;
    public TableRouteResult(List<TableRouteUnit> replaceTables){
        this.replaceTables=new HashSet<>(replaceTables);
        hasDifferentTail= ArrayUtil.hasDifferent(replaceTables, TableRouteUnit::getTail);
        isEmpty=replaceTables.size()==0;
    }

    public Set<TableRouteUnit> getReplaceTables() {
        return replaceTables;
    }

    public boolean isHasDifferentTail() {
        return hasDifferentTail;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
