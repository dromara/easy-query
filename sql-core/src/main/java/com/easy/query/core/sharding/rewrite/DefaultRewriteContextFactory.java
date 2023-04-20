package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
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
        EntityExpression entityExpression = routeContext.getEntityExpression();
        if(entityExpression instanceof EntityQueryExpression){
            return createQueryRewriteContext((EntityQueryExpression) entityExpression);
        }
       throw new UnsupportedOperationException();
    }

    public RewriteContext createQueryRewriteContext(EntityQueryExpression entityQueryExpression){
        EntityQueryExpression cloneSqlQueryExpression = entityQueryExpression.cloneSqlQueryExpression();
        if(entityQueryExpression.hasLimit()){
            long rows = entityQueryExpression.getRows();
            long offset = entityQueryExpression.getOffset();
            if(offset>0){
                cloneSqlQueryExpression.setOffset(0);
            }
            cloneSqlQueryExpression.setRows(offset+rows);
        }

        return new RewriteContext(entityQueryExpression,cloneSqlQueryExpression);
    }
}
