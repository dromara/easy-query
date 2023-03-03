package org.easy.query.core.expression.context;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.query.SqlEntityTableExpressionSegment;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.List;

/**
 * @FileName: SqlContext.java
 * @Description: 文件说明
 * @Date: 2023/2/23 21:54
 * @Created by xuejiaming
 */
public interface SqlContext0 {
     EasyQueryRuntimeContext getRuntimeContext();
     List<SqlEntityTableExpressionSegment> getTables();
     SqlEntityTableExpressionSegment getTable(int index);
     String getQuoteName(String value);
     String getSqlColumnSegment(SqlTableInfo table,String propertyName);
     List<SQLParameter> getParameters();
     void addParameter(SQLParameter parameter);

}
