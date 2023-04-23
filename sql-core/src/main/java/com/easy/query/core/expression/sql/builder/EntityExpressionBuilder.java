package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import java.util.List;

/**
 * @FileName: SqlEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:29
 * @author xuejiaming
 */
public interface EntityExpressionBuilder extends ExpressionBuilder {
    ExpressionContext getExpressionContext();
    EasyQueryRuntimeContext getRuntimeContext();
    void addSqlEntityTableExpression(EntityTableExpressionBuilder tableExpression);
    List<EntityTableExpressionBuilder> getTables();
    EntityTableExpressionBuilder getTable(int index);
    default EntityTableExpressionBuilder getRecentlyTable(){
        int size = getTables().size();
        if(size==0){
            throw new EasyQueryInvalidOperationException("cant get recently table");
        }
        return getTable(size-1);
    }
    String getQuoteName(String value);
    String getSqlOwnerColumn(EntityTableExpressionBuilder table, String propertyName);
    void setLogicDelete(boolean logicDelete);
    EntityExpressionBuilder cloneEntityExpressionBuilder();
}
