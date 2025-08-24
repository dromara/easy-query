package com.easy.query.core.expression.many2group;

import com.easy.query.core.expression.parser.core.available.RelationTableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * create time 2025/8/24 20:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryExtraPredicateProvider {

    void process(EntityQueryExpressionBuilder mainEntityQueryExpressionBuilder, RelationTableAvailable relationTable,EntityQueryExpressionBuilder entityQueryExpressionBuilder);
}
