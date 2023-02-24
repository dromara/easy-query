package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.UpdateSetSqlSegmentBuilder;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.segments.AndPredicateSegment;
import org.easy.query.core.segments.PredicateSegment;

/**
 * @FileName: UpdateContext.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:07
 * @Created by xuejiaming
 */
public class UpdateContext extends AbstractSqlContext{

    private final SqlSegment0Builder set;

    private final PredicateSegment where;
    public UpdateContext(EasyQueryRuntimeContext runtimeContext) {
        super(runtimeContext);
        set=new UpdateSetSqlSegmentBuilder();
        where=new AndPredicateSegment(true);
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }

    public SqlSegment0Builder getSet() {
        return set;
    }

    public PredicateSegment getWhere() {
        return where;
    }
}
