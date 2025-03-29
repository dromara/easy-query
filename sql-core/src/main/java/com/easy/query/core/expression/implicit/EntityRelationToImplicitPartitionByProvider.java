package com.easy.query.core.expression.implicit;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;

/**
 * create time 2025/3/29 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityRelationToImplicitPartitionByProvider {

    <T1> AnonymousManyJoinEntityTableExpressionBuilder toImplicitPartitionBy(Class<T1> entityClass, EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, String fullName, int index, QueryRuntimeContext runtimeContext, SQLExpression1<ClientQueryable<T1>> clientQueryableSQLExpression);
}
