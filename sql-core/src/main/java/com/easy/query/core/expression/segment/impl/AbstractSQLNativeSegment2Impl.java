package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.text.MessageFormat;
import java.util.function.Function;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSQLNativeSegment2Impl {
    protected final ExpressionContext expressionContext;
    protected final SQLSegment sqlSegment;
    protected final Function<String, String> sqlSegmentFunction;
    protected final SQLNativeExpression sqlNativeExpression;

    public AbstractSQLNativeSegment2Impl(ExpressionContext expressionContext, SQLSegment sqlSegment, Function<String, String> sqlSegmentFunction, SQLNativeExpression sqlNativeExpression) {
        this.expressionContext = expressionContext;
        this.sqlSegment = sqlSegment;
        this.sqlSegmentFunction = sqlSegmentFunction;
        this.sqlNativeExpression = sqlNativeExpression;
    }

    public TableAvailable getTable() {
        return sqlNativeExpression.getTableOrNull();
    }

    public String getPropertyName() {
        return sqlNativeExpression.getPropertyOrNull();
    }

    public String getAlias() {
        return sqlNativeExpression.getAlias();
    }


    public String toSQL(ToSQLContext toSQLContext) {

        String resultColumnConst = getResultSQL(toSQLContext);

        String alias = getAlias();
        if (alias != null) {
            return resultColumnConst + " AS " + EasySQLExpressionUtil.getQuoteName(expressionContext.getRuntimeContext(), alias);
        }
        return resultColumnConst;
    }

    private String getResultSQL(ToSQLContext toSQLContext) {
        String sqlSegmentSQL = sqlSegmentFunction.apply(sqlSegment.toSQL(toSQLContext));

        if (EasyCollectionUtil.isNotEmpty(sqlNativeExpression.getExpressions())) {
            Object[] args = new Object[sqlNativeExpression.getExpressions().size()];
            for (int i = 0; i < sqlNativeExpression.getExpressions().size(); i++) {
                ParamExpression paramExpression = sqlNativeExpression.getExpressions().get(i);
                Object arg = EasySQLExpressionUtil.parseParamExpression(expressionContext, paramExpression, toSQLContext);
                args[i] = arg;
            }
            if (sqlNativeExpression.isKeepStyle()) {
                return MessageFormat.format(sqlSegmentSQL.replace("'", "''"), args);
            }
            return MessageFormat.format(sqlSegmentSQL, args);
        }
        return sqlSegmentSQL;
    }

    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public SQLSegment getSqlSegment() {
        return sqlSegment;
    }

    public Function<String, String> getSqlSegmentFunction() {
        return sqlSegmentFunction;
    }

    public SQLNativeExpression getSqlNativeExpression() {
        return sqlNativeExpression;
    }
}
