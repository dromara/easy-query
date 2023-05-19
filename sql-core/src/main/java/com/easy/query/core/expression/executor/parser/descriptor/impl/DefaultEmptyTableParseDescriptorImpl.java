package com.easy.query.core.expression.executor.parser.descriptor.impl;

import com.easy.query.core.expression.executor.parser.descriptor.TableEntityParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.executor.parser.descriptor.TablePredicateParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/5/18 17:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEmptyTableParseDescriptorImpl implements TablePredicateParseDescriptor, TableEntityParseDescriptor {
    public static final DefaultEmptyTableParseDescriptorImpl INSTANCE=new DefaultEmptyTableParseDescriptorImpl();
    private DefaultEmptyTableParseDescriptorImpl(){}

    @Override
    public List<Object> getEntitiesOrNull(TableAvailable table) {
        return null;
    }

    @Override
    public Set<TableAvailable> getTables() {
        return null;
    }

    @Override
    public void addTable(TableAvailable table) {

    }

    @Override
    public void addTablePredicate(TableAvailable table, PredicateSegment predicateSegment) {

    }

    @Override
    public List<PredicateSegment> getPredicatesOrNull(TableAvailable table) {
        return null;
    }

    @Override
    public boolean isShardingTable(TableAvailable table) {
        return false;
    }
}
