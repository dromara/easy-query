package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.common.reverse.ChainReverseEach;
import com.easy.query.core.common.reverse.DefaultReverseEach;
import com.easy.query.core.common.reverse.EmptyReverseEach;
import com.easy.query.core.common.reverse.ReverseEach;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SelectConstSegment;
import com.easy.query.core.expression.segment.builder.GroupBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.visitor.EmptyTableVisitor;
import com.easy.query.core.expression.visitor.ExpressionTableVisitor;
import com.easy.query.core.expression.sql.builder.SQLEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.sort.DefaultTableSortProcessor;
import com.easy.query.core.expression.sql.builder.sort.TableSortProcessor;
import com.easy.query.core.expression.sql.builder.sort.TableSortWithRelationProcessor;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.SQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/3/3 22:10
 */
public class QueryExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityQueryExpressionBuilder {

    protected PredicateSegment where;
    protected SQLBuilderSegment group;
    protected PredicateSegment having;
    protected OrderBySQLBuilderSegment order;
    protected long offset;
    protected long rows;
    protected boolean distinct;

    protected final SQLBuilderSegment projects;

    public QueryExpressionBuilder(ExpressionContext expressionContext, Class<?> queryClass) {
        super(expressionContext, queryClass);
        this.projects = new ProjectSQLBuilderSegmentImpl();
    }


    @Override
    public boolean isEmpty() {
        return tables.isEmpty();
    }

    @Override
    public SQLBuilderSegment getProjects() {
        return projects;
    }

    @Override
    public PredicateSegment getWhere() {
        if (where == null) {
            where = new AndPredicateSegment(true);
        }
        return where;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public long getRows() {
        return rows;
    }

    @Override
    public void setRows(long rows) {
        this.rows = rows;
    }

    @Override
    public boolean hasLimit() {
        return this.rows > 0;
    }

    @Override
    public boolean hasWhere() {
        return where != null && where.isNotEmpty();
    }

    @Override
    public PredicateSegment getHaving() {
        if (having == null) {
            having = new AndPredicateSegment(true);
        }
        return having;
    }

    @Override
    public boolean hasHaving() {
        return EasySQLSegmentUtil.isNotEmpty(having);
    }

    @Override
    public SQLBuilderSegment getGroup() {
        if (group == null) {
            group = new GroupBySQLBuilderSegmentImpl();
        }
        return group;
    }

    @Override
    public boolean hasGroup() {
        return group != null && group.isNotEmpty();
    }

    @Override
    public OrderBySQLBuilderSegment getOrder() {
        if (order == null) {
            order = new OrderBySQLBuilderSegmentImpl();
        }
        return order;
    }

    @Override
    public boolean hasOrder() {
        return order != null && order.isNotEmpty();
    }


    @Override
    public boolean isDistinct() {
        return distinct;
    }

    @Override
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    @Override
    public boolean isQuery() {
        return true;
    }

    @Override
    public EntityQuerySQLExpression toExpression() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        boolean emptySelect = getProjects().isEmpty();
        Iterator<EntityTableExpressionBuilder> iterator = getTables().iterator();
        EntityTableExpressionBuilder firstTable = iterator.next();
        //如果有order需要将order移到内部表达式
        if (emptySelect && tableCount == 1 && (firstTable instanceof AnonymousEntityTableExpressionBuilder && !(((AnonymousEntityTableExpressionBuilder) firstTable).getEntityQueryExpressionBuilder() instanceof SQLEntityQueryExpressionBuilder))) {
            return (EntityQuerySQLExpression) toTableExpressionSQL(firstTable, true);
        }
        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        SQLSegmentFactory sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityQuerySQLExpression easyQuerySQLExpression = expressionFactory.createEasyQuerySQLExpression(entitySQLExpressionMetadata);
        easyQuerySQLExpression.setDistinct(isDistinct());

        if (emptySelect) {
            if (!hasGroup()) {
                ProjectSQLBuilderSegmentImpl projects = new ProjectSQLBuilderSegmentImpl();
                SelectConstSegment selectAllSegment = sqlSegmentFactory.createSelectConstSegment("*");
                projects.append(selectAllSegment);
                easyQuerySQLExpression.setProjects(projects);
            } else {
                ProjectSQLBuilderSegmentImpl projects = new ProjectSQLBuilderSegmentImpl();
                getGroup().copyTo(projects);
                easyQuerySQLExpression.setProjects(projects);
            }
        } else {
            easyQuerySQLExpression.setProjects(getProjects().cloneSQLBuilder());
        }
        boolean hasRelationTables = hasRelationTables();
        TableVisitor expressionTableVisitor = hasRelationTables ? new ExpressionTableVisitor() : EmptyTableVisitor.DEFAULT;
        EntityTableSQLExpression firstTableExpression = (EntityTableSQLExpression) toTableExpressionSQL(firstTable, false);
        TableSortProcessor tableSortProcessor = hasRelationTables ? new TableSortWithRelationProcessor(firstTableExpression, expressionTableVisitor) : new DefaultTableSortProcessor();

        tableSortProcessor.appendTable(firstTableExpression);
        while (iterator.hasNext()) {
            EntityTableExpressionBuilder table = iterator.next();
            EntityTableSQLExpression tableExpression = (EntityTableSQLExpression) toTableExpressionSQL(table, false);

            PredicateSegment on = getTableOnWithQueryFilter(table);
            if (on != null && on.isNotEmpty()) {
                tableExpression.setOn(on);
            }
            tableSortProcessor.appendTable(tableExpression);
        }
//
//        if (hasRelationTables()) {
//            for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> relationTableKV : getRelationTables().entrySet()) {
//                RelationTableKey key = relationTableKV.getKey();
//                EntityTableExpressionBuilder value = relationTableKV.getValue();
//                EntityTableSQLExpression tableExpression = (EntityTableSQLExpression) toTableExpressionSQL(value, false);
//                easyQuerySQLExpression.getTables().add(tableExpression);
//                PredicateSegment on = getTableOnWithQueryFilter(value);
//                if (on != null && on.isNotEmpty()) {
//                    tableExpression.setOn(on);
//                }
//            }
//        }


        PredicateSegment where = getSQLWhereWithQueryFilter();
        if (where != null && where.isNotEmpty()) {
            easyQuerySQLExpression.setWhere(where);
        }
        easyQuerySQLExpression.setGroup(getGroup().cloneSQLBuilder());
        easyQuerySQLExpression.setHaving(getHaving().clonePredicateSegment());
        easyQuerySQLExpression.setOrder(getOrder().cloneSQLBuilder());
        easyQuerySQLExpression.setOffset(getOffset());
        easyQuerySQLExpression.setRows(getRows());

        if (hasRelationTables()) {
            accept(expressionTableVisitor);


            ArrayList<EntityTableSQLExpression> tableSQLExpressions = new ArrayList<>();
            ReverseEach reverseEach = EmptyReverseEach.EMPTY;
            for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> relationTableKV : getRelationTables().entrySet()) {
                RelationTableKey key = relationTableKV.getKey();
                EntityTableExpressionBuilder value = relationTableKV.getValue();
                TableAvailable entityTable = value.getEntityTable();
                //判断where order group having select是否使用了relationTable
                reverseEach = new ChainReverseEach(reverseEach, new DefaultReverseEach(() -> {
                    if (expressionTableVisitor.containsTable(entityTable)) {
                        EntityTableSQLExpression tableExpression = (EntityTableSQLExpression) toTableExpressionSQL(value, false);
                        PredicateSegment on = getTableOnWithQueryFilter(value);
                        if (on != null && on.isNotEmpty()) {
                            tableExpression.setOn(on);
                        }
                        EasySQLSegmentUtil.tableVisit(on, expressionTableVisitor);
                        tableSQLExpressions.add(tableExpression);
                    }
                }));

            }
            reverseEach.invoke();
            if (EasyCollectionUtil.isNotEmpty(tableSQLExpressions)) {
                for (int i = tableSQLExpressions.size()-1; i >=0; i--) {
                    EntityTableSQLExpression tableExpression = tableSQLExpressions.get(i);
                    tableSortProcessor.appendTable(tableExpression);
                }
            }

        }
        easyQuerySQLExpression.getTables().addAll(tableSortProcessor.getTables());

//        if(hasIncludes()){
//            List<EntityQueryExpressionBuilder> includeList = getIncludes();
//            ArrayList<EntityQuerySQLExpression> includeExpressions = new ArrayList<>(includeList.size());
//            for (EntityQueryExpressionBuilder entityQueryExpressionBuilder : includeList) {
//                EntityQuerySQLExpression entityQuerySQLExpression = entityQueryExpressionBuilder.toExpression();
//                includeExpressions.add(entityQuerySQLExpression);
//            }
//            easyQuerySQLExpression.setIncludes(includeExpressions);
//        }
        return easyQuerySQLExpression;
    }

    protected SQLExpression toTableExpressionSQL(EntityTableExpressionBuilder entityTableExpressionBuilder, boolean onlySingleAnonymousTable) {
        if (entityTableExpressionBuilder instanceof AnonymousEntityTableExpressionBuilder) {

            EntityQueryExpressionBuilder sqlEntityQueryExpression = ((AnonymousEntityTableExpressionBuilder) entityTableExpressionBuilder).getEntityQueryExpressionBuilder();
            //如果只有单匿名表且未对齐select那么嵌套表需要被展开
            //todo 如果对其进行order 或者 where了呢怎么办
            return onlySingleAnonymousTable ? sqlEntityQueryExpression.toExpression() : entityTableExpressionBuilder.toExpression();
        }
        return entityTableExpressionBuilder.toExpression();
    }

    protected PredicateSegment getTableOnWithQueryFilter(EntityTableExpressionBuilder table) {
        return sqlPredicateFilter(table, table.hasOn() ? table.getOn() : null);
    }

    @Override
    public PredicateSegment getSQLWhereWithQueryFilter() {
        EntityTableExpressionBuilder table = getTable(0);
        return sqlPredicateFilter(table, hasWhere() ? getWhere() : null);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {

        EntityQueryExpressionBuilder queryExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityQueryExpressionBuilder(expressionContext.cloneExpressionContext(), queryClass);
        if (hasWhere()) {
            getWhere().copyTo(queryExpressionBuilder.getWhere());
        }
        if (hasGroup()) {
            getGroup().copyTo(queryExpressionBuilder.getGroup());
        }
        if (hasHaving()) {
            getHaving().copyTo(queryExpressionBuilder.getHaving());
        }
        if (hasOrder()) {
            getOrder().copyTo(queryExpressionBuilder.getOrder());
        }
        getProjects().copyTo(queryExpressionBuilder.getProjects());
        queryExpressionBuilder.setOffset(this.offset);
        queryExpressionBuilder.setRows(this.rows);
        queryExpressionBuilder.setDistinct(this.distinct);
        for (EntityTableExpressionBuilder table : super.tables) {
            queryExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        if (super.relationTables != null) {
            for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> relationTableKV : super.relationTables.entrySet()) {
                queryExpressionBuilder.getRelationTables().put(relationTableKV.getKey(), relationTableKV.getValue().copyEntityTableExpressionBuilder());
            }
        }
        if(super.manyConfigurationMaps!=null){
            for (Map.Entry<RelationTableKey, ManyConfiguration> manyJoinConfigurationEntry : super.manyConfigurationMaps.entrySet()) {
                queryExpressionBuilder.putManyConfiguration(manyJoinConfigurationEntry.getKey(),manyJoinConfigurationEntry.getValue());
            }
        }
        if(super.manyJoinConfigurationMaps !=null){
            for (Map.Entry<RelationTableKey, SubQueryModeEnum> subQueryModeKv : super.manyJoinConfigurationMaps.entrySet()) {

                queryExpressionBuilder.putSubQueryToGroupJoinJoin(subQueryModeKv.getKey(),subQueryModeKv.getValue());
            }
        }
        return queryExpressionBuilder;
    }

    @Override
    public void accept(TableVisitor visitor) {
        for (EntityTableExpressionBuilder table : getTables()) {
            visitor.visit(table.getEntityTable());
            if (table instanceof AnonymousEntityTableExpressionBuilder) {
                EntityQueryExpressionBuilder entityQueryExpressionBuilder = ((AnonymousEntityTableExpressionBuilder) table).getEntityQueryExpressionBuilder();
                entityQueryExpressionBuilder.accept(visitor);
            } else {
                EasySQLSegmentUtil.tableVisit(table.getOn(), visitor);
            }
        }
        EasySQLSegmentUtil.tableVisit(projects, visitor);
        EasySQLSegmentUtil.tableVisit(where, visitor);
        EasySQLSegmentUtil.tableVisit(order, visitor);
        EasySQLSegmentUtil.tableVisit(group, visitor);
        EasySQLSegmentUtil.tableVisit(having, visitor);
    }
}
