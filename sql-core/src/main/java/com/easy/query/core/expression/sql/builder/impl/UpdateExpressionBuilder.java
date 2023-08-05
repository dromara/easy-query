package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityTrackProperty;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackDiffEntry;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.extension.track.update.ValueUpdateAtomicTrack;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.EntityUpdateTypeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryColumnValueUpdateAtomicTrackException;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateIndex;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnVersionPropertyPredicate;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.impl.ignore.EntityUpdateSetProcessor;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collection;
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
    private static final Log log = LogFactory.getLog(UpdateExpressionBuilder.class);

    protected final boolean isExpressionUpdate;
    private SQLBuilderSegment setColumns;
    private PredicateSegment where;
    private SQLBuilderSegment setIgnoreColumns;
    private SQLBuilderSegment whereColumns;

    public UpdateExpressionBuilder(ExpressionContext queryExpressionContext,Class<?> queryClass, boolean isExpressionUpdate) {
        super(queryExpressionContext,queryClass);
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

    private void checkTable() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
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
        checkTable();
        EntityTableExpressionBuilder entityTableExpressionBuilder = getTables().get(0);
        return entityToExpression(entity, entityTableExpressionBuilder);
    }

    private EntityUpdateSQLExpression toUpdateExpression() {

        checkTable();
        if (EasySQLSegmentUtil.isEmpty(setColumns)) {
            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
        }
        if (EasySQLSegmentUtil.isEmpty(where)) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        EntityTableExpressionBuilder tableExpressionBuilder = getTable(0);

        SQLBuilderSegment updateSetSQLSegment = buildSetSQLSegment(tableExpressionBuilder);
        //逻辑删除
        PredicateSegment sqlWhere = sqlPredicateFilter(tableExpressionBuilder, where);
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata, tableExpressionBuilder.toExpression());
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
                if(interceptorFilter.test(updateSetInterceptor)){
                    updateSetInterceptor.configure(entityMetadata.getEntityClass(), this, sqlColumnSetter);
                }
            }
        }
        if (entityMetadata.hasVersionColumn()) {
            Object version = expressionContext.getVersion();
            if (Objects.nonNull(version)) {
                VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                String propertyName = versionMetadata.getPropertyName();
                VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
                Object newVersionValue = easyVersionStrategy.nextVersion(entityMetadata, propertyName, version);
                sqlColumnSetter.set(propertyName, newVersionValue);
            }else if(expressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.NO_VERSION_ERROR)){
                throw new EasyQueryInvalidOperationException("entity:"+ EasyClassUtil.getSimpleName(table.getEntityClass())+" has version expression not found version");
            }
        }
        return updateSet;
    }

    protected void throwValueUpdateAtomicTrack(EntityMetadata entityMetadata) {
        if (entityMetadata.isColumnValueUpdateAtomicTrack()) {
            throw new EasyQueryColumnValueUpdateAtomicTrackException("entity:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " property has configure value update atomic track，but current update not use track update.");
        }
    }

    public EntityUpdateSQLExpression entityToExpression(Object entity, EntityTableExpressionBuilder tableExpressionBuilder) {
        PredicateSegment where = buildPropertyWhere(tableExpressionBuilder, entity);//构建指定的where列或者主键key
        if (EasySQLSegmentUtil.isEmpty(where)) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        //track value update

        EntityMetadata entityMetadata = tableExpressionBuilder.getEntityMetadata();

        EntityUpdateSetProcessor entityUpdateSetProcessor = EasySQLSegmentUtil.isEmpty(setColumns) ? new EntityUpdateSetProcessor(entity, expressionContext) : null;
        if (entityUpdateSetProcessor != null) {
            if (!Objects.equals(EntityUpdateTypeEnum.TRACK, entityUpdateSetProcessor.getEntityUpdateType())) {
                throwValueUpdateAtomicTrack(entityMetadata);
            }
            EntityTrackProperty entityTrackProperty = entityUpdateSetProcessor.getEntityTrackProperty();
            if (entityTrackProperty != null) {

                SQLExpressionInvokeFactory sqlExpressionInvokeFactory = runtimeContext.getSQLExpressionInvokeFactory();
                WherePredicate<Object> wherePredicate = sqlExpressionInvokeFactory.createWherePredicate(tableExpressionBuilder.getEntityTable(), this, where);
                //设置where
                for (Map.Entry<String, TrackDiffEntry> propertyTrackDiff : entityTrackProperty.getDiffProperties().entrySet()) {
                    String propertyName = propertyTrackDiff.getKey();
                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                    ValueUpdateAtomicTrack<Object> trackValueUpdate = columnMetadata.getValueUpdateAtomicTrack();
                    if (trackValueUpdate != null) {
                        TrackDiffEntry diffValue = propertyTrackDiff.getValue();
                        trackValueUpdate.configureWhere(propertyName, diffValue.getOriginal(), diffValue.getCurrent(), wherePredicate);
                    }
                }
            }
        } else {
            throwValueUpdateAtomicTrack(entityMetadata);
        }
        PredicateSegment sqlWhere = sqlPredicateFilter(tableExpressionBuilder, where);
        SQLBuilderSegment updateSet = getUpdateSetSegment(sqlWhere, entity, tableExpressionBuilder, entityUpdateSetProcessor);

        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata, tableExpressionBuilder.toExpression());
        updateSet.copyTo(easyUpdateSQLExpression.getSetColumns());
        sqlWhere.copyTo(easyUpdateSQLExpression.getWhere());
        return easyUpdateSQLExpression;
    }

    protected SQLBuilderSegment getUpdateSetSegment(PredicateSegment sqlWhere, Object entity, EntityTableExpressionBuilder tableExpressionBuilder, EntityUpdateSetProcessor entityUpdateSetProcessor) {
        if (EasySQLSegmentUtil.isNotEmpty(setColumns)) {
            return setColumns.cloneSQLBuilder();
        } else {
            return buildUpdateSetByWhere(sqlWhere, entity, tableExpressionBuilder, entityUpdateSetProcessor);
        }
    }

    protected SQLBuilderSegment buildUpdateSetByWhere(PredicateSegment sqlWhere, Object entity, EntityTableExpressionBuilder tableExpressionBuilder, EntityUpdateSetProcessor entityUpdateSetProcessor) {
        SQLBuilderSegment updateSetSQLBuilderSegment = new UpdateSetSQLBuilderSegment();
        TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
        EntityMetadata entityMetadata = entityTable.getEntityMetadata();
        Class<?> entityClass = entityMetadata.getEntityClass();
        //非手动指定的那么需要移除where的那一部分
        PredicateIndex predicateIndex = sqlWhere.buildPredicateIndex();
        //查询其他所有列除了在where里面的
        Collection<String> properties = entityMetadata.getProperties();
        boolean hasSetIgnoreColumns = EasySQLSegmentUtil.isNotEmpty(setIgnoreColumns);
        ColumnSetter<Object> columnSetter = runtimeContext.getSQLExpressionInvokeFactory().createColumnSetter(tableExpressionBuilder.getEntityTable(), this, updateSetSQLBuilderSegment);
        for (String property : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(property);
            if (columnMetadata.isPrimary() || columnMetadata.isUpdateIgnore() || columnMetadata.isVersion()) {
                continue;
            }
            if (entityUpdateSetProcessor.shouldRemove(property)) {
                continue;
            }
            ValueUpdateAtomicTrack<Object> valueUpdateAtomicTrack = columnMetadata.getValueUpdateAtomicTrack();

            //如果不是原子更新的话如果出现在where了的属性不应该出现在set中,除非手动指定,但是如果是需要更新的那么应该也需要添加到set中
            if (valueUpdateAtomicTrack == null && predicateIndex.contains(entityClass, property)) {
                EntityTrackProperty entityTrackProperty = entityUpdateSetProcessor.getEntityTrackProperty();
                if(entityTrackProperty==null||!entityTrackProperty.getDiffProperties().containsKey(property)){
                    continue;
                }
            }
            if (hasSetIgnoreColumns && setIgnoreColumns.containsOnce(entityClass, property)) {
                continue;
            }
            //如果是 track value

            if (valueUpdateAtomicTrack != null) {

                TrackDiffEntry trackDiffEntry = entityUpdateSetProcessor.trackValue(property);
                if (trackDiffEntry != null) {
                    //设置set
                    valueUpdateAtomicTrack.configureSet(property, trackDiffEntry.getOriginal(), trackDiffEntry.getCurrent(), columnSetter);
                    continue;
                }
            }
            updateSetSQLBuilderSegment.append(new ColumnPropertyPredicate(entityTable, property, runtimeContext));
//            updateSetSQLBuilderSegment.append(new ColumnPropertyPredicate(entityTable, property, runtimeContext));

        }

        if (entityMetadata.hasVersionColumn()) {
            VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
            VersionStrategy easyVersionStrategy = versionMetadata.getEasyVersionStrategy();
            updateSetSQLBuilderSegment.append(new ColumnVersionPropertyPredicate(entityTable, versionMetadata.getPropertyName(), easyVersionStrategy, runtimeContext));
        }
        return updateSetSQLBuilderSegment;
    }

    /**
     * 构建where条件
     *
     * @return
     */
    protected PredicateSegment buildPropertyWhere(EntityTableExpressionBuilder tableExpressionBuilder, Object entity) {
        AndPredicateSegment where = new AndPredicateSegment(true);
        TrackManager trackManager = expressionContext.getRuntimeContext().getTrackManager();
        TrackContext trackContext = trackManager.getCurrentTrackContext();

        if (EasySQLSegmentUtil.isNotEmpty(whereColumns)) {
            for (SQLSegment sqlSegment : whereColumns.getSQLSegments()) {
                if (!(sqlSegment instanceof SQLEntitySegment)) {
                    throw new EasyQueryException("where 表达式片段不是SQLEntitySegment");
                }
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                buildWhereByProperty(where, trackContext, sqlEntitySegment.getPropertyName(), entity, tableExpressionBuilder);
            }
        } else {
            EntityMetadata entityMetadata = tableExpressionBuilder.getEntityMetadata();
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            if (keyProperties.isEmpty()) {
                throw new EasyQueryException("entity:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " not found primary key properties");
            }
            for (String keyProperty : keyProperties) {
                buildWhereByProperty(where, trackContext, keyProperty, entity, tableExpressionBuilder);
            }
        }
        return where;
    }

    protected void buildWhereByProperty(PredicateSegment where, TrackContext trackContext, String propertyName, Object entity, EntityTableExpressionBuilder tableExpressionBuilder) {
        if (entity != null) {
            Object predicateValue = getPredicateValue(entity, trackContext, propertyName, tableExpressionBuilder.getEntityMetadata());
            if(predicateValue!=null){
                ColumnValuePredicate columnValuePredicate = new ColumnValuePredicate(tableExpressionBuilder.getEntityTable(), propertyName, predicateValue, SQLPredicateCompareEnum.EQ, runtimeContext);
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnValuePredicate);
                where.addPredicateSegment(andPredicateSegment);
            }else{
                ColumnPredicate columnPredicate = new ColumnPredicate(tableExpressionBuilder.getEntityTable(), propertyName, SQLPredicateCompareEnum.IS_NULL, runtimeContext);
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPredicate);
                where.addPredicateSegment(andPredicateSegment);
            }
        } else {
            ColumnPropertyPredicate columnPropertyPredicate = new ColumnPropertyPredicate(tableExpressionBuilder.getEntityTable(), propertyName, runtimeContext);
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPropertyPredicate);
            where.addPredicateSegment(andPredicateSegment);
        }
    }

    protected Object getPredicateValue(Object entity, TrackContext trackContext, String propertyName, EntityMetadata entityMetadata) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
        Property<Object, ?> beanGetter = columnMetadata.getGetterCaller();
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

    @Override
    public EntityUpdateExpressionBuilder cloneEntityExpressionBuilder() {

        EntityUpdateExpressionBuilder updateExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityUpdateExpressionBuilder(expressionContext,queryClass, isExpressionUpdate);

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
        for (EntityTableExpressionBuilder table : super.tables) {
            updateExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        return updateExpressionBuilder;
    }
}
