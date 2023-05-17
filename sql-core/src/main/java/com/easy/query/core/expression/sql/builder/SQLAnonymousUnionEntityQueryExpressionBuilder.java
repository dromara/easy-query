package com.easy.query.core.expression.sql.builder;

import java.util.List;

/**
 * create time 2023/5/17 09:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAnonymousUnionEntityQueryExpressionBuilder extends SQLEntityQueryExpressionBuilder {
    List<EntityQueryExpressionBuilder> getEntityQueryExpressionBuilders();
}
