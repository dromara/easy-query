package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2025/7/26 19:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateWrapperSQLFunction implements SQLFunction {
    private final SQLFunction sqlFunction;
    private final PredicateSegment predicateSegment;

    public PredicateWrapperSQLFunction(SQLFunction sqlFunction, PredicateSegment predicateSegment){
        this.sqlFunction = sqlFunction;
        this.predicateSegment = predicateSegment;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return sqlFunction.sqlSegment(defaultTable);
    }

    @Override
    public int paramMarks() {
        return sqlFunction.paramMarks();
    }

    @Override
    public void consume(SQLNativeChainExpressionContext context) {
        sqlFunction.consume( context);
    }

    @Override
    public AggregationType getAggregationType() {
        return sqlFunction.getAggregationType();
    }
    public PredicateSegment getPredicateSegment(){
        return predicateSegment;
    }
}
