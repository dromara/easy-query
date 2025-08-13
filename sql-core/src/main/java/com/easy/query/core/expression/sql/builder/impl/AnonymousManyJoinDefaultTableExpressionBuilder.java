package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnit;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnitItem;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * create time 2023/3/3 23:31
 */
public class AnonymousManyJoinDefaultTableExpressionBuilder extends AnonymousDefaultTableExpressionBuilder implements AnonymousManyJoinEntityTableExpressionBuilder {
    private final ExpressionContext expressionContext;
    private final String[] defaultSelectKeys;

    private final Map<ManyGroupJoinProjectKey, Integer> projectAliasMap = new HashMap<>();
    private final List<GroupJoinPredicateSegmentContext> groupJoinPredicateSegmentContexts = new ArrayList<>();

    public AnonymousManyJoinDefaultTableExpressionBuilder(ExpressionContext expressionContext, TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder, String[] defaultSelectKeys) {
        super(entityTable, multiTableType, entityQueryExpressionBuilder);
        this.expressionContext = expressionContext;
        this.defaultSelectKeys = defaultSelectKeys;
    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {


        AnonymousManyJoinEntityTableExpressionBuilder anonymousTableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createAnonymousManyGroupEntityTableExpressionBuilder(expressionContext, entityTable, multiTableType, entityQueryExpressionBuilder.cloneEntityExpressionBuilder(), defaultSelectKeys);
        if (on != null) {
            on.copyTo(anonymousTableExpressionBuilder.getOn());
        }
        anonymousTableExpressionBuilder.setTableLinkAs(this.linkAs);
        anonymousTableExpressionBuilder.getProjectAliasMap().putAll(this.projectAliasMap);
        for (GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext : this.groupJoinPredicateSegmentContexts) {
            groupJoinPredicateSegmentContext.setPredicateSegmentAs(null);
            anonymousTableExpressionBuilder.getGroupJoinPredicateSegmentContexts().add(groupJoinPredicateSegmentContext);
        }
        return anonymousTableExpressionBuilder;
    }

    @Override
    public EntityTableSQLExpression toExpression() {
        if (!expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.GROUP_JOIN_NOT_ALLOW_AUTO_MERGE)) {
            //子查询转groupJoin条件都以case when出现在select projects中可能会存在有直接any的表达式那么这种情况不应该合并表达式
            if (this.groupJoinPredicateSegmentContexts.stream().map(o->{
                o.setPredicateSegmentAs(null);
                return o;
            }).allMatch(context -> context.getPredicateSegment() != null)) {
                //如果只有一个那么应该直接添加到where中
                if (EasyCollectionUtil.isSingle(this.groupJoinPredicateSegmentContexts)) {
                    GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext = this.groupJoinPredicateSegmentContexts.get(0);
                    groupJoinPredicateSegmentContext.setPredicateSegmentAs(predicateSegment -> null);
                    PredicateSegment predicateSegment = groupJoinPredicateSegmentContext.getPredicateSegment();
                    if(predicateSegment.isNotEmpty()){
                        entityQueryExpressionBuilder.getWhere().addPredicateSegment(predicateSegment.clonePredicateSegment());
                    }
                } else {
                    List<List<PredicateUnit>> predicateUnitList = this.groupJoinPredicateSegmentContexts.stream().map(content -> {
                        return content.getPredicateSegment().getFlatAndPredicateSegments().stream().map(ps -> {
                            ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(entityQueryExpressionBuilder.getExpressionContext().getTableContext());
                            String sql = ps.toSQL(toSQLContext);
                            String parameterString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());
                            return new PredicateUnit(sql, parameterString, ps, content);
                        }).collect(Collectors.toList());
                    }).sorted((a, b) -> a.size() - b.size()).collect(Collectors.toList());//将最短的放在第一位后续按最短的来进行判断
                    Map<String, PredicateUnitItem> commonPredicateUnits = EasySQLSegmentUtil.findCommonPredicateUnits(predicateUnitList);

                    for (PredicateUnitItem value : commonPredicateUnits.values()) {
                        PredicateSegment appendPredicateSegment = value.mainPredicateUnit.predicateSegment;
                        if(appendPredicateSegment.isNotEmpty()){
                            entityQueryExpressionBuilder.getWhere().addPredicateSegment(appendPredicateSegment.clonePredicateSegment());
                        }
                        for (PredicateUnit predicateUnit : value.predicateUnits) {
                            predicateUnit.groupJoinPredicateSegmentContext.setPredicateSegmentAs(predicateSegment -> null);
                        }
                    }
//                    List<List<PredicateUnit>> predicateUnitList = this.predicateSegments.stream().map(predicateSegment -> {
//                        return predicateSegment.getFlatAndPredicateSegments().stream().map(ps -> {
//                            ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(entityQueryExpressionBuilder.getExpressionContext().getTableContext());
//                            String sql = ps.toSQL(toSQLContext);
//                            String parameterString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());
//                            return new PredicateUnit(sql, parameterString, ps, predicateSegment);
//                        }).collect(Collectors.toList());
//                    }).sorted((a, b) -> a.size() - b.size()).collect(Collectors.toList());//将最短的放在第一位后续按最短的来进行判断
//                    Map<String, PredicateUnitItem> commonPredicateUnits = EasySQLSegmentUtil.findCommonPredicateUnits(predicateUnitList);
//
//                    for (PredicateUnitItem value : commonPredicateUnits.values()) {
//                        entityQueryExpressionBuilder.getWhere().addPredicateSegment(value.mainPredicateUnit.predicateSegment.clonePredicateSegment());
//                        for (PredicateUnit predicateUnit : value.predicateUnits) {
//                            predicateUnit.parentPredicateSegment.removeChildren(predicateUnit.predicateSegment);
//                        }
//                    }
                }
            }
        }


        EntityTableSQLExpression anonymousTableSQLExpression = runtimeContext.getExpressionFactory().createAnonymousEntityTableSQLExpression(entityTable, multiTableType, entityQueryExpressionBuilder.toExpression(), runtimeContext);
        if (EasySQLSegmentUtil.isNotEmpty(on)) {
            anonymousTableSQLExpression.setOn(on.clonePredicateSegment());
        }
        anonymousTableSQLExpression.setTableNameAs(tableNameAs);
        anonymousTableSQLExpression.setSchemaAs(schemaAs);
        anonymousTableSQLExpression.setLinkAs(linkAs);
        return anonymousTableSQLExpression;
    }

    @Override
    public Integer addManyGroupJoinProjectExpression(ManyGroupJoinProjectKey relationTableKey) {
        int projectIndex = entityQueryExpressionBuilder.getProjects().getSQLSegments().size();
        return projectAliasMap.putIfAbsent(relationTableKey, projectIndex + 1);
    }

    @Override
    public String[] getDefaultSelectKeys() {
        return defaultSelectKeys;
    }

    @Override
    public Map<ManyGroupJoinProjectKey, Integer> getProjectAliasMap() {
        return projectAliasMap;
    }

    @Override
    public void addGroupJoinPredicateSegmentContext(GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext) {
        this.groupJoinPredicateSegmentContexts.add(groupJoinPredicateSegmentContext);
    }

    @Override
    public List<GroupJoinPredicateSegmentContext> getGroupJoinPredicateSegmentContexts() {
        return groupJoinPredicateSegmentContexts;
    }

    //    @Override
//    public AnonymousEntityTableExpressionBuilder getManyJoinEntityTableExpressionBuilder() {
//        EntityQueryExpressionBuilder entityQueryExpressionBuilder = this.getEntityQueryExpressionBuilder();
//        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
//        return (AnonymousEntityTableExpressionBuilder) table;q
//    }

}
