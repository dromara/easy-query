package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnConstSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstValueExpression;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpression;
import com.easy.query.core.expression.segment.scec.expression.ConstParamExpression;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.text.MessageFormat;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnConstSegmentImpl implements ColumnConstSegment {
    protected final QueryRuntimeContext runtimeContext;
    protected final String columnConst;
    protected final SQLConstExpressionContext sqlConstExpressionContext;

    public ColumnConstSegmentImpl(QueryRuntimeContext runtimeContext, String columnConst, SQLConstExpressionContext sqlConstExpressionContext) {
        this.runtimeContext = runtimeContext;
        this.columnConst = columnConst;
        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public String getAlias() {
        return sqlConstExpressionContext.getAlias();
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new ColumnConstSegmentImpl(runtimeContext, columnConst, sqlConstExpressionContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        String resultColumnConst = getResultSQL(toSQLContext);

        String alias = getAlias();
        if (alias != null) {
            return resultColumnConst + " AS " + alias;
        }
        return resultColumnConst;
    }
    private String getResultSQL(ToSQLContext toSQLContext){

        if(EasyCollectionUtil.isNotEmpty(sqlConstExpressionContext.getExpressions())){
            Object[] args = new Object[sqlConstExpressionContext.getExpressions().size()];
            for (int i = 0; i < sqlConstExpressionContext.getExpressions().size(); i++) {
                String arg = process(i, toSQLContext);
                args[i]=arg;
            }
            MessageFormat messageFormat = new MessageFormat(columnConst);
            return messageFormat.format(args);
        }
        return columnConst;
    }

    private String process(int index,ToSQLContext toSQLContext) {
        ConstParamExpression constParamExpression = sqlConstExpressionContext.getExpressions().get(index);
        if (constParamExpression instanceof ColumnPropertyExpression) {
            ColumnPropertyExpression columnPropertyExpression = (ColumnPropertyExpression) constParamExpression;
            return columnPropertyExpression.toSQL(runtimeContext, toSQLContext);

        } else if (constParamExpression instanceof ColumnConstValueExpression) {
            ColumnConstValueExpression columnConstValueExpression = (ColumnConstValueExpression) constParamExpression;
            columnConstValueExpression.addParams(toSQLContext);
            return "?";
        }
        throw new EasyQueryInvalidOperationException("can not process ConstParamExpression:" + EasyClassUtil.getInstanceSimpleName(constParamExpression));
    }

}
