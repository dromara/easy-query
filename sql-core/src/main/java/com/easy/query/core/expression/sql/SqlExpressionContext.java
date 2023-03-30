package com.easy.query.core.expression.sql;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.UpdateStrategyEnum;
import com.easy.query.core.expression.sql.internal.EasyBehavior;

import java.util.List;

/**
 * @FileName: QueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:05
 * @author xuejiaming
 */
public interface SqlExpressionContext {
    EasyQueryRuntimeContext getRuntimeContext();

     List<SQLParameter> getParameters();
     void addParameter(SQLParameter parameter);

     String getAlias();
     String createTableAlias();
    String getQuoteName(String value);
    void extractParameters(SqlExpressionContext sqlExpressionContext);
    void clearParameters();
    void deleteThrow(boolean ifDeleteThrowException);
    boolean isDeleteThrow();
    EasyBehavior getBehavior();
    void useUpdateStrategy(UpdateStrategyEnum updateStrategy);
    UpdateStrategyEnum getUpdateStrategy();
    void setVersion(Object version);
    Object getVersion();
}
