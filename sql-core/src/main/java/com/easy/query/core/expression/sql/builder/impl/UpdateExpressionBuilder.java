package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnVersionPropertyPredicate;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateIndex;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.basic.plugin.interceptor.EasyUpdateSetInterceptor;
import com.easy.query.core.basic.plugin.track.EntityState;
import com.easy.query.core.basic.plugin.track.TrackContext;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.*;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/4 17:05
 */
public class UpdateExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityUpdateExpressionBuilder {

    protected final boolean isExpressionUpdate;
    private SQLBuilderSegment setColumns;
    private PredicateSegment where;
    private SQLBuilderSegment setIgnoreColumns;
    private SQLBuilderSegment whereColumns;

    public UpdateExpressionBuilder(ExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext);
        this.isExpressionUpdate = isExpressionUpdate;
    }

    @Override
    public boolean isExpression() {
        return isExpressionUpdate;
    }

    @Override
    public SQLBuilderSegment getSetColumns() {
        if (setColumns == null) {
            setColumns = new UpdateSetSQLBuilderSegment();
        }
        return setColumns;
    }

    @Override
    public boolean hasSetColumns() {
        return setColumns != null && setColumns.isNotEmpty();
    }

    @Override
    public boolean hasWhere() {
        return where != null && where.isNotEmpty();
    }

    @Override
    public PredicateSegment getWhere() {
        if (where == null) {
            where = new AndPredicateSegment(true);
        }
        return where;
    }

    @Override
    public SQLBuilderSegment getSetIgnoreColumns() {
        if (setIgnoreColumns == null) {
            setIgnoreColumns = new UpdateSetSQLBuilderSegment();
        }
        return setIgnoreColumns;
    }

    @Override
    public boolean hasSetIgnoreColumns() {
        return setIgnoreColumns != null && setIgnoreColumns.isNotEmpty();
    }


    @Override
    public SQLBuilderSegment getWhereColumns() {
        if (whereColumns == null) {
            whereColumns = new ProjectSQLBuilderSegmentImpl();
        }
        return whereColumns;
    }

    @Override
    public boolean hasWhereColumns() {
        return whereColumns != null && whereColumns.isNotEmpty();
    }

    private void checkTable() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
    }


    protected SQLBuilderSegment buildSetSQLSegment(EntityTableExpressionBuilder table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        SQLBuilderSegment updateSet = getSetColumns().cloneSQLBuilder();
        SQLColumnSetter<Object> sqlColumnSetter = getRuntimeContext().getSQLExpressionInvokeFactory().createSQLColumnSetter(0, this, updateSet);

        //如果更新拦截器不为空
        if (EasyCollectionUtil.isNotEmpty(entityMetadata.getUpdateSetInterceptors())) {
            QueryConfiguration easyQueryConfiguration = getRuntimeContext().getQueryConfiguration();
            getExpressionContext().getInterceptorFilter(entityMetadata.getUpdateSetInterceptors())
                    .forEach(interceptor -> {
                        EasyUpdateSetInterceptor globalInterceptor = (EasyUpdateSetInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor.getName());
                        globalInterceptor.configure(entityMetadata.getEntityClass(), this, sqlColumnSetter);
                    });
        }
        if (entityMetadata.hasVersionColumn()) {
            Object version = expressionContext.getVersion();
            if (Objects.nonNull(version)) {
                VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                String propertyName = versionMetadata.getPropertyName();
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                FastBean fastBean = EasyBeanUtil.getFastBean(table.getEntityClass());
                EasyVersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
                Object newVersionValue = easyVersionStrategy.nextVersion(entityMetadata, propertyName, version);
                sqlColumnSetter.set(fastBean.getBeanGetter(columnMetadata.getProperty()), newVersionValue);
            }
        }
        return updateSet;
    }

    protected PredicateSegment buildWherePredicateSegment(PredicateSegment where, EntityTableExpressionBuilder table) {
        if (where.isEmpty()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        return sqlPredicateFilter(table, where);
    }

    protected void buildAutoEntitySetColumns(EntityTableExpressionBuilder table, SQLBuilderSegment updateSet, PredicateSegment sqlWhere, Object entity) {

        //如果用户没有指定set的列,那么就是set所有列,并且要去掉主键部分
        if (!hasSetColumns()) {
            EntityMetadata entityMetadata = table.getEntityMetadata();
            QueryRuntimeContext runtimeContext = getRuntimeContext();
            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
            SQLColumnSelector<?> sqlColumnSetter = easyQueryLambdaFactory.createSQLColumnSetSelector(table.getIndex(), this, updateSet);
            sqlColumnSetter.columnAll();
            clearUpdateSet(sqlWhere, runtimeContext, entityMetadata, entity, updateSet);

            if (entityMetadata.hasVersionColumn()) {
                VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                EasyVersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
                updateSet.append(new ColumnVersionPropertyPredicate(table.getEntityTable(), versionMetadata.getPropertyName(), easyVersionStrategy, this.getRuntimeContext()));
            }

        }
    }

    private void clearUpdateSet(PredicateSegment sqlWhere, QueryRuntimeContext runtimeContext, EntityMetadata entityMetadata, Object entity, SQLBuilderSegment updateSet) {

        Class<?> entityClass = entityMetadata.getEntityClass();
        //非手动指定的那么需要移除where的那一部分
        PredicateIndex predicateIndex = sqlWhere.buildPredicateIndex();
        Set<String> ignorePropertySet = new HashSet<>(entityMetadata.getProperties().size());
        //移除属性包含主键
        boolean clearIgnoreProperties = clearIgnoreProperties(ignorePropertySet, runtimeContext, entity, entityMetadata);

        updateSet.getSQLSegments().removeIf(o -> {

            String propertyName = ((SQLEntitySegment) o).getPropertyName();
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            if (columnMetadata.isUpdateIgnore() || columnMetadata.isVersion()) {
                return true;
            }
            if (clearIgnoreProperties) {
                return ignorePropertySet.contains(propertyName);
            }
            if (predicateIndex.contains(entityClass, propertyName)) {
                return true;
            }
            return false;
        });
    }

    private boolean clearIgnoreProperties(Set<String> ignorePropertySet, QueryRuntimeContext runtimeContext, Object entity, EntityMetadata entityMetadata) {

        if (entity != null) {
            TrackManager trackManager = runtimeContext.getTrackManager();
            //以下应该二选一
            //优先级是用户设置、追踪、默认配置
            SQLExecuteStrategyEnum updateStrategy = expressionContext.getSQLStrategy();
            if (!Objects.equals(SQLExecuteStrategyEnum.DEFAULT, updateStrategy)) {
                getCustomIgnoreProperties(ignorePropertySet, updateStrategy, runtimeContext.getEntityMetadataManager(), entity, entityMetadata);
                return true;
            } else {
                //todo 判断是否追踪
                if (trackManager.currentThreadTracking()) {
                    TrackContext trackContext = trackManager.getCurrentTrackContext();
                    //如果当前对象是追踪的并且没有指定更新策略
                    EntityState trackEntityState = trackContext.getTrackEntityState(entity);
                    if (trackEntityState != null) {
                        Set<String> ignoreProperties = EasyTrackUtil.getTrackIgnoreProperties(runtimeContext.getEntityMetadataManager(), trackEntityState);
                        ignorePropertySet.addAll(ignoreProperties);
                        return true;
                    }
                }
                SQLExecuteStrategyEnum globalUpdateStrategy = runtimeContext.getQueryConfiguration().getEasyQueryOption().getUpdateStrategy();
                getCustomIgnoreProperties(ignorePropertySet, globalUpdateStrategy, runtimeContext.getEntityMetadataManager(), entity, entityMetadata);
                return true;
            }
        }
        return false;
    }

    private void getCustomIgnoreProperties(Set<String> ignoreUpdateSet, SQLExecuteStrategyEnum updateStrategy, EntityMetadataManager entityMetadataManager, Object entity, EntityMetadata entityMetadata) {

        if (Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) || Objects.equals(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS, updateStrategy)) {
            Set<String> beanMatchProperties = EasyBeanUtil.getBeanMatchProperties(entityMetadataManager, entity, Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) ? Objects::isNull : Objects::nonNull);
            ignoreUpdateSet.addAll(beanMatchProperties);
        }
        ignoreUpdateSet.addAll(entityMetadata.getKeyProperties());
    }

    /**
     * 构建where条件
     *
     * @param where
     * @param table
     */
    private void buildEntityPredicateSegment(PredicateSegment where, EntityTableExpressionBuilder table, Object entity) {
        if (hasWhereColumns()) {
            buildPredicateWithWhereColumns(where, getWhereColumns(), table, entity);
        } else {
            buildPredicateWithKeyColumns(where, table, entity);
        }
    }

    /**
     * 根据指定where column构建where
     *
     * @param where
     * @param table
     */
    private void buildPredicateWithWhereColumns(PredicateSegment where, SQLBuilderSegment whereColumns, EntityTableExpressionBuilder table, Object entity) {

        if (whereColumns.isNotEmpty()) {
            TrackManager trackManager = expressionContext.getRuntimeContext().getTrackManager();
            TrackContext trackContext = trackManager.getCurrentTrackContext();
            for (SQLSegment sqlSegment : whereColumns.getSQLSegments()) {
                if (!(sqlSegment instanceof SQLEntitySegment)) {
                    throw new EasyQueryException("where 表达式片段不是SQLEntitySegment");
                }

                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                if (entity != null) {
                    Object predicateValue = getPredicateValue(entity, trackContext, sqlEntitySegment.getPropertyName(), table.getEntityMetadata());
                    ColumnValuePredicate columnValuePredicate = new ColumnValuePredicate(table.getEntityTable(), sqlEntitySegment.getPropertyName(), predicateValue, SQLPredicateCompareEnum.EQ, this.getRuntimeContext());
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnValuePredicate);
                    where.addPredicateSegment(andPredicateSegment);
                } else {
                    ColumnPropertyPredicate columnPropertyPredicate = new ColumnPropertyPredicate(table.getEntityTable(), sqlEntitySegment.getPropertyName(), this.getRuntimeContext());
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPropertyPredicate);
                    where.addPredicateSegment(andPredicateSegment);
                }
            }
        }
    }

    private Object getPredicateValue(Object entity, TrackContext trackContext, String propertyName, EntityMetadata entityMetadata) {
        FastBean fastBean = EasyBeanUtil.getFastBean(entityMetadata.getEntityClass());
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
        Property<Object, ?> beanGetter = fastBean.getBeanGetter(columnMetadata.getProperty());
        if (trackContext != null) {
            EntityState trackEntityState = trackContext.getTrackEntityState(entity);
            if (trackEntityState != null) {
                Object originalEntity = trackEntityState.getOriginalValue();
                if (originalEntity != null) {
                    return beanGetter.apply(originalEntity);
                }
            }
        }
        return beanGetter.apply(entity);
    }


    private void buildPredicateWithKeyColumns(PredicateSegment where, EntityTableExpressionBuilder table, Object entity) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if (keyProperties.isEmpty()) {
            throw new EasyQueryException("entity:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " not found primary key properties");
        }
        TrackManager trackManager = expressionContext.getRuntimeContext().getTrackManager();
        TrackContext trackContext = trackManager.getCurrentTrackContext();
        for (String keyProperty : keyProperties) {

            if (entity != null) {
                Object predicateValue = getPredicateValue(entity, trackContext, keyProperty, entityMetadata);
                ColumnValuePredicate columnValuePredicate = new ColumnValuePredicate(table.getEntityTable(), keyProperty, predicateValue, SQLPredicateCompareEnum.EQ, this.getRuntimeContext());
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnValuePredicate);
//            AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, keyProperty, this));
                where.addPredicateSegment(andPredicateSegment);
            } else {
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table.getEntityTable(), keyProperty, this.getRuntimeContext()));
                where.addPredicateSegment(andPredicateSegment);
            }
        }
//        if(entityMetadata.hasVersionColumn()){
//            VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
//            AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, versionMetadata.getPropertyName(), this));
//            where.addPredicateSegment(andPredicateSegment);
//        }
    }

    @Override
    public EntityUpdateSQLExpression toExpression() {
        if (isExpressionUpdate) {
            return toUpdateExpression();
        } else {
            return toExpression(null);
        }
    }

    @Override
    public EntityUpdateSQLExpression toExpression(Object entity) {

        EntityTableExpressionBuilder table = getTables().get(0);
        AndPredicateSegment where = new AndPredicateSegment(true);
        buildEntityPredicateSegment(where, table, entity);
        PredicateSegment sqlWhere = buildWherePredicateSegment(where, table);
        SQLBuilderSegment updateSet = null;
        if (hasSetColumns()) {
            updateSet = getSetColumns().cloneSQLBuilder();
        } else {
            updateSet = new UpdateSetSQLBuilderSegment();
            buildAutoEntitySetColumns(table, updateSet, sqlWhere, entity);
        }
        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(runtimeContext, table.toExpression());
        updateSet.copyTo(easyUpdateSQLExpression.getSetColumns());
        sqlWhere.copyTo(easyUpdateSQLExpression.getWhere());
        return easyUpdateSQLExpression;
    }

    private EntityUpdateSQLExpression toUpdateExpression() {

        checkTable();
        if (!hasSetColumns()) {
            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
        }
        if (!hasWhere()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        EntityTableExpressionBuilder table = getTable(0);

        //逻辑删除
        PredicateSegment sqlWhere = buildWherePredicateSegment(getWhere(), table);

        SQLBuilderSegment updateSet = buildSetSQLSegment(table);
        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(runtimeContext, table.toExpression());
        updateSet.copyTo(easyUpdateSQLExpression.getSetColumns());
        sqlWhere.copyTo(easyUpdateSQLExpression.getWhere());
        return easyUpdateSQLExpression;
    }

    @Override
    public EntityUpdateExpressionBuilder cloneEntityExpressionBuilder() {

        EntityUpdateExpressionBuilder updateExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityUpdateExpressionBuilder(expressionContext,isExpressionUpdate);

        if (hasSetColumns()) {
            getSetColumns().copyTo(updateExpressionBuilder.getSetColumns());
        }
        if (hasWhere()) {
            getWhere().copyTo(updateExpressionBuilder.getWhere());
        }
        if (hasSetIgnoreColumns()) {
            getSetIgnoreColumns().copyTo(updateExpressionBuilder.getSetIgnoreColumns());
        }
        if (hasWhereColumns()) {
            getWhereColumns().copyTo(updateExpressionBuilder.getWhereColumns());
        }
        for (EntityTableExpressionBuilder table : super.tables) {
            updateExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        return updateExpressionBuilder;
    }
}
