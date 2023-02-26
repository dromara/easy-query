package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.impl.SqlContext;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSelector<T1> extends AbstractSqlColumnSelector<T1, SqlColumnSelector<T1>> implements SqlColumnSelector<T1>{
    public DefaultSqlColumnSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder) {
        super(index, sqlContext,sqlSegmentBuilder);
    }
}
