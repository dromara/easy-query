package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.segment.SQLEntitySegment;

/**
 * @FileName: Predicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:05
 * @author xuejiaming
 */
public interface Predicate extends SQLEntitySegment {
    SQLPredicateCompare getOperator();
    @Override
    Predicate cloneSQLColumnSegment();
}
