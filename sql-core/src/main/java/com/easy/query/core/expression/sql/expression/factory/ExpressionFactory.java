package com.easy.query.core.expression.sql.expression.factory;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.AnonymousEntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityDeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityInsertSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;

import java.util.List;

/**
 * create time 2023/4/23 13:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExpressionFactory {
    EntityQuerySQLExpression createEasyQuerySQLExpression(QueryRuntimeContext runtimeContext);

    EntityInsertSQLExpression createEasyInsertSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression entityTableSQLExpression);

    EntityUpdateSQLExpression createEasyUpdateSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression entityTableSQLExpression);

    EntityDeleteSQLExpression createEasyDeleteSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression entityTableSQLExpression);

    EntityTableSQLExpression createEntityTableSQLExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext);

    EntityTableSQLExpression createAnonymousEntityTableSQLExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQuerySQLExpression entityQuerySQLExpression, QueryRuntimeContext runtimeContext);

    AnonymousEntityQuerySQLExpression createEasyAnonymousQuerySQLExpression(QueryRuntimeContext runtimeContext, String sql);

    AnonymousEntityQuerySQLExpression createEasyAnonymousUnionQuerySQLExpression(QueryRuntimeContext runtimeContext, List<EntityQuerySQLExpression> entityQuerySQLExpressions, SQLUnionEnum sqlUnion);
}
