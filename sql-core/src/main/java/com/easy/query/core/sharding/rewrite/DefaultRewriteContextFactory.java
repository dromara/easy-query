package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.sharding.route.RouteContext;

/**
 * create time 2023/4/20 14:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRewriteContextFactory implements RewriteContextFactory{
    @Override
    public RewriteContext createRewriteContext(RouteContext routeContext) {
        EasyEntitySqlExpression easyEntitySqlExpression = routeContext.getEntitySqlExpression();
        if(easyEntitySqlExpression instanceof EasyQuerySqlExpression){
            return createQueryRewriteContext((EasyQuerySqlExpression) easyEntitySqlExpression);
        }
       throw new UnsupportedOperationException();
    }

    public RewriteContext createQueryRewriteContext(EasyQuerySqlExpression easyQuerySqlExpression){
        EasyQuerySqlExpression easySqlExpression = (EasyQuerySqlExpression)easyQuerySqlExpression.cloneSqlExpression();
        if(easySqlExpression.hasLimit()){
            long rows = easySqlExpression.getRows();
            long offset = easySqlExpression.getOffset();
            if(offset>0){
                easySqlExpression.setOffset(0);
            }
            easySqlExpression.setRows(offset+rows);
        }

        return new RewriteContext(easyQuerySqlExpression,easySqlExpression);
    }
}
