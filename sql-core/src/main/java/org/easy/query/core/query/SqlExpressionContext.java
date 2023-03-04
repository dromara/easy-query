package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * @FileName: QueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:05
 * @Created by xuejiaming
 */
public interface SqlExpressionContext {
    EasyQueryRuntimeContext getRuntimeContext();

     List<SQLParameter> getParameters();
     void addParameter(SQLParameter parameter);

     String getAlias();
     String createTableAlias();
    String getQuoteName(String value);
    SqlExpressionContext cloneSqlExpressionContext();
}
