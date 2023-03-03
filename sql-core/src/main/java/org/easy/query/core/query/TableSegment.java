package org.easy.query.core.query;

import org.easy.query.core.expression.parser.abstraction.internal.IndexAware;
import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;

/**
 * @FileName: TableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 17:15
 * @Created by xuejiaming
 */
public interface TableSegment extends SqlSegment, IndexAware{
    boolean isEmpty();
    default boolean isNotEmpty() {
        return !isEmpty();
    }

    void addTableSegment(TableSegment tableSegment);

    PredicateSegment getOn();
    String getAlias();
    int getIndex();
    String getSelectTableSource();
}
