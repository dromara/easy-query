package com.easy.query.core.expression.executor.parser.descriptor.impl;

import com.easy.query.core.expression.executor.parser.descriptor.TablePredicateParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.util.EasyMapUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create time 2023/5/18 17:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class TablePredicateParseDescriptorImpl implements TablePredicateParseDescriptor {
    private final Map<TableAvailable, List<PredicateSegment>> tablePredicates;

    public TablePredicateParseDescriptorImpl(){
        this(Collections.emptyMap());
    }
    public TablePredicateParseDescriptorImpl(Map<TableAvailable, List<PredicateSegment>> tablePredicates) {
        this.tablePredicates = tablePredicates;
    }

    @Override
    public void addTable(TableAvailable table) {
        if(!tablePredicates.containsKey(table)){
            tablePredicates.put(table,new ArrayList<>());
        }
    }

    @Override
    public void addTablePredicate(TableAvailable table, PredicateSegment predicateSegment) {
        List<PredicateSegment> predicateSegments = EasyMapUtil.computeIfAbsent(tablePredicates,table, o -> new ArrayList<>());
        predicateSegments.add(predicateSegment);
    }

    @Override
    public List<PredicateSegment> getPredicatesOrNull(TableAvailable table) {
        return tablePredicates.get(table);
    }

    @Override
    public boolean isShardingTable(TableAvailable table) {
        return tablePredicates.containsKey(table);
    }

    @Override
    public Set<TableAvailable> getTables() {
        return tablePredicates.keySet();
    }
}
