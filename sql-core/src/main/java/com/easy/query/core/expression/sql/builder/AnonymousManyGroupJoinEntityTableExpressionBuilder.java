package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectExpressionBuilder;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;

import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/3/31 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AnonymousManyGroupJoinEntityTableExpressionBuilder extends AnonymousEntityTableExpressionBuilder {
    Integer addManyGroupJoinProjectExpression(ManyGroupJoinProjectKey manyGroupJoinProjectKey);
    String[] getDefaultSelectKeys();
    Map<ManyGroupJoinProjectKey, Integer> getProjectAliasMap();

//    AnonymousEntityTableExpressionBuilder getManyJoinEntityTableExpressionBuilder();
}
