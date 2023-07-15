package com.easy.query.core.expression.func;

/**
 * create time 2023/4/28 21:04
 * 文件说明
 *
 * @author xuejiaming
 */
public enum AggregationType {
    UNKNOWN,MAX, MIN, SUM, COUNT, AVG, BIT_XOR,LENGTH, COUNT_DISTINCT, SUM_DISTINCT, AVG_DISTINCT;

    public static boolean containsAvg(AggregationType aggregationType){
        switch (aggregationType){
            case AVG:
            case AVG_DISTINCT:return true;
        }
        return false;
    }
}
