package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.RelationTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.ColumnValue2SegmentImpl;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnit;
import com.easy.query.core.expression.segment.condition.predicate.PredicateUnitItem;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: EasyAnonymousEntityTableExpressionSegment.java
 * @Description: 匿名实体表表达式
 * create time 2023/3/3 23:31
 */
public class AnonymousManyJoinDefaultTableExpressionBuilder extends AnonymousDefaultTableExpressionBuilder implements AnonymousManyJoinEntityTableExpressionBuilder {
    private final EntityExpressionBuilder mainEntityExpressionBuilder;
    private final ExpressionContext expressionContext;
    private final String[] defaultSelectKeys;

    private final Map<ManyGroupJoinProjectKey, Integer> projectAliasMap = new HashMap<>();
    private final List<GroupJoinPredicateSegmentContext> groupJoinPredicateSegmentContexts = new ArrayList<>();

    public AnonymousManyJoinDefaultTableExpressionBuilder(EntityExpressionBuilder mainEntityExpressionBuilder, ExpressionContext expressionContext, TableAvailable entityTable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder, String[] defaultSelectKeys) {
        super(entityTable, multiTableType, entityQueryExpressionBuilder);
        this.mainEntityExpressionBuilder = mainEntityExpressionBuilder;
        this.expressionContext = expressionContext;
        this.defaultSelectKeys = defaultSelectKeys;
    }

    @Override
    public EntityTableExpressionBuilder copyEntityTableExpressionBuilder() {


        AnonymousManyJoinEntityTableExpressionBuilder anonymousTableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createAnonymousManyGroupEntityTableExpressionBuilder(mainEntityExpressionBuilder.cloneEntityExpressionBuilder(), expressionContext, entityTable, multiTableType, entityQueryExpressionBuilder.cloneEntityExpressionBuilder(), defaultSelectKeys);
        if (on != null) {
            on.copyTo(anonymousTableExpressionBuilder.getOn());
        }
        anonymousTableExpressionBuilder.setTableLinkAs(this.linkAs);
        anonymousTableExpressionBuilder.getProjectAliasMap().putAll(this.projectAliasMap);
        for (GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext : this.groupJoinPredicateSegmentContexts) {
            groupJoinPredicateSegmentContext.setPredicateSegmentAs(null);
            anonymousTableExpressionBuilder.getGroupJoinPredicateSegmentContexts().add(groupJoinPredicateSegmentContext.cloneGroupJoinPredicateSegmentContext());
        }
        return anonymousTableExpressionBuilder;
    }

    @Override
    public EntityTableSQLExpression toExpression() {

        //应该以配置项作为主要的
        if (!expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.GROUP_JOIN_NOT_ALLOW_AUTO_MERGE)) {
            //子查询转groupJoin条件都以case when出现在select projects中可能会存在有直接any的表达式那么这种情况不应该合并表达式
            if (this.groupJoinPredicateSegmentContexts.stream().map(o -> {
                o.setPredicateSegmentAs(null);
                return o;
            }).allMatch(context -> context.getPredicateSegment() != null)) {
                //如果只有一个那么应该直接添加到where中
                if (EasyCollectionUtil.isSingle(this.groupJoinPredicateSegmentContexts)) {
                    GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext = this.groupJoinPredicateSegmentContexts.get(0);
                    groupJoinPredicateSegmentContext.setPredicateSegmentAs(predicateSegment -> null);
                    PredicateSegment predicateSegment = groupJoinPredicateSegmentContext.getPredicateSegment();
                    if (predicateSegment.isNotEmpty()) {
                        entityQueryExpressionBuilder.getWhere().addPredicateSegment(predicateSegment.clonePredicateSegment());
                    }
                } else {
                    List<List<PredicateUnit>> predicateUnitList = this.groupJoinPredicateSegmentContexts.stream().map(content -> {
                        content.cloneGroupJoinPredicateSegmentContext();
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
                        if (appendPredicateSegment.isNotEmpty()) {
                            PredicateSegment predicateSegment = appendPredicateSegment.clonePredicateSegment();
                            entityQueryExpressionBuilder.getWhere().addPredicateSegment(predicateSegment);
                        }
                        //不应该移除除非只有一个了
                        for (PredicateUnit predicateUnit : value.predicateUnits) {
                            PredicateSegment predicateSegment = predicateUnit.groupJoinPredicateSegmentContext.getPredicateSegment();
                            predicateSegment.removeChildren(predicateUnit.predicateSegment);
//                            predicateUnit.groupJoinPredicateSegmentContext.setPredicateSegmentAs(predicateSegment -> null);
                        }
                    }
                }
            }

            //todo 还需要先添加开关
            if (mainEntityExpressionBuilder instanceof EntityQueryExpressionBuilder) {
                if (entityTable instanceof RelationTableAvailable) {
                    RelationTableAvailable relationTable = (RelationTableAvailable) entityTable;
                    RelationTableKey relationTableKey = relationTable.getRelationTableKey();
                    String property = relationTableKey.getProperty();
                    NavigateMetadata navigateMetadata = relationTable.getOriginalTable().getEntityMetadata().getNavigateNotNull(property);
                    //todo 处理
                    if (navigateMetadata.getRelationType() == RelationTypeEnum.OneToMany) {

                        String[] selfProperties = navigateMetadata.getSelfPropertiesOrPrimary();
                        String[] targetProperties = navigateMetadata.getTargetProperties();
                        EntityQueryExpressionBuilder mainEntityQueryExpressionBuilder = (EntityQueryExpressionBuilder) mainEntityExpressionBuilder;

                        if (mainEntityQueryExpressionBuilder.hasWhere()) {

                            List<Predicate> flatAndPredicates = mainEntityQueryExpressionBuilder.getWhere().getFlatAndPredicates();
                            SQLExpressionInvokeFactory sqlExpressionInvokeFactory = entityQueryExpressionBuilder.getRuntimeContext().getSQLExpressionInvokeFactory();
                            WherePredicate<Object> wherePredicate = sqlExpressionInvokeFactory.createWherePredicate(entityQueryExpressionBuilder.getTable(0).getEntityTable(), entityQueryExpressionBuilder, entityQueryExpressionBuilder.getWhere());
                            getWhereExtraPredicateSegment(flatAndPredicates, relationTable.getOriginalTable(), selfProperties, targetProperties, wherePredicate);
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

    private void getWhereExtraPredicateSegment(List<Predicate> flatAndPredicates, TableAvailable fromTable, String[] selfProperties, String[] targetProperties, WherePredicate<Object> wherePredicate) {

        for (Predicate predicate : flatAndPredicates) {

            if (predicate.getTable() == fromTable &&
                    (predicate.getOperator() == SQLPredicateCompareEnum.EQ || predicate.getOperator() == SQLPredicateCompareEnum.IN)) {
                int predicateIndex = getPredicateIndex(selfProperties, predicate.getPropertyName());
                if (predicateIndex > -1) {

                    String targetProperty = targetProperties[predicateIndex];
                    if (predicate.getOperator() == SQLPredicateCompareEnum.EQ) {
                        if (predicate instanceof ValuePredicate) {
                            ValuePredicate valuePredicate = (ValuePredicate) predicate;
                            SQLParameter parameter = valuePredicate.getParameter();
                            if (parameter instanceof ConstSQLParameter) {
                                Object value = parameter.getValue();
                                wherePredicate.eq(targetProperty, value);
                            }
                        }
                    } else if (predicate.getOperator() == SQLPredicateCompareEnum.IN) {
                        if (predicate instanceof ValuesPredicate) {
                            ValuesPredicate valuesPredicate = (ValuesPredicate) predicate;
                            Collection<SQLParameter> parameters = valuesPredicate.getParameters();
                            List<Object> values = new ArrayList<>();
                            for (SQLParameter parameter : parameters) {
                                if (parameter instanceof ConstSQLParameter) {
                                    Object value = parameter.getValue();
                                    values.add(value);
                                }
                            }
                            if (EasyCollectionUtil.isNotEmpty(values)) {
                                wherePredicate.in(targetProperty, values);
                            }
                        }
                    }
                }
            }
        }
    }

    private int getPredicateIndex(String[] selfProperties, String compareProperty) {
        int r = -1;
        for (int i = 0; i < selfProperties.length; i++) {
            String selfProperty = selfProperties[i];
            if (Objects.equals(selfProperty, compareProperty)) {
                return i;
            }
        }
        return -1;
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
