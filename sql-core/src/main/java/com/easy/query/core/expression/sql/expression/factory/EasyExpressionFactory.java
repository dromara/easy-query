package com.easy.query.core.expression.sql.expression.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.expression.AnonymousQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.DeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.InsertSQLExpression;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.expression.sql.expression.UpdateSQLExpression;

import java.util.List;

/**
 * create time 2023/4/23 13:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyExpressionFactory {
    QuerySQLExpression createEasyQuerySQLExpression(EasyQueryRuntimeContext runtimeContext);
    InsertSQLExpression createEasyInsertSQLExpression(EasyQueryRuntimeContext runtimeContext, TableSQLExpression easyTableSQLExpression);
    UpdateSQLExpression createEasyUpdateSQLExpression(EasyQueryRuntimeContext runtimeContext, TableSQLExpression easyTableSQLExpression);
    DeleteSQLExpression createEasyDeleteSQLExpression(EasyQueryRuntimeContext runtimeContext, TableSQLExpression easyTableSQLExpression);
    AnonymousQuerySQLExpression createEasyAnonymousQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, String sql);
    AnonymousQuerySQLExpression createEasyAnonymousUnionQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, List<QuerySQLExpression> easyQuerySQLExpressions, SQLUnionEnum sqlUnion);
}
