package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.DirectMappingIterator;
import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ImplicitGroupRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.builder.impl.DefaultTableExpressionBuilder;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EndNavigateParams;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.Arrays;
import java.util.Map;

/**
 * create time 2024/6/7 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyRelationalUtil {
//    public static TableAvailable getRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName) {
//        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
//        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
//        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToOne && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToOne) {
//            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToOne or ManyToOne],now is " + navigateMetadata.getRelationType());
//        }
//        Class<?> navigateEntityClass = navigateMetadata.getNavigatePropertyType();
//        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new RelationTableKey(leftTable.getEntityClass(), navigateEntityClass, fullName), key -> {
//            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateEntityClass);

    /// /            TableAvailable leftTable = getTable();
//            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, entityMetadata, false);
//            boolean query = entityExpressionBuilder.isQuery();
//            EntityTableExpressionBuilder tableExpressionBuilder = new DefaultTableExpressionBuilder(rightTable, query ? MultiTableTypeEnum.LEFT_JOIN : MultiTableTypeEnum.INNER_JOIN, runtimeContext);
//            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
//
//            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
//            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);
//            sqlPredicate.and(() -> {
//                sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
//                if (navigateMetadata.hasPredicateFilterExpression()) {
//                    navigateMetadata.predicateFilterApply(sqlPredicate);
//                }
//            });
//            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
//            return tableExpressionBuilder;
//        });
//        return entityTableExpressionBuilder.getEntityTable();
//    }

    /**
     * 用来处理动态属性的多级属性问题
     * 根据属性和主表获取关联对象属性表,比如user表有name属性和address关联表然后address又有city属性
     * property为[name]等于表达式[o.name()]
     * property为[address.city]等于表达式[o.address().city()]
     * 返回表对象和最终一级的属性比如city
     *
     * @param entityExpressionBuilder
     * @param leftTable
     * @param property
     * @return
     */
    public static TableOrRelationTable getTableOrRelationalTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property) {
        return getTableOrRelationalTable(entityExpressionBuilder, leftTable, property, true);
    }

    public static TableOrRelationTable getTableOrRelationalTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, boolean strictMode) {
        if (property.contains(".")) {
            String[] properties = property.split("\\.");
            return getTableOrRelationalTable(entityExpressionBuilder, leftTable, properties, strictMode);
        } else {
            return new TableOrRelationTable(leftTable, property);
        }
    }

    public static TableOrRelationTable getTableOrRelationalTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String[] properties, boolean strictMode) {
        if (EasyArrayUtil.isEmpty(properties)) {
            throw new IllegalArgumentException("properties is empty");
        }
        if (properties.length > 1) {
            TableAvailable relationTable = leftTable;
            boolean skip = false;
            for (int i = 0; i < properties.length - 1 && !skip; i++) {
                String navigateEntityProperty = properties[i];
                relationTable = EasyRelationalUtil.getRelationTable(entityExpressionBuilder, relationTable, navigateEntityProperty, strictMode);
                if (relationTable == null) {
                    skip = true;
                }
            }

            return new TableOrRelationTable(relationTable, properties[properties.length - 1]);
        } else {
            return new TableOrRelationTable(leftTable, properties[0]);
        }
    }

    public static TableAvailable getRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property) {
        return getRelationTable(entityExpressionBuilder, leftTable, property, true);
    }

    public static TableAvailable getRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, boolean strictMode) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();

        NavigateMetadata navigateMetadata = strictMode ? leftTable.getEntityMetadata().getNavigateNotNull(property) : leftTable.getEntityMetadata().getNavigateOrNull(property);
        if (navigateMetadata == null) {
            return null;
        }
        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToOne && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToOne) {
            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToOne or ManyToOne],now is " + navigateMetadata.getRelationType());
        }
        String[] directMapping = navigateMetadata.getDirectMapping();
        if (EasyArrayUtil.isNotEmpty(directMapping)) {
            TableAvailable myLeftTable = leftTable;
            DirectMappingIterator directMappingIterator = new DirectMappingIterator(directMapping);
            while (directMappingIterator.hasNext()) {
                String prop = directMappingIterator.next();
                myLeftTable = getRelationTable(entityExpressionBuilder, myLeftTable, prop);
            }
            return myLeftTable;
        } else {

            Class<?> navigateEntityClass = navigateMetadata.getNavigatePropertyType();
            EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new DefaultRelationTableKey(leftTable, navigateMetadata.getPropertyName()), key -> {
                EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateEntityClass);
//            TableAvailable leftTable = getTable();
                RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, entityMetadata, false);

                MultiTableTypeEnum relationJoin = entityExpressionBuilder.isQuery() ? MultiTableTypeEnum.LEFT_JOIN : MultiTableTypeEnum.INNER_JOIN;
                if (relationJoin == MultiTableTypeEnum.LEFT_JOIN) {
                    //如果目标表不为null那么使用inner join和left join是一样的,如果目标表有外键那么可能会设置delete的时候set null模式那么也应该使用inner join来提高性能
                    if (navigateMetadata.isRequired()) {
                        relationJoin = MultiTableTypeEnum.INNER_JOIN;
                    }
                }
                ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
                EntityTableExpressionBuilder tableExpressionBuilder = expressionBuilderFactory.createEntityTableExpressionBuilder(rightTable, relationJoin, entityExpressionBuilder.getExpressionContext());


//                EntityTableExpressionBuilder tableExpressionBuilder = new DefaultTableExpressionBuilder(rightTable, relationJoin, entityExpressionBuilder.getExpressionContext());
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment();

                SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
                WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);
                sqlPredicate.and(() -> {
                    sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                    if (navigateMetadata.hasPredicateFilterExpression()) {
                        navigateMetadata.predicateFilterApply(sqlPredicate);
                    }
                });
                tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
                return tableExpressionBuilder;
            });
            return entityTableExpressionBuilder.getEntityTable();
        }

    }

    public static AnonymousManyJoinEntityTableExpressionBuilder getManyJoinRelationTable(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, ManyConfiguration manyConfiguration) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();

        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToMany && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToMany or ManyToMany],now is " + navigateMetadata.getRelationType());
        }
        String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(Map.class);
        ClientQueryable<?> manyQueryable = createManyQueryable(entityExpressionBuilder, navigateMetadata, targetPropertiesOrPrimary, manyConfiguration);

        String queryableKey = EasySQLUtil.toQueryableKey(manyQueryable);

        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new ImplicitGroupRelationTableKey(leftTable, navigateMetadata.getPropertyName(), queryableKey), key -> {
//            TableAvailable leftTable = getTable();

            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, entityMetadata, true);
            entityExpressionBuilder.getExpressionContext().extract(manyQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
            ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
            MultiTableTypeEnum joinType = MultiTableTypeEnum.LEFT_JOIN;
            if (navigateMetadata.isRequired()) {
                joinType = MultiTableTypeEnum.INNER_JOIN;
            }

            EntityTableExpressionBuilder tableExpressionBuilder = expressionBuilderFactory.createAnonymousManyGroupEntityTableExpressionBuilder(entityExpressionBuilder, entityExpressionBuilder.getExpressionContext(), rightTable, joinType, manyQueryable.getSQLEntityExpressionBuilder(), targetPropertiesOrPrimary);

            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();

            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);
            int groupJoinKeySize = getGroupJoinKeySize(navigateMetadata, targetPropertiesOrPrimary);
            sqlPredicate.and(() -> {
                String[] selfGroupKeys = new String[groupJoinKeySize];
                for (int i = 0; i < groupJoinKeySize; i++) {
                    selfGroupKeys[i] = KeywordTool.getGroupKeyAlias(i);
                }
                sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), selfGroupKeys, navigateMetadata.getSelfPropertiesOrPrimary());
            });
            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
            return tableExpressionBuilder;
        });
        return ((AnonymousManyJoinEntityTableExpressionBuilder) entityTableExpressionBuilder);
    }

    public static AnonymousManyJoinEntityTableExpressionBuilder getManySingleJoinRelationTable(RelationTableKey relationTableKey
            , EntityExpressionBuilder entityExpressionBuilder
            , TableAvailable leftTable
            , NavigateMetadata navigateMetadata
            , int index
            , ClientQueryable<?> clientQueryable) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();

        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToMany && navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
            if (navigateMetadata.getLimit() == 0) {
                throw new EasyQueryInvalidOperationException("navigate relation table should [OneToMany or ManyToMany],now is " + navigateMetadata.getRelationType());
            }
        }
        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(relationTableKey, key -> {
//            TableAvailable leftTable = getTable();

            EntityMetadata partitionByEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());

            String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
//            ClientQueryable<?> select = clientQueryable.where(m -> m.eq("__row__", index + 1))
//                    .select(navigateMetadata.getNavigatePropertyType(), o -> {
//                        for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : partitionByEntityMetadata.getProperty2ColumnMap().entrySet()) {
////                            o.column(columnMetadataEntry.getValue().getName());
//                            o.sqlNativeSegment("{0}", c -> {
//                                c.expression(columnMetadataEntry.getValue().getName());
//                                c.setAlias(columnMetadataEntry.getValue().getName());
//                            });
//                        }
//                        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
//                            EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
//                            for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
//                                ColumnMetadata columnMetadata = mappingEntityMetadata.getColumnNotNull(selfMappingProperty);
////                                o.columnAs(columnMetadata.getName(), columnMetadata.getName());
//                                o.sqlNativeSegment("{0}", c -> {
//                                    c.expression(columnMetadata.getName());
//                                    c.setAlias(columnMetadata.getName());
//                                });
//                            }
//                        }
//                    });

            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key, leftTable, partitionByEntityMetadata, true);
            entityExpressionBuilder.getExpressionContext().extract(clientQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
            ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
            MultiTableTypeEnum joinType = MultiTableTypeEnum.LEFT_JOIN;
            if (index == 0 && navigateMetadata.isRequired()) {
                joinType = MultiTableTypeEnum.INNER_JOIN;
            }
            EntityTableExpressionBuilder tableExpressionBuilder = expressionBuilderFactory.createAnonymousManyGroupEntityTableExpressionBuilder(entityExpressionBuilder, entityExpressionBuilder.getExpressionContext(), rightTable, joinType, clientQueryable.getSQLEntityExpressionBuilder(), targetPropertiesOrPrimary);

            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();

            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);


            if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {

                EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
                String[] selfMappingColumnNames = Arrays.stream(navigateMetadata.getSelfMappingProperties()).map(prop -> mappingEntityMetadata.getColumnNotNull(prop).getName()).toArray(String[]::new);
                sqlPredicate.and(() -> {
                    for (int i = 0; i < selfMappingColumnNames.length; i++) {
                        String selfMappingColumnName = selfMappingColumnNames[i];
                        String selfProperty = navigateMetadata.getSelfPropertiesOrPrimary()[i];
                        sqlPredicate.sqlNativeSegment("{0} = {1}", c -> {
                            c.columnName("__" + selfMappingColumnName + "__");
                            c.expression(leftTable, selfProperty);
                        });
                    }
//                    sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), selfMappingColumnNames, navigateMetadata.getSelfPropertiesOrPrimary());
                });
                sqlPredicate.eqColumn(true, "__row__", index + 1);
            } else {
                sqlPredicate.and(() -> {
                    sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), targetPropertiesOrPrimary, navigateMetadata.getSelfPropertiesOrPrimary());
                });
                sqlPredicate.eqColumn(true, "__row__", index + 1);
            }
//            sqlPredicate.and(() -> {
//                sqlPredicate.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), targetPropertiesOrPrimary, navigateMetadata.getSelfPropertiesOrPrimary());
//            });
            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
            return tableExpressionBuilder;
        });
        return ((AnonymousManyJoinEntityTableExpressionBuilder) entityTableExpressionBuilder);
    }


    private static ClientQueryable<?> createManyQueryable(EntityExpressionBuilder entityExpressionBuilder, NavigateMetadata navigateMetadata, String[] targetPropertiesOrPrimary, ManyConfiguration manyConfiguration) {

        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        //todo 如果启用了全局filterConfigure传递
        ClientQueryable<?> clientQueryable = runtimeContext.getSQLClientApiFactory().createSubQueryable(navigateMetadata.getNavigatePropertyType(), runtimeContext, entityExpressionBuilder.getExpressionContext());

        Class<?> mainClass = clientQueryable.queryClass();
        SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> configureExpression = manyConfiguration.getConfigureExpression();
        clientQueryable = configureExpression.apply(clientQueryable);

        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = clientQueryable.getSQLEntityExpressionBuilder();
        EntityMetadata mainEntityMetadata = sqlEntityExpressionBuilder.getFromTable().getEntityMetadata();
        boolean hasLimit = sqlEntityExpressionBuilder.hasLimit();
        boolean many2manyHasMiddleClass = navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null;
        long offset = sqlEntityExpressionBuilder.getOffset();
        long rows = sqlEntityExpressionBuilder.getRows();
        long rowNumberLeft = offset + 1;
        long rowNumberRight = offset + rows;
        if (hasLimit) {
            sqlEntityExpressionBuilder.setOffset(0);
            sqlEntityExpressionBuilder.setRows(0);

        }
        int groupJoinKeySize = getGroupJoinKeySize(navigateMetadata, targetPropertiesOrPrimary);
        if (many2manyHasMiddleClass) {
            if (hasLimit) {

                OrderBySQLBuilderSegment orderBySQLBuilderSegment = EasySQLExpressionUtil.appendPartitionByOrderSegment(clientQueryable, new EndNavigateParams(navigateMetadata, null));
                clientQueryable = clientQueryable.innerJoin(navigateMetadata.getMappingClass(), (target, middle) -> {
                            target.multiEq(true, middle, targetPropertiesOrPrimary, navigateMetadata.getTargetMappingProperties());
                        })
                        .where((target, middle) -> {
                            navigateMetadata.predicateMappingClassFilterApply(middle);
                            navigateMetadata.predicateFilterApply(target);
                        }).select(Map.class, (target, middle) -> {
                            for (ColumnMetadata column : target.getEntityMetadata().getColumns()) {
                                target.column(column.getPropertyName());
                            }
                            String[] selfMappingProperties = navigateMetadata.getSelfMappingProperties();
                            for (int i = 0; i < groupJoinKeySize; i++) {
                                String selfMappingProperty = selfMappingProperties[i];
                                middle.columnAs(selfMappingProperty, KeywordTool.getGroupKeyAlias(i));

                            }

                            PartitionBySQLFunction partitionBySQLFunction = getPartitionBySQLFunction(runtimeContext, navigateMetadata, targetPropertiesOrPrimary, middle.getTable(), orderBySQLBuilderSegment);


                            target.sqlFuncAs(partitionBySQLFunction, "__row__");

                        }).where(m -> m.ge("__row__", rowNumberLeft).le("__row__", rowNumberRight))
                        .select(mainClass, o -> {
                            for (ColumnMetadata column : mainEntityMetadata.getColumns()) {
                                o.column(column.getName());
                            }
                            for (int i = 0; i < groupJoinKeySize; i++) {
                                o.column(KeywordTool.getGroupKeyAlias(i));
                            }
                        });

                clientQueryable.
                        groupBy(m -> {
                            for (int i = 0; i < groupJoinKeySize; i++) {
                                String groupKeyAlias = KeywordTool.getGroupKeyAlias(i);
                                m.sqlNativeSegment("{0}", c -> {
                                    c.columnName(m.getTable(), groupKeyAlias);
                                });
                            }
                        }).select(Map.class, m -> {
                            for (int i = 0; i < groupJoinKeySize; i++) {
                                String groupKeyAlias = KeywordTool.getGroupKeyAlias(i);
                                m.sqlNativeSegment("{0}", c -> {
                                    c.columnName(m.getTable(), groupKeyAlias);
                                });
                            }
                        });
                return clientQueryable;
            } else {

                clientQueryable.innerJoin(navigateMetadata.getMappingClass(), (target, middle) -> {
                            target.multiEq(true, middle, targetPropertiesOrPrimary, navigateMetadata.getTargetMappingProperties());
                        })
                        .where((target, middle) -> {
                            navigateMetadata.predicateMappingClassFilterApply(middle);
                            navigateMetadata.predicateFilterApply(target);
                        }).groupBy((target, middle) -> {
                            for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                                middle.column(selfMappingProperty);
                            }
                        }).select(Map.class, (target, middle) -> {
                            String[] selfMappingProperties = navigateMetadata.getSelfMappingProperties();
                            for (int i = 0; i < groupJoinKeySize; i++) {
                                String selfMappingProperty = selfMappingProperties[i];
                                middle.columnAs(selfMappingProperty, KeywordTool.getGroupKeyAlias(i));
                            }
//                            for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
//                                ColumnMetadata columnMetadata = middleEntityMetadata.getColumnNotNull(selfMappingProperty);
//                                middle.columnAs(selfMappingProperty, columnMetadata.getName());
//                            }
                        });
                return clientQueryable;
            }
        } else {
            if (hasLimit) {
                OrderBySQLBuilderSegment orderBySQLBuilderSegment = EasySQLExpressionUtil.appendPartitionByOrderSegment(clientQueryable, new EndNavigateParams(navigateMetadata, null));
                clientQueryable = clientQueryable.select(Map.class, o -> {
                            for (ColumnMetadata column : o.getEntityMetadata().getColumns()) {
                                o.columnAs(column.getPropertyName(), column.getName());
                            }

                            PartitionBySQLFunction partitionBySQLFunction = getPartitionBySQLFunction(runtimeContext, navigateMetadata, targetPropertiesOrPrimary, o.getTable(), orderBySQLBuilderSegment);

                            o.sqlFuncAs(partitionBySQLFunction, "__row__");
                        }).where(m -> m.ge("__row__", rowNumberLeft).le("__row__", rowNumberRight))
                        .select(mainClass);
            }

            return clientQueryable.where(t -> {
                navigateMetadata.predicateFilterApply(t);
            }).groupBy(o -> {
                for (String column : targetPropertiesOrPrimary) {
                    o.column(column);
                }
            }).select(o -> {
                for (int i = 0; i < groupJoinKeySize; i++) {
                    String column = targetPropertiesOrPrimary[i];
                    o.columnFixedAs(column, KeywordTool.getGroupKeyAlias(i));
                }
            });
        }
    }

    public static int getGroupJoinKeySize(NavigateMetadata navigateMetadata, String[] targetPropertiesOrPrimary) {
        int columnCount = 0;
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            columnCount = navigateMetadata.getSelfMappingProperties().length;
        } else {
            columnCount = targetPropertiesOrPrimary.length;
        }
        return columnCount;
    }

    private static PartitionBySQLFunction getPartitionBySQLFunction(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertiesOrPrimary, TableAvailable entityTable, OrderBySQLBuilderSegment orderBySQLBuilderSegment) {
        PartitionBySQLFunction partitionBySQLFunction = runtimeContext.fx().rowNumberOver(s -> {
            if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {

                for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                    s.column(entityTable, selfMappingProperty);
                }
            } else {
                for (String column : targetPropertiesOrPrimary) {
                    s.column(column);
                }
            }
        });

        if (EasySQLSegmentUtil.isNotEmpty(orderBySQLBuilderSegment)) {
            partitionBySQLFunction.addOrder(orderBySQLBuilderSegment);
        }
        return partitionBySQLFunction;
    }

    public static class TableOrRelationTable {
        public final TableAvailable table;
        public final String property;

        public TableOrRelationTable(TableAvailable table, String property) {
            this.table = table;
            this.property = property;
        }
    }
}
