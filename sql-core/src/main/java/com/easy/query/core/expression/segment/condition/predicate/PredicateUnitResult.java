package com.easy.query.core.expression.segment.condition.predicate;

import java.util.List;

/**
 * create time 2025/7/26 21:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateUnitResult {
    public final List<PredicateUnit> samePredicateUnits;
    public final List<PredicateUnit> removePredicateUnits;

    public PredicateUnitResult(List<PredicateUnit> samePredicateUnits, List<PredicateUnit> removePredicateUnits){
        this.samePredicateUnits = samePredicateUnits;
        this.removePredicateUnits = removePredicateUnits;
    }
}
