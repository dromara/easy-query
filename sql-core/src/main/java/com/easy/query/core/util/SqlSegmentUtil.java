package com.easy.query.core.util;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * create time 2023/4/23 13:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class SqlSegmentUtil {
    public static boolean isEmpty(PredicateSegment predicateSegment) {
        return predicateSegment == null || predicateSegment.isEmpty();
    }
    public static boolean isNotEmpty(PredicateSegment predicateSegment) {
        return !isEmpty(predicateSegment);
    }
    public static boolean isEmpty(SqlBuilderSegment sqlBuilderSegment) {
        return sqlBuilderSegment == null || sqlBuilderSegment.isEmpty();
    }
    public static boolean isNotEmpty(SqlBuilderSegment sqlBuilderSegment) {
        return !isEmpty(sqlBuilderSegment);
    }
}
