package com.easy.query.core.extension.casewhen;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2025/3/18 19:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLCaseWhenBuilder {
    ExpressionContext getExpressionContext();

    default SQLCaseWhenBuilder caseWhen(SQLActionExpression1<Filter> predicate, Object then) {
        return caseWhen(predicate, new ColumnConstParameterExpressionImpl(then));
    }

    SQLCaseWhenBuilder caseWhen(SQLActionExpression1<Filter> predicate, ParamExpression paramExpression);

    default SQLFunction elseEnd(Object elseValue) {
        return elseEnd(new ColumnConstParameterExpressionImpl(elseValue));
    }

    SQLFunction elseEnd(ParamExpression paramExpression);

    default SQLFunction elseEndColumn(TableAvailable table, String property) {
        return elseEnd(new ColumnPropertyExpressionImpl(table, property, getExpressionContext()));
    }

    default SQLFunction elseEndColumn(SQLTableOwner sqlTableOwner, String property) {
        return elseEndColumn(sqlTableOwner.getTable(), property);
    }
}
