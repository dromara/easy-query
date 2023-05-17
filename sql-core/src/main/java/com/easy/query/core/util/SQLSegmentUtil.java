package com.easy.query.core.util;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * create time 2023/4/23 13:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSegmentUtil {
    public static boolean isEmpty(PredicateSegment predicateSegment) {
        return predicateSegment == null || predicateSegment.isEmpty();
    }
    public static boolean isNotEmpty(PredicateSegment predicateSegment) {
        return !isEmpty(predicateSegment);
    }
    public static boolean isEmpty(SQLBuilderSegment sqlBuilderSegment) {
        return sqlBuilderSegment == null || sqlBuilderSegment.isEmpty();
    }
    public static boolean isNotEmpty(SQLBuilderSegment sqlBuilderSegment) {
        return !isEmpty(sqlBuilderSegment);
    }
}
