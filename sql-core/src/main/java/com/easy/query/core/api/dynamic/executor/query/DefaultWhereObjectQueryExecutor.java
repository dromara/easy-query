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
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
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
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * create time 2023/9/26 08:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultWhereObjectQueryExecutor implements WhereObjectQueryExecutor {


    private WhereObjectEntry checkStrict(EntityQueryExpressionBuilder entityQueryExpressionBuilder, boolean strictMode, String property, int tableIndex) {
        if (tableIndex < 0 || tableIndex > entityQueryExpressionBuilder.getTables().size() - 1) {
            if (strictMode) {
                throw new EasyQueryWhereInvalidOperationException("table index:[" + tableIndex + "] not found in query context");
            }
            return null;
        }
        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(tableIndex).getEntityTable();
        EntityMetadata entityMetadata = entityTable.getEntityMetadata();

        ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(property);
        if (columnMetadata == null) {
            if (strictMode) {
                throw new EasyQueryWhereInvalidOperationException("property name:[" + property + "] not found query entity class:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()));
            }
            return null;
        }
        return new WhereObjectEntry(entityTable, property);
    }

    /**
     * element为null
     *
     * @param entityQueryExpressionBuilder
     * @param q
     * @param fieldName
     * @return
     */
    private List<WhereObjectEntry> getQueryPropertiesOrNull(EntityQueryExpressionBuilder entityQueryExpressionBuilder, TableAvailable table, EasyWhereCondition q, String fieldName) {
        EasyWhereCondition.Mode mode = q.mode();
        boolean strictMode = q.strict();
        switch (mode) {
            case SINGLE: {
//                int tableIndex = q.tableIndex();
//
//                //获取映射的对象名称
//                String queryPropertyName = fieldName;
//
//                WhereObjectEntry whereObjectEntry = checkStrict(entityQueryExpressionBuilder, strictMode, queryPropertyName, tableIndex);
//                if (whereObjectEntry == null) {
//                    return Collections.emptyList();
//                }
                return Collections.singletonList(new WhereObjectEntry(table, fieldName));
            }
            case MULTI_OR: {
                String[] propNames = q.propNames();
                if (propNames == null || propNames.length == 0) {
                    throw new EasyQueryInvalidOperationException("where object mode: multi or,propNames can not be empty.");
                }
                int[] tablesIndex = buildTablesIndex(propNames, q.tablesIndex());
                List<WhereObjectEntry> whereObjectEntries = new ArrayList<>(propNames.length);
                for (int i = 0; i < propNames.length; i++) {
                    WhereObjectEntry whereObjectEntry = checkStrict(entityQueryExpressionBuilder, strictMode, propNames[i], tablesIndex[i]);
                    if (whereObjectEntry == null) {
                        continue;
                    }
                    whereObjectEntries.add(whereObjectEntry);
                }
                return whereObjectEntries;
            }
        }
        throw new EasyQueryInvalidOperationException("cant support where object mode:" + mode);

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
            if (child.getFieldName().equals(fieldName)) {
                return child;
            }
        }
        return null;
    }

    private ColumnMetadata getColumnMetadata(TableAvailable table, String property, boolean strictMode) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnOrNull(property);
        if (columnMetadata == null) {
            if (strictMode) {
                throw new EasyQueryWhereInvalidOperationException("property name:[" + property + "] not found query entity class:" + EasyClassUtil.getSimpleName(table.getEntityClass()));
            }
        }
        return columnMetadata;
    }

    private NavigateMetadata getNavigateMetadata(TableAvailable table, String property, boolean strictMode) {
        NavigateMetadata navigateMetadata = table.getEntityMetadata().getNavigateOrNull(property);
        if (navigateMetadata == null) {
            if (strictMode) {
                throw new EasyQueryWhereInvalidOperationException("property name:[" + property + "] not found query entity class:" + EasyClassUtil.getSimpleName(table.getEntityClass()));
            }
        }
        return navigateMetadata;
    }

    private void setSubQueryTreeNode(QueryPathTreeNode treeNode, TableAvailable leftTable, String property, EntityQueryExpressionBuilder entityExpressionBuilder, NavigateMetadata navigateMetadata) {
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
                treeNode.setTable(manyJoinTableExpressionBuilder.getEntityTable());
                treeNode.setAnonymousManyJoinEntityTableExpressionBuilder(anonymousManyJoinEntityTableExpressionBuilder);
                {

                    FilterImpl filter = new FilterImpl(treeNode.getEntityQueryExpressionBuilder().getRuntimeContext(), treeNode.getEntityQueryExpressionBuilder().getExpressionContext(), new AndPredicateSegment(true), false, treeNode.getEntityQueryExpressionBuilder().getExpressionContext().getValueFilter());

                    treeNode.setFilter(filter);
                }
                //构建一个filter然后用这个filter配合resolve
                return;
            }
        }

        ClientQueryable<?> implicitSubQuery = entityRelationPredicateProvider.toImplicitSubQuery(entityExpressionBuilder, leftTable, navigateMetadata, runtimeContext);

        ManyConfiguration manyConfiguration = entityExpressionBuilder.getManyConfiguration(defaultRelationTableKey);

        if (manyConfiguration != null) {
            implicitSubQuery = EasyObjectUtil.typeCastNotNull(manyConfiguration.getConfigureExpression().apply(implicitSubQuery));
        }

        treeNode.setEntityQueryExpressionBuilder(implicitSubQuery.getSQLEntityExpressionBuilder());
        treeNode.setTable(implicitSubQuery.getSQLEntityExpressionBuilder().getTable(0).getEntityTable());
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

    @Override
    public void whereObject(Object object, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        if (object == null) {
            return;
        }

        Collection<Field> allFields = EasyClassUtil.getAllFields(object.getClass());


        EntityTableExpressionBuilder tableExpressionBuilder = entityQueryExpressionBuilder.getTable(0);
        TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
        QueryPathTreeNode root = new QueryPathTreeNode(null);
        root.setTable(entityTable);
        root.setEntityQueryExpressionBuilder(entityQueryExpressionBuilder);

        {

            FilterImpl filter = new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getWhere(), false, entityQueryExpressionBuilder.getExpressionContext().getValueFilter());

            root.setFilter(filter);
        }
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
                String queryPropertyName = EasyStringUtil.isNotBlank(q.propName()) ? q.propName() : field.getName();

                String[] parts = queryPropertyName.split("\\.");
                QueryPathTreeNode current = root;

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    boolean isLast = i == (parts.length - 1);
                    QueryPathTreeNode child = findChild(current, part);
                    if (child == null) {
                        ColumnMetadata columnMetadata = isLast ? getColumnMetadata(current.getTable(), part, q.strict()) : null;
                        NavigateMetadata navigateMetadata = !isLast ? getNavigateMetadata(current.getTable(), part, q.strict()) : null;
                        child = new QueryPathTreeNode(part);
                        child.setColumnMetadata(columnMetadata);
                        child.setNavigateMetadata(navigateMetadata);
                        if (navigateMetadata != null) {
                            if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToOne || navigateMetadata.getRelationType() == RelationTypeEnum.OneToOne) {
                                child.setEntityQueryExpressionBuilder(current.getEntityQueryExpressionBuilder());
                                EntityRelationPropertyProvider entityRelationToImplicitProvider = navigateMetadata.getEntityRelationPropertyProvider();
                                if (entityRelationToImplicitProvider == null) {
                                    throw new EasyQueryInvalidOperationException("entityRelationToImplicitProvider is null,Navigate property in non entity plz set supportNonEntity = true.");
                                }
                                TableAvailable relationTable = entityRelationToImplicitProvider.toImplicitJoin(child.getEntityQueryExpressionBuilder(), current.getTable(), part);

                                child.setTable(relationTable);

                                child.setEntityQueryExpressionBuilder(current.getEntityQueryExpressionBuilder());
                                child.setFilter(current.getFilter());
                            } else {
                                //创建新的表达式查询builder
                                setSubQueryTreeNode(child, current.getTable(), part, current.getEntityQueryExpressionBuilder(), navigateMetadata);
                            }

                        } else if (columnMetadata != null) {
                            child.setTable(current.getTable());
                            child.setEntityQueryExpressionBuilder(current.getEntityQueryExpressionBuilder());
                            child.setFilter(current.getFilter());
                        }
                        current.addChild(child);
                    }
                    if (isLast) {
                        child.addCondition(q,val,field);
                    }
                    current = child;
                }
            } catch (Exception e) {
                throw new EasyQueryException(e);
            } finally {
                field.setAccessible(accessible);
            }

        }

        if (root.hasChildren()) {
            for (QueryPathTreeNode child : root.getChildren()) {

                acceptCondition(child, root);
            }
        }
    }

    private void acceptCondition(QueryPathTreeNode node, QueryPathTreeNode parent) {
        if (EasyCollectionUtil.isNotEmpty(node.getConditions())) {
            for (QueryPathTreeNode.ConditionVal condition : node.getConditions()) {
                acceptCondition0(node.getEntityQueryExpressionBuilder(),node.getFilter(), condition.condition, node.getTable(), node.getFieldName(), condition.val);
            }


        }
        for (QueryPathTreeNode child : node.getChildren()) {
            acceptCondition(child, node);
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

                    String any = getOrAppendGroupProjects(parent.getEntityQueryExpressionBuilder(), node.getAnonymousManyJoinEntityTableExpressionBuilder(), "any",sqlFunction);

                    new EasyClientQueryable<>(parent.getEntityQueryExpressionBuilder().getQueryClass(), parent.getEntityQueryExpressionBuilder())
                            .where(o -> {

                                o.getFilter().eq(node.getAnonymousManyJoinEntityTableExpressionBuilder().getEntityTable(), o.fx().nullOrDefault(any,false), true);
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


    public String getOrAppendGroupProjects(EntityExpressionBuilder entityExpressionBuilder, AnonymousManyJoinEntityTableExpressionBuilder anonymousManyJoinEntityTableExpressionBuilder, String methodName,SQLFunction sqlFunction) {

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

    private void acceptCondition0(EntityQueryExpressionBuilder entityQueryExpressionBuilder, Filter filter, EasyWhereCondition q, TableAvailable table, String fieldName, Object val) {

        List<WhereObjectEntry> queries = getQueryPropertiesOrNull(entityQueryExpressionBuilder, table, q, fieldName);
        if (EasyCollectionUtil.isEmpty(queries)) {
            return;
        }


        switch (q.type()) {
            case EQUAL: {
                if (queries.size() > 1) {
                    filter.and(x -> {
                        for (WhereObjectEntry whereObjectEntry : queries) {
                            x.eq(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                        }
                    });
                } else {
                    filter.eq(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                }
            }
            break;
            case GREATER_THAN:
            case RANGE_LEFT_OPEN: {
                if (queries.size() > 1) {
                    filter.and(x -> {
                        for (WhereObjectEntry whereObjectEntry : queries) {
                            x.gt(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                        }
                    });
                } else {
                    filter.gt(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                }
            }
            break;
            case LESS_THAN:
            case RANGE_RIGHT_OPEN: {
                if (queries.size() > 1) {
                    filter.and(x -> {
                        for (WhereObjectEntry whereObjectEntry : queries) {
                            x.lt(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                        }
                    });
                } else {
                    filter.lt(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                }
            }
            break;
            case LIKE:
            case LIKE_MATCH_LEFT:
            case LIKE_MATCH_RIGHT: {
                SQLLikeEnum sqlLike = getSQLLike(q.type());
                if (queries.size() > 1) {
                    filter.and(x -> {
                        for (WhereObjectEntry whereObjectEntry : queries) {
                            x.like(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val, sqlLike).or();
                        }
                    });
                } else {
                    filter.like(queries.get(0).getTable(), queries.get(0).getProperty(), val, sqlLike);
                }
            }
            break;
            case GREATER_THAN_EQUAL:
            case RANGE_LEFT_CLOSED: {
                if (queries.size() > 1) {
                    filter.and(x -> {
                        for (WhereObjectEntry whereObjectEntry : queries) {
                            x.ge(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                        }
                    });
                } else {
                    filter.ge(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                }
            }
            break;
            case LESS_THAN_EQUAL:
            case RANGE_RIGHT_CLOSED: {
                if (queries.size() > 1) {
                    filter.and(x -> {
                        for (WhereObjectEntry whereObjectEntry : queries) {
                            x.le(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                        }
                    });
                } else {
                    filter.le(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                }
            }
            break;
            case IN:
                if (val.getClass().isArray()) {
                    if (EasyArrayUtil.isNotEmpty((Object[]) val)) {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.in(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Object[]) val).or();
                                }
                            });
                        } else {
                            filter.in(queries.get(0).getTable(), queries.get(0).getProperty(), (Object[]) val);
                        }
                    }
                } else {
                    if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.in(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Collection<?>) val).or();
                                }
                            });
                        } else {
                            filter.in(queries.get(0).getTable(), queries.get(0).getProperty(), (Collection<?>) val);
                        }
                    }
                }
                break;
            case NOT_IN:
                if (val.getClass().isArray()) {
                    if (EasyArrayUtil.isNotEmpty((Object[]) val)) {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.notIn(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Object[]) val).or();
                                }
                            });
                        } else {
                            filter.notIn(queries.get(0).getTable(), queries.get(0).getProperty(), (Object[]) val);
                        }
                    }
                } else {
                    if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.notIn(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Collection<?>) val).or();
                                }
                            });
                        } else {
                            filter.notIn(queries.get(0).getTable(), queries.get(0).getProperty(), (Collection<?>) val);
                        }
                    }
                }
                break;
            case NOT_EQUAL: {
                if (queries.size() > 1) {
                    filter.and(x -> {
                        for (WhereObjectEntry whereObjectEntry : queries) {
                            x.ne(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                        }
                    });
                } else {
                    filter.ne(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                }
            }
            break;
            case COLLECTION_EQUAL_OR: {
                if (val.getClass().isArray()) {
                    if (EasyArrayUtil.isNotEmpty((Object[]) val)) {

                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    for (Object o : (Object[]) val) {
                                        x.eq(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), o).or();
                                    }
                                }
                            });
                        } else {
                            filter.and(f -> {
                                for (Object o : (Object[]) val) {
                                    f.eq(queries.get(0).getTable(), queries.get(0).getProperty(), o).or();
                                }
                            });
                        }
                    }
                } else {
                    if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    for (Object o : (Collection<?>) val) {
                                        x.eq(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), o).or();
                                    }
                                }
                            });
                        } else {
                            filter.and(f -> {
                                for (Object o : (Collection<?>) val) {
                                    f.eq(queries.get(0).getTable(), queries.get(0).getProperty(), o).or();
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
                MergeTuple2<SQLPredicateCompareEnum, SQLPredicateCompareEnum> sqlPredicateCompareEnum = EasyWhereCondition.Condition.getSQLPredicateCompareEnum(q.type());
                if (val.getClass().isArray()) {
                    Object[] valArray = (Object[]) val;
                    if (EasyArrayUtil.isNotEmpty(valArray)) {
                        Object[] pairArray = {valArray[0], null};
                        if (valArray.length > 1) {
                            pairArray[1] = valArray[1];
                        }

                        rangePairArray(pairArray, queries, filter, sqlPredicateCompareEnum);
                    }
                } else {
                    Collection<?> valCollection = (Collection<?>) val;
                    if (EasyCollectionUtil.isNotEmpty(valCollection)) {
                        Object[] pairArray = getPairArray(valCollection);
                        rangePairArray(pairArray, queries, filter, sqlPredicateCompareEnum);
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    //数组处理
    private void rangePairArray(Object[] pairArray, List<WhereObjectEntry> queries, Filter filter, MergeTuple2<SQLPredicateCompareEnum, SQLPredicateCompareEnum> sqlPredicateCompareEnum) {

        if (queries.size() > 1) {
            filter.and(x -> {
                for (WhereObjectEntry whereObjectEntry : queries) {
                    if (pairArray[1] != null) {
                        x.compare(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), pairArray[0], sqlPredicateCompareEnum.t1)
                                .and()
                                .compare(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), pairArray[1], sqlPredicateCompareEnum.t2);
                    } else {
                        x.compare(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), pairArray[0], sqlPredicateCompareEnum.t1);
                    }
                }
            });
        } else {
            filter.and(f -> {
                if (pairArray[1] != null) {
                    f.compare(queries.get(0).getTable(), queries.get(0).getProperty(), pairArray[0], sqlPredicateCompareEnum.t1)
                            .and()
                            .compare(queries.get(0).getTable(), queries.get(0).getProperty(), pairArray[1], sqlPredicateCompareEnum.t2);
                } else {
                    f.compare(queries.get(0).getTable(), queries.get(0).getProperty(), pairArray[0], sqlPredicateCompareEnum.t1);
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
                return SQLLikeEnum.LIKE_PERCENT_ALL;
            case LIKE_MATCH_LEFT:
                return SQLLikeEnum.LIKE_PERCENT_RIGHT;
            case LIKE_MATCH_RIGHT:
                return SQLLikeEnum.LIKE_PERCENT_LEFT;
        }
        throw new UnsupportedOperationException("where object cant get sql like");
    }
}
