package com.easy.query.core.expression.many2group;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.PartitionByRelationTableKey;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.parser.core.available.RelationTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousDefaultTableExpressionBuilder;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2025/8/24 20:58
 * 默认的子查询的额外条件合并逻辑
 * select ... from table1 a join (select ... form table where [relationId=?] group by relationId) where a.relationId= ?
 * 让外部关联关系逻辑进行合并到子查询转groupJoin处 使groupJoin性能更加高效
 *
 * @author xuejiaming
 */
public class DefaultSubQueryExtraPredicateProvider implements SubQueryExtraPredicateProvider {
    @Override
    public void process(EntityQueryExpressionBuilder mainEntityQueryExpressionBuilder, RelationTableAvailable relationTable, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        QueryRuntimeContext runtimeContext = mainEntityQueryExpressionBuilder.getRuntimeContext();
        RelationTableKey relationTableKey = relationTable.getRelationTableKey();
        String property = relationTableKey.getProperty();
        NavigateMetadata navigateMetadata = relationTable.getOriginalTable().getEntityMetadata().getNavigateNotNull(property);

        String[] selfProperties = navigateMetadata.getSelfPropertiesOrPrimary();
        String[] targetProperties = getTargetProperties(navigateMetadata, runtimeContext);
        if (mainEntityQueryExpressionBuilder.hasWhere()) {

            List<PredicateSegment> flatAndPredicateSegments = mainEntityQueryExpressionBuilder.getWhere().getFlatAndPredicateSegments();
            WherePredicate<Object> wherePredicate = getWherePredicate(relationTableKey, entityQueryExpressionBuilder);
            getWhereExtraPredicateSegment(flatAndPredicateSegments, relationTable.getOriginalTable(), selfProperties, targetProperties, wherePredicate);
        }
    }

    private WherePredicate<Object> getWherePredicate(RelationTableKey relationTableKey, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        SQLExpressionInvokeFactory sqlExpressionInvokeFactory = entityQueryExpressionBuilder.getRuntimeContext().getSQLExpressionInvokeFactory();
        if (relationTableKey instanceof PartitionByRelationTableKey) {
            EntityQueryExpressionBuilder queryExpressionBuilder = ((AnonymousDefaultTableExpressionBuilder) entityQueryExpressionBuilder.getTable(0)).getEntityQueryExpressionBuilder();
            return sqlExpressionInvokeFactory.createWherePredicate(queryExpressionBuilder.getTable(0).getEntityTable(), queryExpressionBuilder, queryExpressionBuilder.getWhere());
        }
        return sqlExpressionInvokeFactory.createWherePredicate(entityQueryExpressionBuilder.getTable(0).getEntityTable(), entityQueryExpressionBuilder, entityQueryExpressionBuilder.getWhere());
    }

    private String[] getTargetProperties(NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext) {
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany) {
            if (navigateMetadata.getMappingClass() != null) {
                return navigateMetadata.getSelfMappingProperties();
            }
        }
        return navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
    }

    private void getWhereExtraPredicateSegment(List<PredicateSegment> flatAndPredicateSegments, TableAvailable fromTable, String[] selfProperties, String[] targetProperties, WherePredicate<Object> wherePredicate) {

        for (PredicateSegment predicateSegment : flatAndPredicateSegments) {
            if (predicateSegment instanceof AndPredicateSegment) {
                AndPredicateSegment andPredicateSegment = (AndPredicateSegment) predicateSegment;

                List<Predicate> flatAndPredicates = andPredicateSegment.getFlatAndPredicates();
                if (EasyCollectionUtil.isNotEmpty(flatAndPredicates)) {
                    SubQueryExtraPredicateUnit subQueryExtraAndPredicateUnit = new SubQueryExtraAndPredicateUnit(andPredicateSegment, fromTable, selfProperties, targetProperties);
                    subQueryExtraAndPredicateUnit.invoke(wherePredicate);
                } else {

                    List<PredicateSegment> children = andPredicateSegment.getChildren();
                    if (children != null) {
                        boolean allMatch = children.stream().skip(1).allMatch(o -> o instanceof OrPredicateSegment);
                        if (allMatch) {
                            SubQueryExtraOrPredicateUnit subQueryExtraOrPredicateUnit = new SubQueryExtraOrPredicateUnit(andPredicateSegment, fromTable, selfProperties, targetProperties);
                            subQueryExtraOrPredicateUnit.invoke(wherePredicate);
                        }
                    }
                }
            }
        }
    }

}
