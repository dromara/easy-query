package org.easy.query.core.basic.api.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.expression.builder.SelectSqlSegmentBuilder;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.List;


/**
 * @FileName: InsertContext.java
 * @Description: 文件说明
 * @Date: 2023/2/22 19:01
 * @Created by xuejiaming
 */
public class InsertContext extends AbstractSqlContext {
    private final SqlSegmentBuilder columns;

    public InsertContext(EasyQueryRuntimeContext runtimeContext){
        super(runtimeContext);
        columns=new SelectSqlSegmentBuilder();
    }
    public void addSqlTable(SqlTableInfo sqlTableInfo){
        this.tables.add(sqlTableInfo);
    }
    public SqlSegmentBuilder getColumns() {
        return columns;
    }
}
