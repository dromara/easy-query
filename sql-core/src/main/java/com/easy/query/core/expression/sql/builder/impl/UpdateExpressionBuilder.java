package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateEntityColumnInterceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityTrackProperty;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.builder.impl.OnlySelectorImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.impl.ColumnOnlySelectorImpl;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnEqualsPropertyPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnEqualsTrackPropertyPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnNullAssertPredicate;
import com.easy.query.core.expression.segment.impl.InsertUpdateColumnConfigureSegmentImpl;
import com.easy.query.core.expression.segment.index.SegmentIndex;
import com.easy.query.core.expression.sql.builder.ColumnConfigurerContext;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.TableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.ignore.EntityUpdateSetProcessor;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyColumnSegmentUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/4 17:05
 */
public class UpdateExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements EntityUpdateExpressionBuilder {

    protected final boolean isExpressionUpdate;
    protected SQLBuilderSegment setColumns;
    protected PredicateSegment where;
    protected SQLBuilderSegment setIgnoreColumns;
    protected SQLBuilderSegment whereColumns;
    protected Map<String, ColumnConfigurerContext> columnConfigurers;

    public UpdateExpressionBuilder(ExpressionContext queryExpressionContext, Class<?> queryClass, boolean isExpressionUpdate) {
        super(queryExpressionContext, queryClass);
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
    public Map<String, ColumnConfigurerContext> getColumnConfigurer() {
        if (columnConfigurers == null) {
            columnConfigurers = new HashMap<>();
        }
        return columnConfigurers;
    }

    private void checkTable() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            //未找到查询表信息
            throw new EasyQueryException("not found any table in delete expression build.");
        }
        if (tableCount > 1) {
            //找到多张表信息
            throw new EasyQueryInvalidOperationException("not support multi table in delete expression build.");
        }
    }

    @Override
    public EntityUpdateSQLExpression toExpression() {
        if (isExpressionUpdate) {
            return toUpdateExpression();
        } else {
            throw new UnsupportedOperationException();
//            return toExpression(null);
        }
    }

    @Override
    public EntityUpdateSQLExpression toExpression(@NotNull Object entity) {
        Objects.requireNonNull(entity,"UpdateExpressionBuilder entity is null.");
        checkTable();
        EntityTableExpressionBuilder entityTableExpressionBuilder = getTables().get(0);
        return entityToExpression(entity, entityTableExpressionBuilder);
    }

    private EntityUpdateSQLExpression toUpdateExpression() {

        checkTable();
//        if (EasySQLSegmentUtil.isEmpty(setColumns)) {
//            log.warn("'UPDATE' statement without 'SET',not generate sql execute");
//        }
        if (EasySQLSegmentUtil.isEmpty(where)) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
//        EntityTableExpressionBuilder tableExpressionBuilder = getTable(0);
//        TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
//        if (EasySQLSegmentUtil.isEmpty(setColumns)) {
//            SetterImpl setter = new SetterImpl(this, getSetColumns());
//            if(EasyCollectionUtil.isNotEmpty(entityTable.getEntityMetadata().getKeyProperties())){
//                for (String keyProperty : entityTable.getEntityMetadata().getKeyProperties()) {
//                    setter.setWithColumn(entityTable,keyProperty,keyProperty);
//                }
//            }else{
//                String firstProperty = EasyCollectionUtil.first(entityTable.getEntityMetadata().getProperties());
//                setter.setWithColumn(entityTable,firstProperty,firstProperty);
//            }
//            SQLExpressionInvokeFactory easyQueryLambdaFactory = getRuntimeContext().getSQLExpressionInvokeFactory();
//            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(entityTable, this, where);
//            sqlPredicate.sqlNativeSegment("1=2");
//        }

        EntityTableExpressionBuilder tableExpressionBuilder = getTable(0);
        SQLBuilderSegment updateSetSQLSegment = buildSetSQLSegment(tableExpressionBuilder);
        //逻辑删除
        PredicateSegment sqlWhere = sqlPredicateFilter(tableExpressionBuilder, where);
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata,tableExpressionBuilder.toExpression());

        addRelationTables(easyUpdateSQLExpression);

        updateSetSQLSegment.copyTo(easyUpdateSQLExpression.getSetColumns());
        sqlWhere.copyTo(easyUpdateSQLExpression.getWhere());
        return easyUpdateSQLExpression;
    }

    protected SQLBuilderSegment buildSetSQLSegment(EntityTableExpressionBuilder table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        SQLBuilderSegment updateSet = getSetColumns().cloneSQLBuilder();
        ColumnSetter<Object> sqlColumnSetter = getRuntimeContext().getSQLExpressionInvokeFactory().createColumnSetter(table.getEntityTable(), this, updateSet);

        //如果更新拦截器不为空
        List<UpdateSetInterceptor> updateSetInterceptors = entityMetadata.getUpdateSetInterceptors();
        if (EasyCollectionUtil.isNotEmpty(updateSetInterceptors)) {

            Predicate<Interceptor> interceptorFilter = getExpressionContext().getInterceptorFilter();
            for (UpdateSetInterceptor updateSetInterceptor : updateSetInterceptors) {
                if (interceptorFilter.test(updateSetInterceptor)) {
                    updateSetInterceptor.configure(entityMetadata.getEntityClass(), this, sqlColumnSetter);
                }
            }
        }
        if (entityMetadata.hasVersionColumn()) {
            boolean ignoreVersion = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.IGNORE_VERSION);
            if (!ignoreVersion) {
                Object version = expressionContext.getVersion();
                if (Objects.nonNull(version)) {
                    VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                    String propertyName = versionMetadata.getPropertyName();
                    VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
                    Object newVersionValue = easyVersionStrategy.nextVersion(entityMetadata, propertyName, version);
                    sqlColumnSetter.set(propertyName, newVersionValue);
                } else {
                    throw new EasyQueryInvalidOperationException("entity:" + EasyClassUtil.getSimpleName(table.getEntityClass()) + " has version expression not found version");
                }
            }
        }
        return updateSet;
    }

    public EntityUpdateSQLExpression entityToExpression(@NotNull Object entity, EntityTableExpressionBuilder tableExpressionBuilder) {

        TrackManager trackManager = expressionContext.getRuntimeContext().getTrackManager();
        TrackContext trackContext = trackManager.getCurrentTrackContext();
        EntityUpdateSetProcessor entityUpdateSetProcessor = new EntityUpdateSetProcessor(entity, expressionContext);
        PredicateSegment where = buildPropertyWhere(tableExpressionBuilder, entity, trackContext, entityUpdateSetProcessor);//构建指定的where列或者主键key
        if (EasySQLSegmentUtil.isEmpty(where)) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }

        PredicateSegment sqlWhere = sqlPredicateFilter(tableExpressionBuilder, where);
        //替换掉配置的片段
        SQLBuilderSegment updateSet = updateSetConfigurer(tableExpressionBuilder,getUpdateSetSegment(sqlWhere, entity, tableExpressionBuilder, entityUpdateSetProcessor),entity);

        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata,tableExpressionBuilder.toExpression());


        updateSet.copyTo(easyUpdateSQLExpression.getSetColumns());
        sqlWhere.copyTo(easyUpdateSQLExpression.getWhere());
        return easyUpdateSQLExpression;
    }

    private SQLBuilderSegment updateSetConfigurer(TableExpressionBuilder tableExpressionBuilder, SQLBuilderSegment updateSet,@NotNull Object entity) {
        EntityMetadata entityMetadata = tableExpressionBuilder.getEntityTable().getEntityMetadata();

        //如果更新拦截器不为空
        List<UpdateEntityColumnInterceptor> updateEntityColumnInterceptors = entityMetadata.getUpdateEntityColumnInterceptors();
        if (EasyCollectionUtil.isNotEmpty(updateEntityColumnInterceptors)) {
            TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
            ColumnOnlySelector<Object> columnOnlySelector = new ColumnOnlySelectorImpl<>(entityTable, new OnlySelectorImpl(runtimeContext, expressionContext, updateSet));

            Predicate<Interceptor> interceptorFilter = getExpressionContext().getInterceptorFilter();
            for (UpdateEntityColumnInterceptor updateEntityColumnInterceptor : updateEntityColumnInterceptors) {
                if (interceptorFilter.test(updateEntityColumnInterceptor)) {
                    updateEntityColumnInterceptor.configure(entityMetadata.getEntityClass(), this, columnOnlySelector,entity);
                }
            }
        }

        boolean hasConfigure = columnConfigurers != null && !columnConfigurers.isEmpty();
        if (!hasConfigure) {
            return updateSet;
        }
        int size = updateSet.getSQLSegments().size();
        for (int i = 0; i < size; i++) {
            InsertUpdateSetColumnSQLSegment sqlSegment = (InsertUpdateSetColumnSQLSegment) updateSet.getSQLSegments().get(i);
            ColumnConfigurerContext columnConfigurerContext = columnConfigurers.get(sqlSegment.getPropertyName());
            if (columnConfigurerContext == null) {
                continue;
            }
            updateSet.getSQLSegments().set(i, new InsertUpdateColumnConfigureSegmentImpl(sqlSegment, getExpressionContext(), columnConfigurerContext.getSqlSegment(), columnConfigurerContext.getSqlNativeExpressionContext()));
        }
        return updateSet;
    }

    protected SQLBuilderSegment getUpdateSetSegment(PredicateSegment sqlWhere, Object entity, EntityTableExpressionBuilder tableExpressionBuilder, EntityUpdateSetProcessor entityUpdateSetProcessor) {
        if (EasySQLSegmentUtil.isNotEmpty(setColumns)) {
            //手动指定的情况下如果指定了原子更新列一样需要考虑是否开启环境track
//            TrackManager trackManager = runtimeContext.getTrackManager();
//            boolean envTracked = trackManager.currentThreadTracking();
//            for (SQLSegment sqlSegment : setColumns.getSQLSegments()) {
//                if (sqlSegment instanceof SQLEntitySegment) {
////                    SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
////                    ColumnMetadata columnMetadata = sqlEntitySegment.getTable().getEntityMetadata().getColumnNotNull(sqlEntitySegment.getPropertyName());
////                    if (columnMetadata.isConcurrentUpdateInTrack() && !envTracked) {
////                        throw new EasyQueryColumnValueUpdateAtomicTrackException("entity:" + EasyClassUtil.getSimpleName(sqlEntitySegment.getTable().getEntityClass()) + " property:[" + sqlEntitySegment.getPropertyName() + "] has configure value update atomic track，but current update not use track update.");
////                    }
//                }
//            }
            //如果存在版本号需要关联
            SQLBuilderSegment sqlSetBuilderSegment = setColumns.cloneSQLBuilder();
            TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
//            EntityMetadata entityMetadata = entityTable.getEntityMetadata();
//            if (entityMetadata.hasVersionColumn()) {
//                boolean ignoreVersion = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.IGNORE_VERSION);
//                if (!ignoreVersion) {
//                    VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
//                    VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
//                    sqlSetBuilderSegment.append(new ColumnVersionPropertySegmentImpl(entityTable, versionMetadata.getPropertyName(), easyVersionStrategy, runtimeContext));
//                }
//            }
            return processVersionColumn(sqlSetBuilderSegment, entityTable);
        } else {
            return buildUpdateSetByWhere(sqlWhere, entity, tableExpressionBuilder, entityUpdateSetProcessor);
        }
    }

    protected SQLBuilderSegment processVersionColumn(SQLBuilderSegment sqlSetBuilderSegment, TableAvailable entityTable) {
        EntityMetadata entityMetadata = entityTable.getEntityMetadata();
        if (entityMetadata.hasVersionColumn()) {
            boolean ignoreVersion = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.IGNORE_VERSION);
            if (!ignoreVersion) {
                VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
                InsertUpdateSetColumnSQLSegment updateColumnSegment = sqlSegmentFactory.createUpdateColumnSegment(entityTable, versionMetadata.getPropertyName(), getExpressionContext(), easyVersionStrategy);
                sqlSetBuilderSegment.append(updateColumnSegment);
            }
        }
        return sqlSetBuilderSegment;
    }

    protected SQLBuilderSegment buildUpdateSetByWhere(PredicateSegment sqlWhere, Object entity, EntityTableExpressionBuilder tableExpressionBuilder, EntityUpdateSetProcessor entityUpdateSetProcessor) {
        SQLBuilderSegment updateSetSQLBuilderSegment = new UpdateSetSQLBuilderSegment();
        TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
        EntityMetadata entityMetadata = entityTable.getEntityMetadata();
        Class<?> entityClass = entityMetadata.getEntityClass();
        //非手动指定的那么需要移除where的那一部分
        SegmentIndex predicateIndex = sqlWhere.buildPredicateIndex();
        //查询其他所有列除了在where里面的
        Collection<String> properties = entityMetadata.getProperties();
        boolean hasSetIgnoreColumns = EasySQLSegmentUtil.isNotEmpty(setIgnoreColumns);
        boolean envTracked = runtimeContext.getTrackManager().currentThreadTracking();
//        ColumnSetter<Object> columnSetter = runtimeContext.getSQLExpressionInvokeFactory().createColumnSetter(tableExpressionBuilder.getEntityTable(), this, updateSetSQLBuilderSegment);
        for (String property : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
            //(&&!columnMetadata.isLarge())
            if (columnMetadata.isPrimary() || columnMetadata.isVersion() || columnMetadata.isValueObject() || (columnMetadata.isUpdateIgnore() && !columnMetadata.isUpdateSetInTrackDiff())) {
                continue;
            }
            if (entityUpdateSetProcessor.shouldRemove(property)) {
                continue;
            }

            //如果不是原子更新的话如果出现在where了的属性不应该出现在set中,除非手动指定,但是如果是需要更新的那么应该也需要添加到set中
            if ((columnMetadata.isUpdateIgnore() && columnMetadata.isUpdateSetInTrackDiff()) || predicateIndex.contains(entityClass, property)) {
                EntityTrackProperty entityTrackProperty = entityUpdateSetProcessor.getEntityTrackProperty();
                if (entityTrackProperty == null || !entityTrackProperty.getDiffProperties().containsKey(property)) {
                    continue;
                }
            }
            if (hasSetIgnoreColumns && setIgnoreColumns.containsOnce(entityClass, property)) {
                continue;
            }
            //如果是 track value
//
//            if (columnMetadata.isConcurrentUpdateInTrack()) {
//                if (!envTracked) {
//                    throw new EasyQueryColumnValueUpdateAtomicTrackException("entity:" + EasyClassUtil.getSimpleName(entityClass) + " property:[" + property + "] has configure value update atomic track，but current update not use track update.");
//                }
//            }
            InsertUpdateSetColumnSQLSegment updateColumnSegment = sqlSegmentFactory.createUpdateColumnSegment(entityTable, property, getExpressionContext(), null);
            updateSetSQLBuilderSegment.append(updateColumnSegment);
//            updateSetSQLBuilderSegment.append(new ColumnPropertyPredicate(entityTable, property, runtimeContext));

        }

//        if (entityMetadata.hasVersionColumn()) {
//            boolean ignoreVersion = expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.IGNORE_VERSION);
//            if (!ignoreVersion) {
//                VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
//                VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
//                updateSetSQLBuilderSegment.append(new ColumnVersionPropertySegmentImpl(entityTable, versionMetadata.getPropertyName(), easyVersionStrategy, runtimeContext));
//            }
//        }
//        return updateSetSQLBuilderSegment;
        return processVersionColumn(updateSetSQLBuilderSegment, entityTable);
    }

    /**
     * 构建where条件
     *
     * @return
     */
    protected PredicateSegment buildPropertyWhere(EntityTableExpressionBuilder tableExpressionBuilder, Object entity, TrackContext trackContext, EntityUpdateSetProcessor entityUpdateSetProcessor) {
        AndPredicateSegment where = new AndPredicateSegment(true);

        if (EasySQLSegmentUtil.isNotEmpty(whereColumns)) {
            for (SQLSegment sqlSegment : whereColumns.getSQLSegments()) {
                if (!(sqlSegment instanceof SQLEntitySegment)) {
                    throw new EasyQueryException("where expression sql segment not instanceof SQLEntitySegment");
                }
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                buildWhereByProperty(where, trackContext, sqlEntitySegment.getPropertyName(), entity, tableExpressionBuilder, true);
            }
        } else {
            EntityMetadata entityMetadata = tableExpressionBuilder.getEntityMetadata();
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            if (keyProperties.isEmpty()) {
                throw new EasyQueryException("entity:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " not found primary key properties");
            }
            for (String keyProperty : keyProperties) {
                buildWhereByProperty(where, trackContext, keyProperty, entity, tableExpressionBuilder, false);
            }
        }
        return where;
    }

    protected void buildWhereByProperty(PredicateSegment where, TrackContext trackContext, String propertyName, Object entity, EntityTableExpressionBuilder tableExpressionBuilder, boolean nullAssert) {
        if (entity != null) {
            EntityPredicateValue predicateValue = getPredicateValue(entity, trackContext, propertyName, tableExpressionBuilder.getEntityMetadata());
            if (nullAssert && predicateValue.getPredicateValue() == null) {
                TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
                ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(propertyName);
                Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(entityTable, columnMetadata, expressionContext);
                ColumnNullAssertPredicate columnPredicate = new ColumnNullAssertPredicate(column2Segment, SQLPredicateCompareEnum.IS_NULL);
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPredicate);
                where.addPredicateSegment(andPredicateSegment);
            } else {
                if (predicateValue.isTrack()) {
                    //如果是追踪应该使用track的original属性
                    TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
                    ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(propertyName);
                    Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(entityTable, columnMetadata, getExpressionContext());
                    ColumnValue2Segment columnTrackValue2Segment = EasyColumnSegmentUtil.createColumnTrackValue2Segment(entityTable, columnMetadata, getExpressionContext());

                    ColumnEqualsTrackPropertyPredicate columnPropertyPredicate = new ColumnEqualsTrackPropertyPredicate(column2Segment, columnTrackValue2Segment, SQLPredicateCompareEnum.EQ);
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPropertyPredicate);
                    where.addPredicateSegment(andPredicateSegment);
                } else {
                    TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
                    ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(propertyName);
                    Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(entityTable, columnMetadata, this.getExpressionContext());
                    ColumnValue2Segment columnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(entityTable, columnMetadata, this.getExpressionContext(), null);

                    ColumnEqualsPropertyPredicate columnPropertyPredicate = new ColumnEqualsPropertyPredicate(column2Segment, columnValue2Segment);
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPropertyPredicate);
                    where.addPredicateSegment(andPredicateSegment);
                }
            }
        } else {
            TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
            ColumnMetadata columnMetadata = entityTable.getEntityMetadata().getColumnNotNull(propertyName);
            Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(entityTable, columnMetadata, this.getExpressionContext());
            ColumnValue2Segment columnValue2Segment = EasyColumnSegmentUtil.createColumnValue2Segment(entityTable, columnMetadata, this.getExpressionContext(), null);
            ColumnEqualsPropertyPredicate columnPropertyPredicate = new ColumnEqualsPropertyPredicate(column2Segment, columnValue2Segment);
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPropertyPredicate);
            where.addPredicateSegment(andPredicateSegment);
        }
    }

    protected EntityPredicateValue getPredicateValue(Object entity, TrackContext trackContext, String propertyName, EntityMetadata entityMetadata) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
//        Property<Object, ?> beanGetter = columnMetadata.getGetterCaller();
        if (trackContext != null) {
            EntityState trackEntityState = trackContext.getTrackEntityState(entity);
            if (trackEntityState != null) {
                Object originalEntity = trackEntityState.getOriginalValue();
                if (originalEntity != null) {
                    Object predicateValue = EasyBeanUtil.getPropertyValue(originalEntity, entityMetadata, columnMetadata);
                    return new EntityPredicateValue(true, predicateValue);
//                    return beanGetter.apply(originalEntity);
                }
            }
        }
        Object predicateValue = EasyBeanUtil.getPropertyValue(entity, entityMetadata, columnMetadata);
        return new EntityPredicateValue(false, predicateValue);
//        return beanGetter.apply(entity);
    }

    @Override
    public EntityUpdateExpressionBuilder cloneEntityExpressionBuilder() {

        EntityUpdateExpressionBuilder updateExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityUpdateExpressionBuilder(expressionContext, queryClass, isExpressionUpdate);

        if (hasSetColumns()) {
            getSetColumns().copyTo(updateExpressionBuilder.getSetColumns());
        }
        if (hasWhere()) {
            getWhere().copyTo(updateExpressionBuilder.getWhere());
        }
        if (hasSetIgnoreColumns()) {
            getSetIgnoreColumns().copyTo(updateExpressionBuilder.getSetIgnoreColumns());
        }
        if (EasySQLSegmentUtil.isNotEmpty(whereColumns)) {
            whereColumns.copyTo(updateExpressionBuilder.getWhereColumns());
        }
        if (columnConfigurers != null) {
            updateExpressionBuilder.getColumnConfigurer().putAll(columnConfigurers);
        }
        for (EntityTableExpressionBuilder table : super.tables) {
            updateExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        if(hasRelationTables()){
            for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> entry : relationTables.entrySet()) {
                updateExpressionBuilder.getRelationTables().put(entry.getKey(), entry.getValue().copyEntityTableExpressionBuilder());
            }
        }
        if(super.manyConfigurationMaps!=null){
            for (Map.Entry<RelationTableKey, ManyConfiguration> manyJoinConfigurationEntry : super.manyConfigurationMaps.entrySet()) {
                updateExpressionBuilder.addManyConfiguration(manyJoinConfigurationEntry.getKey(),manyJoinConfigurationEntry.getValue());
            }
        }
        if(super.manyJoinConfigurationSets!=null){
            for (RelationTableKey manyJoinConfigurationSet : super.manyJoinConfigurationSets) {
                updateExpressionBuilder.addManyJoinConfiguration(manyJoinConfigurationSet);
            }
        }
        return updateExpressionBuilder;
    }
}
