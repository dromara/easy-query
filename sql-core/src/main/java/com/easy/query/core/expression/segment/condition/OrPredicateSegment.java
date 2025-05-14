package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;

/**
 * @FileName: OrPredicateSegment.java
 * @Description: 文件说明
 * create time 2023/2/14 23:11
 * @author xuejiaming
 */
public class OrPredicateSegment extends AbstractPredicateSegment {
    public static final String OR= " "+ SQLKeywordEnum.OR.toSQL()+" ";

    public OrPredicateSegment() {
    }

    public OrPredicateSegment(boolean root) {
        super(root);
    }

    public OrPredicateSegment(Predicate predicate) {
        super(predicate);
    }

    public OrPredicateSegment(Predicate predicate, boolean root) {
        super(predicate, root);
    }

    @Override
    public PredicateSegment clonePredicateSegment() {
        OrPredicateSegment orPredicateSegment = new OrPredicateSegment(predicate, root);
        copyTo(orPredicateSegment);
        return orPredicateSegment;
    }
}
