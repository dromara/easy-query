package com.easy.query.mysql.expression;

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
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.AnonymousEntityQuerySQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.AnonymousEntityTableSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.AnonymousUnionQuerySQLExpressionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/5/17 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLExpressionFactory implements ExpressionFactory {
    @Override
    public EntityQuerySQLExpression createEasyQuerySQLExpression(QueryRuntimeContext runtimeContext) {
        return new MySQLQuerySQLExpression(runtimeContext);
    }

    @Override
    public EntityInsertSQLExpression createEasyInsertSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression entityTableSQLExpression) {
        return new MySQLInsertSQLExpression(runtimeContext,entityTableSQLExpression);
    }

    @Override
    public EntityUpdateSQLExpression createEasyUpdateSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression entityTableSQLExpression) {
        return new MySQLUpdateSQLExpression(runtimeContext,entityTableSQLExpression);
    }

    @Override
    public EntityDeleteSQLExpression createEasyDeleteSQLExpression(QueryRuntimeContext runtimeContext, EntityTableSQLExpression entityTableSQLExpression) {
        return new MySQLDeleteSQLExpression(runtimeContext,entityTableSQLExpression);
    }

    @Override
    public EntityTableSQLExpression createEntityTableSQLExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, QueryRuntimeContext runtimeContext) {
        return new MySQLTableSQLExpression(entityTable,multiTableType,runtimeContext);
    }

    @Override
    public EntityTableSQLExpression createAnonymousEntityTableSQLExpression(TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQuerySQLExpression entityQuerySQLExpression, QueryRuntimeContext runtimeContext) {
        return new AnonymousEntityTableSQLExpressionImpl(entityTable,multiTableType, entityQuerySQLExpression,runtimeContext);
    }


    @Override
    public AnonymousEntityQuerySQLExpression createEasyAnonymousQuerySQLExpression(QueryRuntimeContext runtimeContext, String sql) {
        return new AnonymousEntityQuerySQLExpressionImpl(runtimeContext, sql);
    }

    @Override
    public AnonymousEntityQuerySQLExpression createEasyAnonymousUnionQuerySQLExpression(QueryRuntimeContext runtimeContext, List<EntityQuerySQLExpression> entityQuerySQLExpressions, SQLUnionEnum sqlUnion) {
        return new AnonymousUnionQuerySQLExpressionImpl(runtimeContext, new ArrayList<>(entityQuerySQLExpressions), sqlUnion);
    }
}
