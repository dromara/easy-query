package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.route.RouteContext;

/**
 * create time 2023/4/20 13:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class RewriteContext {
    private final EntityExpression originalEntityExpression;
    private final EntityExpression entityExpression;

    public RewriteContext(EntityExpression originalEntityExpression, EntityExpression entityExpression){
        this.originalEntityExpression = originalEntityExpression;
        this.entityExpression = entityExpression;
    }

    public EntityExpression getOriginalEntityExpression() {
        return originalEntityExpression;
    }

    public EntityExpression getEntityExpression() {
        return entityExpression;
    }
}
