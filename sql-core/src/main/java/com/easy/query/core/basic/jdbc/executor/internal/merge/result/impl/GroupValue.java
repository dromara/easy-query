package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.sharding.context.StreamMergeContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/28 10:38
 * 文件说明
 *
 * @author xuejiaming
 */
public final class GroupValue {
    private final List<Object> groupValues;

    public GroupValue(StreamMergeContext streamMergeContext, StreamResultSet streamResult) throws SQLException {
        this.groupValues = getGroupByValues(streamMergeContext, streamResult);
    }

    private List<Object> getGroupByValues(StreamMergeContext streamMergeContext, StreamResultSet streamResult) throws SQLException {

        List<PropertyGroup> groups = streamMergeContext.getGroups();
        if(EasyCollectionUtil.isNotEmpty(groups)){

            int groupSize = groups.size();
            ArrayList<Object> result = new ArrayList<>(groupSize);
            for (int i = 0; i < groupSize; i++) {
                PropertyGroup propertyGroup = groups.get(i);
                int columnIndex = propertyGroup.columnIndex();
                if(columnIndex<0){
                    throw new EasyQuerySQLCommandException("group column not in select:"+propertyGroup.propertyName());
                }
                //因为jdbc的索引是1开始的所以要加1
                Object groupValue = streamResult.getObject(columnIndex + 1);
                result.add(groupValue);
            }
            return result;
        }else{
            int columnIndex=-1;
            ArrayList<Object> result = new ArrayList<>(streamMergeContext.getSelectColumns().getSQLSegments().size());
            for (SQLSegment sqlSegment : streamMergeContext.getSelectColumns().getSQLSegments()) {
                columnIndex++;
                boolean aggregateColumn = EasySQLSegmentUtil.isAggregateColumn(sqlSegment);
                if(aggregateColumn){
                    continue;
                }
                //因为jdbc的索引是1开始的所以要加1
                Object groupValue = streamResult.getObject(columnIndex + 1);
                result.add(groupValue);
            }
            return result;
        }
    }

    public List<Object> getGroupValues() {
        return groupValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupValue that = (GroupValue) o;
        return Objects.equals(groupValues, that.groupValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupValues);
    }
}
