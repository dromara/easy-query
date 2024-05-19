package com.easy.query.core.func;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.impl.LazySQLSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/10/18 11:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFunctionTranslateImpl implements SQLFunctionTranslate {
    private final SQLFunction sqlFunction;

    public SQLFunctionTranslateImpl(SQLFunction sqlFunction){

        this.sqlFunction = sqlFunction;
    }
    @Override
    public SQLSegment toSQLSegment(ExpressionContext expressionContext, TableAvailable defTable, QueryRuntimeContext runtimeContext,String alias) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext,runtimeContext);

        return toSQLSegment(sqlNativeExpressionContext, defTable,alias,null);

    }

    @Override
    public SQLSegment toSQLSegment(SQLNativeExpressionContext sqlNativeExpressionContext, TableAvailable defTable, String alias, SQLActionExpression afterConsume) {

        return new LazySQLSegmentImpl(()->{
            QueryRuntimeContext runtimeContext = sqlNativeExpressionContext.getRuntimeContext();
            ExpressionContext expressionContext = sqlNativeExpressionContext.getExpressionContext();
            if(alias!=null){
                sqlNativeExpressionContext.setAlias(alias);
            }
            String sqlSegment = sqlFunction.sqlSegment(defTable);
            sqlFunction.consume(new SQLNativeChainExpressionContextImpl(defTable,sqlNativeExpressionContext));
            if(afterConsume!=null){
                afterConsume.apply();
            }
            return runtimeContext.getSQLSegmentFactory().createSQLNativeSegment(expressionContext, sqlSegment, sqlNativeExpressionContext);
        });
    }
}
