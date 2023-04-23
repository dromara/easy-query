package com.easy.query.core.expression.segment.condition;


import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;

/**
 * @FileName: AndPredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:11
 * @author xuejiaming
 */
public class AndPredicateSegment extends AbstractPredicateSegment {
    public static final String AND= " "+ SqlKeywordEnum.AND.toSql()+" ";

    public AndPredicateSegment() {
    }

    public AndPredicateSegment(boolean root) {
        super(root);
    }

    public AndPredicateSegment(Predicate predicate) {
        super(predicate);
    }

    public AndPredicateSegment(Predicate predicate, boolean root) {
        super(predicate, root);
    }

    @Override
    public PredicateSegment clonePredicateSegment() {
        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(predicate, root);
        copyTo(andPredicateSegment);
        return andPredicateSegment;
    }
}
