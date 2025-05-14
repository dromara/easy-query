package com.easy.query.core.expression.builder.core;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/7/31 13:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNative<TChain> extends SQLNativeAble {
    /**
     * 参数格式化 占位符 {0} {1}
     *
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    @Override
    TChain sqlNativeSegment(String sqlSegment, SQLActionExpression1<SQLNativeExpressionContext> contextConsume);

    /**
     * 执行sql函数片段
     * @param defaultTable
     * @param sqlFunction
     * @return
     */
    default TChain sqlFunctionExecute(TableAvailable defaultTable, SQLFunction sqlFunction){
        return sqlNativeSegment(sqlFunction.sqlSegment(defaultTable),c->{
            sqlFunction.consume(new SQLNativeChainExpressionContextImpl(defaultTable,c));
        });
    }
}
