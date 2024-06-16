package com.easy.query.api.proxy.extension.ifequals;

import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.api.proxy.extension.casewhen.CaseWhenThenEntityBuilder;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.segment.scec.expression.SQLSegmentParamExpressionImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2024/6/16 09:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class IfEqualsEntityBuilder {
    private final CaseWhenEntityBuilder caseWhenEntityBuilder;

    public IfEqualsEntityBuilder(CaseWhenEntityBuilder caseWhenEntityBuilder){

        this.caseWhenEntityBuilder = caseWhenEntityBuilder;
    }
    public <TProperty> ColumnFunctionComparableAnyChainExpression<TProperty> elseEnd(Object elseValue){
        return caseWhenEntityBuilder.elseEnd(elseValue);
    }
}
