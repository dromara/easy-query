package com.easy.query.core.expression.segment.condition.predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/7/28 08:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateUnitItem {
    public final PredicateUnit mainPredicateUnit;
    public final List<PredicateUnit> predicateUnits;

    public PredicateUnitItem(PredicateUnit mainPredicateUnit) {
        this.mainPredicateUnit = mainPredicateUnit;
        this.predicateUnits = new ArrayList<>();
    }

    public void addPredicateUnit(PredicateUnit predicateUnit){
        predicateUnits.add(predicateUnit);
    }
}
