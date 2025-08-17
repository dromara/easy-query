package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.tree.QueryPathTreeNode;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.implicit.EntityRelationToImplicitGroupProvider;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContextImpl;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.extension.casewhen.ClientPredicateCaseWhenBuilder;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/9/26 08:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultWhereObjectQueryExecutor implements WhereObjectQueryExecutor {
    private final WhereConditionProvider whereConditionProvider;

    public DefaultWhereObjectQueryExecutor(WhereConditionProvider whereConditionProvider) {
        this.whereConditionProvider = whereConditionProvider;
    }

    /**
     * 返回大于等于propNames长度的tableIndex索引数组
     *
     * @param propNames
     * @param tablesIndex
     * @return
     */
    private int[] buildTablesIndex(String[] propNames, int[] tablesIndex) {
        if (tablesIndex == null) {
            return new int[propNames.length];
        } else if (propNames.length != tablesIndex.length) {
            if (propNames.length < tablesIndex.length) {
                return tablesIndex;
            }
            int[] mergedIndex = new int[propNames.length];
            System.arraycopy(tablesIndex, 0, mergedIndex, 0, tablesIndex.length);
            return mergedIndex;
        }
        return tablesIndex;
    }


    private static QueryPathTreeNode findChild(QueryPathTreeNode node, String fieldName) {
        for (QueryPathTreeNode child : node.getChildren()) {
            if (fieldName.equals(child.getFieldName())) {
                return child;
            }
        }
        return null;
    }

    private void setSubQueryTreeNode(QueryPathTreeNode treeNode, TableAvailable leftTable, String property, EntityQueryExpressionBuilder entityExpressionBuilder, NavigateMetadata navigateMetadata, int tableIndex) {
        ExpressionContext expressionContext = entityExpressionBuilder.getExpressionContext();
        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        RelationTableKey defaultRelationTableKey = new DefaultRelationTableKey(leftTable, property);
        EntityRelationPropertyProvider entityRelationPredicateProvider = navigateMetadata.getEntityRelationPropertyProvider();
        boolean hasBehavior = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN);

        SubQueryModeEnum subQueryMode = entityExpressionBuilder.getSubQueryToGroupJoin(defaultRelationTableKey);
        boolean subQueryToGroupJoin = subQueryToGroupJoin(navigateMetadata.isSubQueryToGroupJoin(), hasBehavior, subQueryMode);

        if (subQueryToGroupJoin) {
            EntityRelationPropertyProvider entityRelationPropertyProvider = navigateMetadata.getEntityRelationPropertyProvider();
            if (entityRelationPropertyProvider instanceof EntityRelationToImplicitGroupProvider) {
                EntityRelationToImplicitGroupProvider entityRelationToImplicitGroupProvider = (EntityRelationToImplicitGroupProvider) entityRelationPropertyProvider;
                ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);
                ManyConfiguration finalManyConfiguration = new ManyConfiguration(clientQueryable -> {
                    if (manyConfiguration != null) {
                        manyConfiguration.getConfigureExpression().apply(clientQueryable);
                    }
                    if (hasBehavior) {
                        clientQueryable.configure(op -> {
                            op.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN);
                        });
                    }
                    return clientQueryable;
                });
                AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder = entityRelationToImplicitGroupProvider.toImplicitGroup(entityExpressionBuilder, leftTable, navigateMetadata, finalManyConfiguration);

                EntityTableExpressionBuilder manyJoinTableExpressionBuilder = anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder().getTable(0);
                treeNode.setEntityQueryExpressionBuilder(anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder());
                treeNode.setTable(tableIndex, manyJoinTableExpressionBuilder.getEntityTable());
                treeNode.setAnonymousManyJoinEntityTableExpressionBuilder(anonymousManyJoinEntityTableExpressionBuilder);

                FilterImpl filter = new FilterImpl(treeNode.getEntityQueryExpressionBuilder().getRuntimeContext(), treeNode.getEntityQueryExpressionBuilder().getExpressionContext(), new AndPredicateSegment(true), false, treeNode.getEntityQueryExpressionBuilder().getExpressionContext().getValueFilter());

                treeNode.setFilter(filter);
                return;
            }
        }

        ClientQueryable<?> implicitSubQuery = entityRelationPredicateProvider.toImplicitSubQuery(entityExpressionBuilder, leftTable, navigateMetadata, runtimeContext);

        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);

        if (manyConfiguration != null) {
            implicitSubQuery = EasyObjectUtil.typeCastNotNull(manyConfiguration.getConfigureExpression().apply(implicitSubQuery));
        }

        treeNode.setEntityQueryExpressionBuilder(implicitSubQuery.getSQLEntityExpressionBuilder());
        treeNode.setTable(tableIndex, implicitSubQuery.getSQLEntityExpressionBuilder().getTable(0).getEntityTable());
        FilterImpl filter = new FilterImpl(treeNode.getEntityQueryExpressionBuilder().getRuntimeContext(), treeNode.getEntityQueryExpressionBuilder().getExpressionContext(), treeNode.getEntityQueryExpressionBuilder().getWhere(), false, treeNode.getEntityQueryExpressionBuilder().getExpressionContext().getValueFilter());
        treeNode.setFilter(filter);
    }

    private boolean subQueryToGroupJoin(boolean isSubQueryToGroupJoin, boolean hasBehavior, SubQueryModeEnum subQueryMode) {
        if (subQueryMode == null) {
            return isSubQueryToGroupJoin || hasBehavior;
        } else {
            if (subQueryMode == SubQueryModeEnum.DEFAULT) {
                return isSubQueryToGroupJoin || hasBehavior;
            }
            if (subQueryMode == SubQueryModeEnum.SUB_QUERY_ONLY) {
                return false;
            }
            return subQueryMode == SubQueryModeEnum.GROUP_JOIN;
        }

    }

    public static class InternalField {
        public final int tableIndex;
        public final String fieldName;
        public final String[] fieldNames;

        public InternalField(int tableIndex, String fieldName) {
            this.tableIndex = tableIndex;
            this.fieldName = fieldName;

            this.fieldNames = fieldName.split("\\.");
        }
    }

    private List<InternalField> getQueryPropertiesOrNull(EasyWhereCondition q, String fieldName) {
        EasyWhereCondition.Mode mode = q.mode();
        switch (mode) {
            case SINGLE: {

                String realFieldName = EasyStringUtil.isBlank(q.propName()) ? fieldName : q.propName();

                return Collections.singletonList(new InternalField(q.tableIndex(), realFieldName));
            }
            case MULTI_OR: {
                String[] propNames = q.propNames();
                if (propNames == null || propNames.length == 0) {
                    throw new EasyQueryInvalidOperationException("where object mode: multi or,propNames can not be empty.");
                }
                int[] tablesIndexs = buildTablesIndex(propNames, q.tablesIndex());
                List<InternalField> internalFields = new ArrayList<>(propNames.length);
                for (int i = 0; i < propNames.length; i++) {
                    String propName = propNames[i];
                    int tablesIndex = tablesIndexs[i];
                    String realFieldName = EasyStringUtil.isBlank(propName) ? fieldName : propName;

                    internalFields.add(new InternalField(tablesIndex, realFieldName));
                }
                return internalFields;
            }
        }
        throw new EasyQueryInvalidOperationException("cant support where object mode:" + mode);
    }

    @Override
    public void whereObject(Object object, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        if (object == null) {
            return;
        }

        Collection<Field> allFields = EasyClassUtil.getAllFields(object.getClass());


        EntityTableExpressionBuilder tableExpressionBuilder = entityQueryExpressionBuilder.getTable(0);
        TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
        QueryPathTreeNode root = new QueryPathTreeNode(null);
        root.setTable(0, entityTable);
        root.setEntityQueryExpressionBuilder(entityQueryExpressionBuilder);

        FilterImpl filter = new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getWhere(), false, entityQueryExpressionBuilder.getExpressionContext().getValueFilter());

        root.setFilter(filter);
        for (Field field : allFields) {
            boolean accessible = field.isAccessible();

            try {
                field.setAccessible(true);

                EasyWhereCondition q = field.getAnnotation(EasyWhereCondition.class);
                if (q == null) {
                    continue;
                }

                Object val = field.get(object);

                if (Objects.isNull(val)) {
                    continue;
                }
                if (val instanceof String) {
                    if (EasyStringUtil.isBlank(String.valueOf(val)) && !q.allowEmptyStrings()) {
                        continue;
                    }
                }
                //获取映射的对象名称

                List<InternalField> fieldNames = getQueryPropertiesOrNull(q, field.getName());
                Iterator<InternalField> iterator = fieldNames.iterator();
                InternalField firstInternalField = iterator.next();
                QueryPathTreeNode.ConditionVal conditionVal = new QueryPathTreeNode.ConditionVal(q, val, field);
                QueryPathTreeNode queryPathTreeNode = buildQueryTree(root, firstInternalField, conditionVal);
                queryPathTreeNode.addCondition(conditionVal);

                while (iterator.hasNext()) {
                    InternalField internalField = iterator.next();
                    if (internalField.fieldNames.length != firstInternalField.fieldNames.length) {
                        throw new EasyQueryInvalidOperationException("In WhereCondition, when mode is set to MULTI_OR, all property paths specified in propNames must be of the same depth. first:[" + firstInternalField.fieldName + "],current:[" + internalField.fieldName + "]");
                    }
                    buildQueryTree(root, internalField, conditionVal);
                }
            } catch (Exception e) {
                throw new EasyQueryException(e);
            } finally {
                field.setAccessible(accessible);
            }

        }

        if (root.hasChildren()) {
            for (QueryPathTreeNode child : root.getChildren()) {
                acceptCondition(child, root, object);
            }
        }
    }

    private QueryPathTreeNode buildQueryTree(QueryPathTreeNode root, InternalField internalField, QueryPathTreeNode.ConditionVal conditionVal) {

        QueryPathTreeNode current = root;
        String[] fieldNames = internalField.fieldNames;
        int tableIndex = internalField.tableIndex;
        TableAvailable currentTable = current.getEntityQueryExpressionBuilder().getTable(tableIndex).getEntityTable();
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            boolean isLast = i == (fieldNames.length - 1);
            QueryPathTreeNode child = findChild(current, fieldName);
            if (child == null) {
                ColumnMetadata columnMetadata = isLast ? currentTable.getEntityMetadata().getColumnNotNull(fieldName) : null;
                NavigateMetadata navigateMetadata = !isLast ? currentTable.getEntityMetadata().getNavigateOrNull(fieldName) : null;
                child = new QueryPathTreeNode(fieldName);
                child.setNavigateMetadata(navigateMetadata);
                if (navigateMetadata != null) {
                    if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToOne || navigateMetadata.getRelationType() == RelationTypeEnum.OneToOne) {
                        child.setEntityQueryExpressionBuilder(current.getEntityQueryExpressionBuilder());
                        EntityRelationPropertyProvider entityRelationToImplicitProvider = navigateMetadata.getEntityRelationPropertyProvider();
                        if (entityRelationToImplicitProvider == null) {
                            throw new EasyQueryInvalidOperationException("entityRelationToImplicitProvider is null,Navigate property in non entity plz set supportNonEntity = true.");
                        }
                        TableAvailable implicitJoinTable = entityRelationToImplicitProvider.toImplicitJoin(current.getEntityQueryExpressionBuilder(), currentTable, fieldName);
                        child.setTable(tableIndex, implicitJoinTable);
                        child.setEntityQueryExpressionBuilder(current.getEntityQueryExpressionBuilder());
                        child.setFilter(current.getFilter());
                    } else {
                        //创建新的表达式查询builder
                        setSubQueryTreeNode(child, currentTable, fieldName, current.getEntityQueryExpressionBuilder(), navigateMetadata, tableIndex);
                    }

                } else if (columnMetadata != null) {
                    child.setTable(tableIndex, current.getTable(tableIndex));
                    child.setEntityQueryExpressionBuilder(current.getEntityQueryExpressionBuilder());
                    child.setFilter(current.getFilter());
                }
                current.addChild(child);
            }
            current = child;
            currentTable = child.getTable(tableIndex);
        }
        conditionVal.addFieldCondition(current.getFieldName(), current.getTable(tableIndex));
        return current;
    }

    private void acceptCondition(QueryPathTreeNode node, QueryPathTreeNode parent, Object whereObjectEntity) {
        if (EasyCollectionUtil.isNotEmpty(node.getConditions())) {
            for (QueryPathTreeNode.ConditionVal condition : node.getConditions()) {
                acceptCondition0(whereObjectEntity, node.getFilter(), condition.condition, condition.field, condition.val, condition.fieldConditions);
            }


        }
        for (QueryPathTreeNode child : node.getChildren()) {
            acceptCondition(child, node, whereObjectEntity);
        }
        if (node.getNavigateMetadata() != null) {
            if (node.getNavigateMetadata().getRelationType() == RelationTypeEnum.OneToMany || node.getNavigateMetadata().getRelationType() == RelationTypeEnum.ManyToMany) {
                if (node.getAnonymousManyJoinEntityTableExpressionBuilder() != null) {

                    PredicateSegment predicateSegment = node.getFilter().getRootPredicateSegment();
                    GroupJoinPredicateSegmentContextImpl groupJoinPredicateSegmentContext = new GroupJoinPredicateSegmentContextImpl(predicateSegment);
                    node.getAnonymousManyJoinEntityTableExpressionBuilder().addGroupJoinPredicateSegmentContext(groupJoinPredicateSegmentContext);
                    SQLFunction sqlFunction = new ClientPredicateCaseWhenBuilder(node.getEntityQueryExpressionBuilder().getRuntimeContext(), node.getEntityQueryExpressionBuilder().getExpressionContext(), groupJoinPredicateSegmentContext)
                            .then(1).elseEnd(null);

//                    count()case when
//                    this.groupJoinPredicateSegmentContext=

                    String any = getOrAppendGroupProjects(parent.getEntityQueryExpressionBuilder(), node.getAnonymousManyJoinEntityTableExpressionBuilder(), "any", sqlFunction);

                    new EasyClientQueryable<>(parent.getEntityQueryExpressionBuilder().getQueryClass(), parent.getEntityQueryExpressionBuilder())
                            .where(o -> {

                                o.getFilter().eq(node.getAnonymousManyJoinEntityTableExpressionBuilder().getEntityTable(), o.fx().nullOrDefault(any, false), true);
                            });
                } else {
                    new EasyClientQueryable<>(parent.getEntityQueryExpressionBuilder().getQueryClass(), parent.getEntityQueryExpressionBuilder())
                            .where(o -> {
                                o.exists(
                                        new EasyClientQueryable<>(node.getEntityQueryExpressionBuilder().getQueryClass(), node.getEntityQueryExpressionBuilder()).limit(1)
                                );
                            });
                }

            }
        }
    }


    public String getOrAppendGroupProjects(EntityExpressionBuilder entityExpressionBuilder, AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder, String methodName, SQLFunction sqlFunction) {

        EntityQueryExpressionBuilder entityQueryExpressionBuilder = anonymousManyJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
        TableAvailable manyGroupJoinTable = anonymousManyJoinEntityTableExpressionBuilder.getEntityTable();
        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();
        AsSelectorImpl asSelector = new AsSelectorImpl(entityQueryExpressionBuilder, projectSQLBuilderSegment, manyGroupJoinTable.getEntityMetadata());
        asSelector.sqlNativeSegment("(COUNT({0}) > 0)", c -> {
            c.sqlFunction(sqlFunction);
        });
        //表达式可能使用了隐式join需要将表进行对齐
        entityExpressionBuilder.getExpressionContext().getTableContext().extract(entityQueryExpressionBuilder.getExpressionContext().getTableContext());
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(entityExpressionBuilder.getExpressionContext().getTableContext());
        String sql = projectSQLBuilderSegment.toSQL(toSQLContext);
        String parameterString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());

        Integer projectIndex = anonymousManyJoinEntityTableExpressionBuilder.addManyGroupJoinProjectExpression(new ManyGroupJoinProjectKey(sql, parameterString, methodName));
        if (projectIndex == null) {
            SQLSegment sqlSegment = projectSQLBuilderSegment.getSQLSegments().get(0);
            int i = entityQueryExpressionBuilder.getProjects().getSQLSegments().size();
            projectIndex = i + 1;
            SQLEntityAliasSegment aliasSegment = (SQLEntityAliasSegment) sqlSegment;
            aliasSegment.setAlias("__" + methodName + projectIndex + "__");
            entityQueryExpressionBuilder.getProjects().getSQLSegments().add(sqlSegment);
        }
        SQLSegment sqlSegment = entityQueryExpressionBuilder.getProjects().getSQLSegments().get(projectIndex - 1);
        SQLEntityAliasSegment aliasSegment = (SQLEntityAliasSegment) sqlSegment;
        return aliasSegment.getAlias();
    }

    private void acceptCondition0(Object whereObjectEntity, Filter filter, EasyWhereCondition q, Field field, Object val, List<QueryPathTreeNode.FieldCondition> fieldConditions) {

        if (EasyCollectionUtil.isEmpty(fieldConditions)) {
            return;
        }

        EasyWhereCondition.Condition whereConditionType = whereConditionProvider.getQueryCondition(whereObjectEntity, field, val, q.type());
        switch (whereConditionType) {
            case EQUAL: {
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                            x.eq(fieldCondition.table, fieldCondition.property, val).or();
                        }
                    });
                } else {
                    filter.eq(fieldConditions.get(0).table, fieldConditions.get(0).property, val);
                }
            }
            break;
            case GREATER_THAN:
            case RANGE_LEFT_OPEN: {
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                            x.gt(fieldCondition.table, fieldCondition.property, val).or();
                        }
                    });
                } else {
                    filter.gt(fieldConditions.get(0).table, fieldConditions.get(0).property, val);
                }
            }
            break;
            case LESS_THAN:
            case RANGE_RIGHT_OPEN: {
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                            x.lt(fieldCondition.table, fieldCondition.property, val).or();
                        }
                    });
                } else {
                    filter.lt(fieldConditions.get(0).table, fieldConditions.get(0).property, val);
                }
            }
            break;
            case LIKE:
            case LIKE_MATCH_LEFT:
            case LIKE_MATCH_RIGHT: {
                SQLLikeEnum sqlLike = getSQLLike(whereConditionType);
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                            x.like(fieldCondition.table, fieldCondition.property, val, sqlLike).or();
                        }
                    });
                } else {
                    filter.like(fieldConditions.get(0).table, fieldConditions.get(0).property, val, sqlLike);
                }
            }
            break;
            case CONTAINS:
            case STARTS_WITH:
            case ENDS_WITH: {
                SQLLikeEnum sqlLike = getSQLLike(whereConditionType);
                SQLFunc fx = filter.getRuntimeContext().fx();
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {

                            SQLFunction likeSQLFunction = fx.like(s -> {
                                s.column(fieldCondition.table, fieldCondition.property);
                                s.value(val);
                            }, true, sqlLike);
                            x.sqlNativeSegment(likeSQLFunction.sqlSegment(fieldCondition.table), c -> {
                                likeSQLFunction.consume(new SQLNativeChainExpressionContextImpl(fieldCondition.table, c));
                            }).or();

                        }
                    });
                } else {
                    TableAvailable table = fieldConditions.get(0).table;
                    String property = fieldConditions.get(0).property;
                    SQLFunction likeSQLFunction = fx.like(s -> {
                        s.column(table, property);
                        s.value(val);
                    }, true, sqlLike);
                    filter.sqlNativeSegment(likeSQLFunction.sqlSegment(table), c -> {
                        likeSQLFunction.consume(new SQLNativeChainExpressionContextImpl(table, c));
                    });
                }
            }
            break;
            case GREATER_THAN_EQUAL:
            case RANGE_LEFT_CLOSED: {
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                            x.ge(fieldCondition.table, fieldCondition.property, val).or();
                        }
                    });
                } else {
                    filter.ge(fieldConditions.get(0).table, fieldConditions.get(0).property, val);
                }
            }
            break;
            case LESS_THAN_EQUAL:
            case RANGE_RIGHT_CLOSED: {
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                            x.le(fieldCondition.table, fieldCondition.property, val).or();
                        }
                    });
                } else {
                    filter.le(fieldConditions.get(0).table, fieldConditions.get(0).property, val);
                }
            }
            break;
            case IN:
                if (val.getClass().isArray()) {
                    if (EasyArrayUtil.isNotEmpty((Object[]) val)) {
                        if (fieldConditions.size() > 1) {
                            filter.and(x -> {
                                for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                                    x.in(fieldCondition.table, fieldCondition.property, (Object[]) val).or();
                                }
                            });
                        } else {
                            filter.in(fieldConditions.get(0).table, fieldConditions.get(0).property, (Object[]) val);
                        }
                    }
                } else {
                    if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                        if (fieldConditions.size() > 1) {
                            filter.and(x -> {
                                for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                                    x.in(fieldCondition.table, fieldCondition.property, (Collection<?>) val).or();
                                }
                            });
                        } else {
                            filter.in(fieldConditions.get(0).table, fieldConditions.get(0).property, (Collection<?>) val);
                        }
                    }
                }
                break;
            case NOT_IN:
                if (val.getClass().isArray()) {
                    if (EasyArrayUtil.isNotEmpty((Object[]) val)) {
                        if (fieldConditions.size() > 1) {
                            filter.and(x -> {
                                for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                                    x.notIn(fieldCondition.table, fieldCondition.property, (Object[]) val).or();
                                }
                            });
                        } else {
                            filter.notIn(fieldConditions.get(0).table, fieldConditions.get(0).property, (Object[]) val);
                        }
                    }
                } else {
                    if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                        if (fieldConditions.size() > 1) {
                            filter.and(x -> {
                                for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                                    x.notIn(fieldCondition.table, fieldCondition.property, (Collection<?>) val).or();
                                }
                            });
                        } else {
                            filter.notIn(fieldConditions.get(0).table, fieldConditions.get(0).property, (Collection<?>) val);
                        }
                    }
                }
                break;
            case NOT_EQUAL: {
                if (fieldConditions.size() > 1) {
                    filter.and(x -> {
                        for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                            x.ne(fieldCondition.table, fieldCondition.property, val).or();
                        }
                    });
                } else {
                    filter.ne(fieldConditions.get(0).table, fieldConditions.get(0).property, val);
                }
            }
            break;
            case COLLECTION_EQUAL_OR: {
                if (val.getClass().isArray()) {
                    if (EasyArrayUtil.isNotEmpty((Object[]) val)) {

                        if (fieldConditions.size() > 1) {
                            filter.and(x -> {
                                for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                                    for (Object o : (Object[]) val) {
                                        x.eq(fieldCondition.table, fieldCondition.property, o).or();
                                    }
                                }
                            });
                        } else {
                            filter.and(f -> {
                                for (Object o : (Object[]) val) {
                                    f.eq(fieldConditions.get(0).table, fieldConditions.get(0).property, o).or();
                                }
                            });
                        }
                    }
                } else {
                    if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                        if (fieldConditions.size() > 1) {
                            filter.and(x -> {
                                for (QueryPathTreeNode.FieldCondition fieldCondition : fieldConditions) {
                                    for (Object o : (Collection<?>) val) {
                                        x.eq(fieldCondition.table, fieldCondition.property, o).or();
                                    }
                                }
                            });
                        } else {
                            filter.and(f -> {
                                for (Object o : (Collection<?>) val) {
                                    f.eq(fieldConditions.get(0).table, fieldConditions.get(0).property, o).or();
                                }
                            });
                        }
                    }
                }
            }
            break;
            case RANGE_OPEN:
            case RANGE_CLOSED:
            case RANGE_CLOSED_OPEN:
            case RANGE_OPEN_CLOSED: {
                MergeTuple2<SQLPredicateCompareEnum, SQLPredicateCompareEnum> sqlPredicateCompareEnum = EasyWhereCondition.Condition.getSQLPredicateCompareEnum(whereConditionType);
                if (val.getClass().isArray()) {
                    Object[] valArray = (Object[]) val;
                    if (EasyArrayUtil.isNotEmpty(valArray)) {
                        Object[] pairArray = {valArray[0], null};
                        if (valArray.length > 1) {
                            pairArray[1] = valArray[1];
                        }

                        rangePairArray(pairArray, fieldConditions, filter, sqlPredicateCompareEnum);
                    }
                } else {
                    Collection<?> valCollection = (Collection<?>) val;
                    if (EasyCollectionUtil.isNotEmpty(valCollection)) {
                        Object[] pairArray = getPairArray(valCollection);
                        rangePairArray(pairArray, fieldConditions, filter, sqlPredicateCompareEnum);
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    //数组处理
    private void rangePairArray(Object[] pairArray, List<QueryPathTreeNode.FieldCondition> queries, Filter filter, MergeTuple2<SQLPredicateCompareEnum, SQLPredicateCompareEnum> sqlPredicateCompareEnum) {

        if (queries.size() > 1) {
            filter.and(x -> {
                for (QueryPathTreeNode.FieldCondition fieldCondition : queries) {
                    if (pairArray[1] != null) {
                        x.compare(fieldCondition.table, fieldCondition.property, pairArray[0], sqlPredicateCompareEnum.t1)
                                .and()
                                .compare(fieldCondition.table, fieldCondition.property, pairArray[1], sqlPredicateCompareEnum.t2);
                    } else {
                        x.compare(fieldCondition.table, fieldCondition.property, pairArray[0], sqlPredicateCompareEnum.t1);
                    }
                }
            });
        } else {
            filter.and(f -> {
                if (pairArray[1] != null) {
                    f.compare(queries.get(0).table, queries.get(0).property, pairArray[0], sqlPredicateCompareEnum.t1)
                            .and()
                            .compare(queries.get(0).table, queries.get(0).property, pairArray[1], sqlPredicateCompareEnum.t2);
                } else {
                    f.compare(queries.get(0).table, queries.get(0).property, pairArray[0], sqlPredicateCompareEnum.t1);
                }
            });
        }
    }

    private Object[] getPairArray(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();
        Object first = iterator.next();
        Object[] array = {first, null};
        if (iterator.hasNext()) {
            array[1] = iterator.next();
        }
        return array;
    }

    private SQLLikeEnum getSQLLike(EasyWhereCondition.Condition like) {
        switch (like) {
            case LIKE:
            case CONTAINS:
                return SQLLikeEnum.LIKE_PERCENT_ALL;
            case LIKE_MATCH_LEFT:
            case STARTS_WITH:
                return SQLLikeEnum.LIKE_PERCENT_RIGHT;
            case LIKE_MATCH_RIGHT:
            case ENDS_WITH:
                return SQLLikeEnum.LIKE_PERCENT_LEFT;
        }
        throw new UnsupportedOperationException("where object cant get sql like");
    }
}
