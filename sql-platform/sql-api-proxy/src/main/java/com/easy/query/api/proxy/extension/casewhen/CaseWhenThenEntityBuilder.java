package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.api.proxy.util.EasyParamExpressionUtil;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenThenEntityBuilder {

    private final SQLActionExpression sqlActionExpression;
    private final CaseWhenEntityBuilder caseWhenEntityBuilder;
    private final EntitySQLContext entitySQLContext;
    private final SQLCaseWhenBuilder sqlCaseWhenBuilder;

    public CaseWhenThenEntityBuilder(CaseWhenEntityBuilder caseWhenEntityBuilder, EntitySQLContext entitySQLContext, SQLCaseWhenBuilder sqlCaseWhenBuilder, SQLActionExpression sqlActionExpression) {
        this.caseWhenEntityBuilder = caseWhenEntityBuilder;
        this.entitySQLContext = entitySQLContext;
        this.sqlCaseWhenBuilder = sqlCaseWhenBuilder;
        this.sqlActionExpression = sqlActionExpression;
    }

    public <TV> CaseWhenEntityBuilder then(TV then) {
        sqlCaseWhenBuilder.caseWhen(filter -> {
            entitySQLContext._where(filter, sqlActionExpression);
        }, EasyParamExpressionUtil.getParamExpression(entitySQLContext,then));
        return caseWhenEntityBuilder;
    }
}