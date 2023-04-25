package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;

/**
 * create time 2023/4/20 14:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRewriteContextFactory implements RewriteContextFactory{
    @Override
    public void rewriteExpression(PrepareParseResult prepareParseResult) {
        if(prepareParseResult instanceof QueryPrepareParseResult){
             rewriteQueryExpression((QueryPrepareParseResult) prepareParseResult);
        }
    }

    public void rewriteQueryExpression(QueryPrepareParseResult queryPrepareParseResult){
        EasyQuerySqlExpression easyQuerySqlExpression = queryPrepareParseResult.getEasyQuerySqlExpression();
        if(easyQuerySqlExpression.hasLimit()){
            long rows = easyQuerySqlExpression.getRows();
            long offset = easyQuerySqlExpression.getOffset();
            if(offset>0){
                easyQuerySqlExpression.setOffset(0);
            }
            easyQuerySqlExpression.setRows(offset+rows);
        }
    }
}
