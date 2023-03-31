package com.easy.query.core.expression.sql;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import java.util.List;

/**
 * @FileName: SqlEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:29
 * @author xuejiaming
 */
public interface EntityExpression extends ExpressionSegment {
    ExpressionContext getExpressionContext();
    EasyQueryRuntimeContext getRuntimeContext();
    void addSqlEntityTableExpression(EntityTableExpression tableExpression);
    List<EntityTableExpression> getTables();
    EntityTableExpression getTable(int index);
    default EntityTableExpression getRecentlyTable(){
        int size = getTables().size();
        if(size==0){
            throw new EasyQueryInvalidOperationException("cant get recently table");
        }
        return getTable(size-1);
    }
    String getQuoteName(String value);
    String getSqlOwnerColumn(EntityTableExpression table, String propertyName);
    List<SQLParameter> getParameters();
    void addParameter(SQLParameter parameter);
    void setLogicDelete(boolean logicDelete);
}
