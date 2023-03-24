package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;

/**
 * @FileName: OrPredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:11
 * @author xuejiaming
 */
public class OrPredicateSegment extends AbstractPredicateSegment {
    public static final String OR= " "+ SqlKeywordEnum.OR.toSql()+" ";
    public OrPredicateSegment() {
    }

    public OrPredicateSegment(boolean root) {
        super(root);
    }

    public OrPredicateSegment(Predicate predicate) {
        super(predicate);
    }
}
