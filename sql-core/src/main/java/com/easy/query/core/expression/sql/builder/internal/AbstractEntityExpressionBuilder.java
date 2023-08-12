package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 文件说明
 * @Date: 2023/3/6 08:58
 * @author xuejiaming
 */
public abstract class AbstractEntityExpressionBuilder implements EntityExpressionBuilder {
    protected final ExpressionContext expressionContext;
    protected final QueryRuntimeContext runtimeContext;
    protected final Class<?> queryClass;
    protected final List<EntityTableExpressionBuilder> tables;

    public AbstractEntityExpressionBuilder(ExpressionContext expressionContext,Class<?> queryClass){
        this.expressionContext = expressionContext;
        this.runtimeContext = expressionContext.getRuntimeContext();
        this.queryClass = queryClass;
        this.tables = new ArrayList<>(10);
    }

    @Override
    public Class<?> getQueryClass() {
        return queryClass;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return expressionContext.getRuntimeContext();
    }

    @Override
    public void addSQLEntityTableExpression(EntityTableExpressionBuilder tableExpression) {
        if(tableExpression.getEntityTable().getEntityMetadata().isSharding()){
            expressionContext.useSharding();
        }
        expressionContext.getTableContext().addTable(tableExpression.getEntityTable());
        tables.add(tableExpression);
    }

    @Override
    public List<EntityTableExpressionBuilder> getTables() {
        return tables;
    }
    @Override
    public void setLogicDelete(boolean logicDelete) {
        if(logicDelete){
            expressionContext.getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }else{
            expressionContext.getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
    }
}
