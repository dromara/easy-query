package org.easy.query.core.impl.lambda;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.SqlSegmentBuilder;

/**
 * @FileName: AbstractSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 16:27
 * @Created by xuejiaming
 */
public abstract class AbstractSqlSegmentBuilder implements SqlSegmentBuilder {
    protected final SqlSegment sqlSegment;
    public AbstractSqlSegmentBuilder(SqlSegment sqlSegment){

        this.sqlSegment = sqlSegment;
    }
    @Override
    public SqlSegment getSqlSegment() {
        return sqlSegment;
    }
}
