package org.easy.query.core.basic.sql.segment.segment;


import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.basic.sql.segment.segment.predicate.Predicate;

/**
 * @FileName: AndPredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:11
 * @Created by xuejiaming
 */
public class AndPredicateSegment extends PredicateSegment{
    public static final String AND= " "+SqlKeywordEnum.AND.getSql()+" ";
    public AndPredicateSegment() {
    }

    public AndPredicateSegment(Predicate predicate) {
        super(predicate);
    }

    public AndPredicateSegment(boolean root) {
        super(root);
    }
}
