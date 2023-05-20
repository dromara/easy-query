package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.api.select.Queryable;

/**
 * create time 2023/5/20 10:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryColumnSegment extends ColumnSegment{
    Queryable<?> getSubQueryable();

    @Override
    SubQueryColumnSegment cloneSQLEntitySegment();
}
