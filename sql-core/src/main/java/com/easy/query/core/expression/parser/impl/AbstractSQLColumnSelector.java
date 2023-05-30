package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.SQLAnonymousUnionEntityQueryExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.EasyLambdaUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 */
public class AbstractSQLColumnSelector<T1, TChain> {
    protected final int index;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlSegmentBuilder;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public AbstractSQLColumnSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder) {
        this.index = index;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.table = entityQueryExpressionBuilder.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }


    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public TChain column(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), null);
        sqlSegmentBuilder.append(columnSegment);
        return (TChain) this;
    }


    public TChain columnIgnore(Property<T1, ?> column) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = EasyLambdaUtil.getPropertyName(column);
        sqlSegmentBuilder.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table.getEntityTable()) && Objects.equals(sqlEntitySegment.getPropertyName(), propertyName);
            }
            return false;
        });
        return (TChain) this;
    }


    public TChain columnAll() {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            boolean queryLargeColumn = entityQueryExpressionBuilder.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<String> properties = entityMetadata.getProperties();
            for (String property : properties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
                if (!columnAllQueryLargeColumn(queryLargeColumn, columnMetadata)) {
                    continue;
                }
                ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(),null);
                sqlSegmentBuilder.append(columnSegment);
            }
        }
        return (TChain) this;
    }

    private EntityQueryExpressionBuilder getAnonymousTableQueryExpressionBuilder(AnonymousEntityTableExpressionBuilder table) {
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = table.getEntityQueryExpressionBuilder();
        return getEntityQueryExpressionBuilder(entityQueryExpressionBuilder);
    }

    private EntityQueryExpressionBuilder getEntityQueryExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        if (entityQueryExpressionBuilder instanceof SQLAnonymousUnionEntityQueryExpressionBuilder) {
            List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders = ((SQLAnonymousUnionEntityQueryExpressionBuilder) entityQueryExpressionBuilder).getEntityQueryExpressionBuilders();
            EntityQueryExpressionBuilder first = EasyCollectionUtil.first(entityQueryExpressionBuilders);
            return getEntityQueryExpressionBuilder(first);
        } else if (EasySQLSegmentUtil.isEmpty(entityQueryExpressionBuilder.getProjects())) {
            if (EasyCollectionUtil.isSingle(entityQueryExpressionBuilder.getTables())) {
                EntityTableExpressionBuilder entityQueryExpressionBuilderTable = entityQueryExpressionBuilder.getTable(0);
                if (entityQueryExpressionBuilderTable instanceof AnonymousEntityTableExpressionBuilder) {
                    return getAnonymousTableQueryExpressionBuilder((AnonymousEntityTableExpressionBuilder) entityQueryExpressionBuilderTable);
                }
            }
        }
        return entityQueryExpressionBuilder;
    }

    protected TChain columnAnonymousAll(AnonymousEntityTableExpressionBuilder table) {
        EntityQueryExpressionBuilder queryExpressionBuilder = getAnonymousTableQueryExpressionBuilder(table);
        if (EasySQLSegmentUtil.isNotEmpty(queryExpressionBuilder.getProjects())) {

            List<SQLSegment> sqlSegments = queryExpressionBuilder.getProjects().getSQLSegments();
            //匿名表内部设定的不查询
//            boolean queryLargeColumn = queryExpressionBuilder.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
//            EntityMetadata entityMetadata = table.getEntityMetadata();
            for (SQLSegment sqlSegment : sqlSegments) {
//                if (sqlSegment instanceof SQLEntitySegment) {
//                    SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
//                    ColumnMetadata columnMetadata = sqlEntitySegment.getTable().getEntityMetadata().getColumnNotNull(sqlEntitySegment.getPropertyName());
//                    if (!columnAllQueryLargeColumn(queryLargeColumn, columnMetadata)) {
//                        continue;
//                    }
//                }

                if (sqlSegment instanceof SQLEntityAliasSegment) {
                    SQLEntityAliasSegment sqlEntityAliasSegment = (SQLEntityAliasSegment) sqlSegment;

                    String propertyName = EasyUtil.getAnonymousPropertyName(sqlEntityAliasSegment, table.getEntityTable());
                    ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), sqlEntityAliasSegment.getAlias());
                    sqlSegmentBuilder.append(columnSegment);
                } else {
                    throw new EasyQueryException("columnAll函数无法获取指定列" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                }
            }
        }
        return (TChain) this;
    }


    public EntityExpressionBuilder getEntityQueryExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }


    protected boolean columnAllQueryLargeColumn(boolean queryLargeColumn, ColumnMetadata columnMetadata) {
        //如果不查询的情况下当列是非大列才可以查询

        if (!queryLargeColumn) {
            return !columnMetadata.isLarge();
        }
        return true;
    }
}
