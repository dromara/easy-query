package org.easy.query.core.expression.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.query.builder.SqlTableInfo;


/**
 * @FileName: InsertContext.java
 * @Description: 文件说明
 * @Date: 2023/2/22 19:01
 * @Created by xuejiaming
 */
public class InsertContext extends AbstractSqlContext {
    private final SqlBuilderSegment columns;

    public InsertContext(EasyQueryRuntimeContext runtimeContext){
        super(runtimeContext);
        columns=new ProjectSqlBuilderSegment();
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }
    public SqlBuilderSegment getColumns() {
        return columns;
    }
}
