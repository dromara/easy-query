package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation.AggregationType;

/**
 * create time 2023/4/30 23:10
 * 文件说明
 *
 * @author xuejiaming
 */
public enum GroupAvgBehaviorEnum {
    AVG(1),
    COUNT(1<<1),
    SUM(1<<2);
    private final int code;

    GroupAvgBehaviorEnum(int code){

        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static GroupAvgBehaviorEnum getGroupAvgBehavior(AggregationType aggregationType){
        switch (aggregationType){
            case COUNT:return GroupAvgBehaviorEnum.COUNT;
            case AVG:return GroupAvgBehaviorEnum.AVG;
            case SUM:return GroupAvgBehaviorEnum.SUM;
        }
        return null;
    }
}
