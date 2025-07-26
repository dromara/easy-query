package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnit;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnitResult;
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
    private final List<PredicateSegment> predicateSegments = new ArrayList<>();

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
        anonymousTableExpressionBuilder.getPredicateSegments().addAll(this.predicateSegments);
        return anonymousTableExpressionBuilder;
    }

    @Override
    public EntityTableSQLExpression toExpression() {
        if (!entityQueryExpressionBuilder.hasWhere() && expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.SUB_QUERY_GROUP_JOIN_AUTO_MERGE)) {
            if (this.predicateSegments.stream().allMatch(predicateSegment -> predicateSegment != null)) {
                if (EasyCollectionUtil.isSingle(this.predicateSegments)) {
                    PredicateSegment predicateSegment = this.predicateSegments.get(0);
                    entityQueryExpressionBuilder.getWhere().addPredicateSegment(predicateSegment.clonePredicateSegment());
                    predicateSegment.reset();
                } else {
                    List<List<PredicateUnit>> predicateUnitList = this.predicateSegments.stream().map(predicateSegment -> {
                        return predicateSegment.getFlatAndPredicateSegments().stream().map(ps -> {
                            ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(entityQueryExpressionBuilder.getExpressionContext().getTableContext());
                            String sql = ps.toSQL(toSQLContext);
                            String parameterString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());
                            return new PredicateUnit(sql, parameterString, ps, predicateSegment);
                        }).collect(Collectors.toList());
                    }).collect(Collectors.toList());
                    PredicateUnitResult predicateUnitResult = EasySQLSegmentUtil.findCommonPredicateUnits(predicateUnitList);
                    List<PredicateUnit> samePredicateUnits = predicateUnitResult.samePredicateUnits;
                    if (EasyCollectionUtil.isNotEmpty(samePredicateUnits)) {
                        for (PredicateUnit commonPredicateUnit : samePredicateUnits) {
                            entityQueryExpressionBuilder.getWhere().addPredicateSegment(commonPredicateUnit.predicateSegment.clonePredicateSegment());
                        }
                        for (PredicateUnit removePredicateUnit : predicateUnitResult.removePredicateUnits) {
                            this.predicateSegments.forEach(predicateSegment -> {
                                if (removePredicateUnit.parentPredicateSegment == predicateSegment) {
                                    if (removePredicateUnit.predicateSegment == predicateSegment) {
                                        predicateSegment.reset();
                                    } else {
                                        predicateSegment.removeChildren(removePredicateUnit.predicateSegment);
                                    }
                                }
                            });
                        }
                    }
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
    public void addPredicateSegment(PredicateSegment predicateSegment) {
        this.predicateSegments.add(predicateSegment);
    }

    @Override
    public List<PredicateSegment> getPredicateSegments() {
        return predicateSegments;
    }

    //    @Override
//    public AnonymousEntityTableExpressionBuilder getManyJoinEntityTableExpressionBuilder() {
//        EntityQueryExpressionBuilder entityQueryExpressionBuilder = this.getEntityQueryExpressionBuilder();
//        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
//        return (AnonymousEntityTableExpressionBuilder) table;
//    }

}
