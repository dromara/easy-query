package org.easy.query.core.impl.lambda.select;

import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.SqlContext;

/**
 * @FileName: DefaultSqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 * @Created by xuejiaming
 */
public class DefaultSqlColumnSelector<T1> extends AbstractSqlColumnSelector<T1, SqlColumnSelector<T1>> implements SqlColumnSelector<T1>{
    public DefaultSqlColumnSelector(int index, SqlContext sqlContext, SqlSegment0Builder sqlSegmentBuilder) {
        super(index, sqlContext,sqlSegmentBuilder);
    }
}
