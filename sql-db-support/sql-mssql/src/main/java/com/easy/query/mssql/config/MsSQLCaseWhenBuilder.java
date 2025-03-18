package com.easy.query.mssql.config;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParamExpression;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.extension.casewhen.DefaultCaseWhenBuilder;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.func.SQLFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * create time 2025/3/18 20:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLCaseWhenBuilder extends DefaultCaseWhenBuilder {

    public MsSQLCaseWhenBuilder(ExpressionContext expressionContext) {
        super(expressionContext);
    }

    @Override
    public SQLFunction elseEnd(ParamExpression paramExpression) {
        return super.elseEnd(processorConstBigDecimal(paramExpression));
    }

    @Override
    public SQLCaseWhenBuilder caseWhen(SQLExpression1<Filter> predicate, ParamExpression paramExpression) {
        return super.caseWhen(predicate, processorConstBigDecimal(paramExpression));
    }

    private ParamExpression processorConstBigDecimal(ParamExpression paramExpression) {
        if (paramExpression instanceof ColumnConstParamExpression) {
            ColumnConstParamExpression columnConstParamExpression = (ColumnConstParamExpression) paramExpression;
            Object constValue = columnConstParamExpression.getConstValue();
            if (constValue instanceof BigDecimal) {
                throw new EasyQueryInvalidOperationException("Since the official mssql-jdbc driver may cause BigDecimal precision loss, please override the current type and set scale when using BigDecimal in CASE WHEN statements.");
//                BigDecimal constBigDecimal = (BigDecimal) constValue;
//                if (constBigDecimal.scale() < 19) {
//                    constBigDecimal = constBigDecimal.setScale(19, RoundingMode.HALF_UP);
//                    return new ColumnConstParameterExpressionImpl(constBigDecimal);
//                }
            }
        }
        return paramExpression;
    }
}
