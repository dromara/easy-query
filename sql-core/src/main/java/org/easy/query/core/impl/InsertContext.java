package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.SelectSqlSegmentBuilder;
import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.query.builder.SqlTableInfo;


/**
 * @FileName: InsertContext.java
 * @Description: 文件说明
 * @Date: 2023/2/22 19:01
 * @Created by xuejiaming
 */
public class InsertContext extends AbstractSqlContext {
    private final SqlSegment0Builder columns;

    public InsertContext(EasyQueryRuntimeContext runtimeContext){
        super(runtimeContext);
        columns=new SelectSqlSegmentBuilder();
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }
    public SqlSegment0Builder getColumns() {
        return columns;
    }

}
