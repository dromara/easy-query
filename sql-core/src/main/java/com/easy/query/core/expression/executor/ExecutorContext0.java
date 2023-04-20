package com.easy.query.core.expression.executor;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.sql.EntityExpression;

import java.util.List;

/**
 * create time 2023/4/18 21:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExecutorContext0 {
    private final EntityExpression entityExpression;
    private final List<SQLParameter> parameters;

    public ExecutorContext0(EntityExpression entityExpression, List<SQLParameter> parameters){

        this.entityExpression = entityExpression;
        this.parameters = parameters;
    }

    public EntityExpression getEntityExpression() {
        return entityExpression;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }
}
