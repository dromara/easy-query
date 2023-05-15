package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

/**
 * create time 2023/4/28 21:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class AggregationUnitFactory {
    public static AggregationUnit create(final AggregationType type) {
        switch (type) {
            case MAX:
                return new ComparableAggregationUnit(false);
            case MIN:
                return new ComparableAggregationUnit(true);
            case SUM:
                return new AccumulationAggregationUnit();
            case COUNT:
                return new AccumulationAggregationUnit();
            case AVG:
                return new AverageAggregationUnit();
            case BIT_XOR:
                return new BitXorAggregationUnit();
            case COUNT_DISTINCT:
                return new DistinctCountAggregationUnit();
            case SUM_DISTINCT:
                return new DistinctSumAggregationUnit();
            case AVG_DISTINCT:
                return new DistinctAverageAggregationUnit();
            default:
                throw new UnsupportedOperationException(type.name());
        }
    }
}
