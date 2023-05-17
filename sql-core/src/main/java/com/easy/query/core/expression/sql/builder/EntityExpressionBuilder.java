package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;

import java.util.List;

/**
 * @Description: 文件说明
 * @Date: 2023/3/4 16:29
 * @author xuejiaming
 */
public interface EntityExpressionBuilder extends ExpressionBuilder {
    ExpressionContext getExpressionContext();
    QueryRuntimeContext getRuntimeContext();
    void addSQLEntityTableExpression(EntityTableExpressionBuilder tableExpression);
    List<EntityTableExpressionBuilder> getTables();
    default EntityTableExpressionBuilder getTable(int index){
        return getTables().get(index);
    }
    default EntityTableExpressionBuilder getRecentlyTable(){
        int size = getTables().size();
        if(size==0){
            throw new EasyQueryInvalidOperationException("cant get recently table");
        }
        return getTable(size-1);
    }
    void setLogicDelete(boolean logicDelete);

    @Override
    EntitySQLExpression toExpression();

    EntityExpressionBuilder cloneEntityExpressionBuilder();

}
