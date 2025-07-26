package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.SQLFunctionTranslateImpl;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Objects;

/**
 * create time 2023/10/13 09:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSegmentParamExpressionImpl implements SQLSegmentParamExpression {
    private final SQLSegment sqlSegment;

    public SQLSegmentParamExpressionImpl(SQLSegment sqlSegment) {
        Objects.requireNonNull(sqlSegment, "sqlSegment can not be null");
        this.sqlSegment = sqlSegment;
    }

    public SQLSegmentParamExpressionImpl(SQLFunction sqlFunction, ExpressionContext expressionContext, TableAvailable defTable, QueryRuntimeContext runtimeContext, String alias) {
        Objects.requireNonNull(sqlFunction, "sqlFunction can not be null");
        this.sqlSegment = new SQLFunctionTranslateImpl(sqlFunction).toSQLSegment(expressionContext, defTable, runtimeContext, alias);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return sqlSegment.toSQL(toSQLContext);
    }

    @Override
    public SQLSegment getSQLSegment() {
        return sqlSegment;
    }

    @Override
    public void accept(TableVisitor visitor) {
        EasySQLSegmentUtil.sqlSegmentTableVisit(sqlSegment, visitor);
    }
}
