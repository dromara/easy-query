package com.easy.query.core.expression.implicit;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
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
public interface EntityRelationToImplicitGroupProvider {
    AnonymousManyJoinEntityTableExpressionBuilder toImplicitGroup(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, ManyConfiguration manyConfiguration);

}
