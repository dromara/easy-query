package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.result.impl.memory.row.MemoryResultSetRow;
import com.easy.query.core.sharding.merge.segment.PropertyGroup;
import com.easy.query.core.sharding.merge.segment.PropertyOrder;
import com.easy.query.core.sharding.merge.segment.PropertySqlColumn;
import com.easy.query.core.util.CompareUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyCheck;

import java.util.Comparator;
import java.util.List;

/**
 * create time 2023/5/3 16:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupByRowComparator implements Comparator<MemoryResultSetRow> {
    private final StreamMergeContext streamMergeContext;
    private final List<Boolean> valueCaseSensitive;

    public GroupByRowComparator(StreamMergeContext streamMergeContext, List<Boolean> valueCaseSensitive) {

        this.streamMergeContext = streamMergeContext;
        this.valueCaseSensitive = valueCaseSensitive;
    }

    @Override
    public int compare(MemoryResultSetRow o1, MemoryResultSetRow o2) {
        if (EasyCollectionUtil.isNotEmpty(streamMergeContext.getOrders())) {
            return compareOrder(o1, o2, streamMergeContext.getOrders());
        }

        return compareGroup(o1, o2, streamMergeContext.getGroups());
    }

    private int compareGroup(MemoryResultSetRow o1, MemoryResultSetRow o2, List<PropertyGroup> groups) {
        for (PropertyGroup group : groups) {
            int compared = compare(o1, o2, group);
            if(0!=compared){
                return compared;
            }
        }
        return 0;
    }

    private int compareOrder(MemoryResultSetRow o1, MemoryResultSetRow o2, List<PropertyOrder> orders) {
        for (PropertyOrder order : orders) {
            int compared = compare(o1, o2, order);
            if(0!=compared){
                return compared;
            }
        }
        return 0;
    }
    @SuppressWarnings("rawtypes")
    private int compare(MemoryResultSetRow o1, MemoryResultSetRow o2,PropertySqlColumn propertySqlColumn){
        Object value1 = o1.getValue(propertySqlColumn.columnIndex() + 1);
        EasyCheck.assertElse(value1==null||value1 instanceof Comparable<?>,"order by value must implements Comparable");
        Object value2 = o2.getValue(propertySqlColumn.columnIndex() + 1);
        EasyCheck.assertElse(value2==null||value2 instanceof Comparable<?>,"order by value must implements Comparable");
        boolean asc=true;
        if(propertySqlColumn instanceof PropertyOrder){
            PropertyOrder propertyOrder = (PropertyOrder) propertySqlColumn;
            asc=propertyOrder.asc();
        }
        boolean caseSensitive = valueCaseSensitive.get(propertySqlColumn.columnIndex());
        return CompareUtil.safeCompare((Comparable) value1,(Comparable)value2,asc,caseSensitive);
    }
}
