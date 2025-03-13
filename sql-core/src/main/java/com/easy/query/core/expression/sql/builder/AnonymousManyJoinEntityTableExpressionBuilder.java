package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;

import java.util.Map;

/**
 * create time 2023/3/31 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AnonymousManyJoinEntityTableExpressionBuilder extends AnonymousEntityTableExpressionBuilder {
    Integer addManyGroupJoinProjectExpression(ManyGroupJoinProjectKey manyGroupJoinProjectKey);
    String[] getDefaultSelectKeys();
    Map<ManyGroupJoinProjectKey, Integer> getProjectAliasMap();

//    AnonymousEntityTableExpressionBuilder getManyJoinEntityTableExpressionBuilder();
}
