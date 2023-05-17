package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.EntityPrepareParseResult;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySQLExpression;

import java.util.List;

/**
 * create time 2023/4/25 21:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityExecutionCreator extends BaseEntityExecutionCreator{

    public EntityExecutionCreator(String dataSource, EntityPrepareParseResult entityPrepareParseResult) {
        this(dataSource,entityPrepareParseResult.getEntityExpressionBuilder(),entityPrepareParseResult.getEntities());
    }
    public EntityExecutionCreator(String dataSource, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities) {
        super(dataSource,entityExpressionBuilder,entities);
    }
    @Override
    protected EasyEntitySQLExpression createEasySQLExpression(Object entity) {
        if(entityExpressionBuilder instanceof EntityUpdateExpressionBuilder){
            return ((EntityUpdateExpressionBuilder)entityExpressionBuilder).toExpression(entity);
        }else{
            return entityExpressionBuilder.toExpression();
        }
    }

    @Override
    protected boolean getFillAutoIncrement() {
        return false;
    }
}
