package org.easy.query.core.expression.segment;

import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.expression.segment.predicate.node.Predicate;

/**
 * @FileName: OrPredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:11
 * @Created by xuejiaming
 */
public class OrPredicateSegment extends PredicateSegment{
    public static final String OR= " "+SqlKeywordEnum.OR.getSql()+" ";
    public OrPredicateSegment() {
    }

    public OrPredicateSegment(boolean root) {
        super(root);
    }

    public OrPredicateSegment(Predicate predicate) {
        super(predicate);
    }
}
