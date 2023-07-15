package com.easy.query.core.sharding.context;

import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2023/6/28 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupMergeContext {
    private final Map<FuncColumnSegment, Map<AggregationType,ColumnIndexFuncColumnSegment>> AVG_COLUMN_MAPPING=new HashMap<>();
    public void addAvgColumn(FuncColumnSegment avgColumn){
        AVG_COLUMN_MAPPING.computeIfAbsent(avgColumn,k->new HashMap<>());
    }

    public boolean hasAvgColumn(){
        return !AVG_COLUMN_MAPPING.isEmpty();
    }
    public void addCountOrSum(FuncColumnSegment funcColumnSegment,int columnIndex){
        AggregationType aggregationType = funcColumnSegment.getAggregationType();
        switch (aggregationType){
            case COUNT:
            case SUM:addLinkFuncColumn(funcColumnSegment,columnIndex);
        }
    }
    private void addLinkFuncColumn(FuncColumnSegment funcColumnSegment,int columnIndex){
        for (Map.Entry<FuncColumnSegment, Map<AggregationType, ColumnIndexFuncColumnSegment>> funcColumnSegmentMapEntry : AVG_COLUMN_MAPPING.entrySet()) {

            FuncColumnSegment avgColumn = funcColumnSegmentMapEntry.getKey();
            TableAvailable table = avgColumn.getTable();
            String propertyName = avgColumn.getPropertyName();

            if(Objects.equals(table,funcColumnSegment.getTable())&&Objects.equals(propertyName,funcColumnSegment.getPropertyName())){
                funcColumnSegmentMapEntry.getValue().put(funcColumnSegment.getAggregationType(),new ColumnIndexFuncColumnSegment(columnIndex,funcColumnSegment));
            }
        }
    }
    public Map<FuncColumnSegment, Map<AggregationType,ColumnIndexFuncColumnSegment>> getColumnMapping(){
        return AVG_COLUMN_MAPPING;
    }
}
