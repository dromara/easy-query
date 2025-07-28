package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.List;
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

    void addGroupJoinPredicateSegmentContext(GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext);
    List<GroupJoinPredicateSegmentContext> getGroupJoinPredicateSegmentContexts();

//    AnonymousEntityTableExpressionBuilder getManyJoinEntityTableExpressionBuilder();
}
