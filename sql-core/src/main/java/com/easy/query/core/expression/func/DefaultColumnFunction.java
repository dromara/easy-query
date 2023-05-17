package com.easy.query.core.expression.func;

/**
 * create time 2023/5/17 12:59
 * 文件说明
 *
 * @author xuejiaming
 */
public enum DefaultColumnFunction implements ColumnFunction {
    SUM("SUM(%s)", AggregationType.SUM),
    COUNT("COUNT(%s)",AggregationType.COUNT),
    MAX("MAX(%s)",AggregationType.MAX),
    MIN("MIN(%s)",AggregationType.MIN),
    AVG("AVG(%s)",AggregationType.AVG),
    COUNT_DISTINCT("COUNT(DISTINCT %s)",AggregationType.COUNT_DISTINCT),
    SUM_DISTINCT("SUM(DISTINCT %s)",AggregationType.SUM_DISTINCT),
    AVG_DISTINCT("AVG(DISTINCT %s)",AggregationType.AVG_DISTINCT),
    LEN("LENGTH(%s)",AggregationType.LENGTH);
    private final String aggregate;
    private final AggregationType aggregationType;

    DefaultColumnFunction(String aggregate, AggregationType aggregationType){

        this.aggregate = aggregate;
        this.aggregationType = aggregationType;
    }

    @Override
    public String getFuncColumn(String column) {
        return String.format(aggregate,column);
    }

    @Override
    public AggregationType getAggregationType() {
        return aggregationType;
    }
}
