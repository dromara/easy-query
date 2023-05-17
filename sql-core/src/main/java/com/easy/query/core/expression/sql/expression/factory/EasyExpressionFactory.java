package com.easy.query.core.expression.sql.expression.factory;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.expression.EasyAnonymousQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EasyDeleteSQLExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSQLExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSQLExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSQLExpression;

import java.util.List;

/**
 * create time 2023/4/23 13:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyExpressionFactory {
    EasyQuerySQLExpression createEasyQuerySQLExpression(EasyQueryRuntimeContext runtimeContext);
    EasyInsertSQLExpression createEasyInsertSQLExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSQLExpression easyTableSQLExpression);
    EasyUpdateSQLExpression createEasyUpdateSQLExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSQLExpression easyTableSQLExpression);
    EasyDeleteSQLExpression createEasyDeleteSQLExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSQLExpression easyTableSQLExpression);
    EasyAnonymousQuerySQLExpression createEasyAnonymousQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, String sql);
    EasyAnonymousQuerySQLExpression createEasyAnonymousUnionQuerySQLExpression(EasyQueryRuntimeContext runtimeContext, List<EasyQuerySQLExpression> easyQuerySQLExpressions, SQLUnionEnum sqlUnion);
}
