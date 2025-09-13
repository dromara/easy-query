package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;

/**
 * create time 2023/6/25 17:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OnlySelector extends SQLNative<OnlySelector> {
    SQLBuilderSegment getSQLSegmentBuilder();
    OnlySelector columnKeys(TableAvailable table);

    OnlySelector column(TableAvailable table, String property);
    OnlySelector columnNull(TableAvailable table, String property);

    OnlySelector columnAll(TableAvailable table);

    OnlySelector columnIgnore(TableAvailable table, String property);
    OnlySelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment);
}
