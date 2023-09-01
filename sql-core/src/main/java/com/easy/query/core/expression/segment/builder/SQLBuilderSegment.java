package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.lambda.BreakConsumer;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.index.SegmentIndex;

import java.util.List;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/13 12:49
 */
public interface SQLBuilderSegment extends SQLSegment {
    List<SQLSegment> getSQLSegments();

    void append(SQLSegment sqlSegment);

    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    void copyTo(SQLBuilderSegment predicateSegment);

    SQLBuilderSegment cloneSQLBuilder();

    boolean containsOnce(Class<?> entityClass, String propertyName);

    SegmentIndex buildSegmentIndex();

    boolean forEach(BreakConsumer<SQLSegment> consumer);
    void clear();
}
