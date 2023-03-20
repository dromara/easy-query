package com.easy.query.core.query;

import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.enums.UpdateStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateIndex;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.interceptor.GlobalUpdateSetInterceptor;
import com.easy.query.core.track.EntityState;
import com.easy.query.core.track.TrackContext;
import com.easy.query.core.track.TrackDiffEntry;
import com.easy.query.core.track.TrackManager;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.TrackUtil;

import java.util.*;

/**
 * @FileName: EasySqlUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:05
 * @Created by xuejiaming
 */
public abstract class EasySqlUpdateExpression extends AbstractSqlPredicateEntityExpression implements SqlEntityUpdateExpression {

    protected final boolean isExpressionUpdate;
    private SqlBuilderSegment setColumns;
    private PredicateSegment where;
    private SqlBuilderSegment setIgnoreColumns;
    private SqlBuilderSegment whereColumns;

    public EasySqlUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext);
        this.isExpressionUpdate = isExpressionUpdate;
    }

    @Override
    public SqlBuilderSegment getSetColumns() {
        if (setColumns == null) {
            setColumns = new UpdateSetSqlBuilderSegment();
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
    public SqlBuilderSegment getSetIgnoreColumns() {
        if (setIgnoreColumns == null) {
            setIgnoreColumns = new UpdateSetSqlBuilderSegment();
        }
        return setIgnoreColumns;
    }

    @Override
    public boolean hasSetIgnoreColumns() {
        return setIgnoreColumns != null && setIgnoreColumns.isNotEmpty();
    }


    @Override
    public SqlBuilderSegment getWhereColumns() {
        if (whereColumns == null) {
            whereColumns = new ProjectSqlBuilderSegment();
        }
        return whereColumns;
    }

    @Override
    public boolean hasWhereColumns() {
        return whereColumns != null && whereColumns.isNotEmpty();
    }


    @Override
    public String toSql() {
        getSqlExpressionContext().clearParameters();
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        if (isExpressionUpdate) {
            return expressionUpdate();
        } else {
            return toSql(null);
        }
    }

    protected String expressionUpdate() {

        if (!hasSetColumns()) {
            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
        }
        if (!hasWhere()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        SqlEntityTableExpression table = getTable(0);
        //逻辑删除
        PredicateSegment sqlWhere = buildWherePredicateSegment(getWhere(), table);

        SqlBuilderSegment updateSet = buildSetSqlSegment(table);
        return internalToSql(table, updateSet, sqlWhere);
    }

//    protected String entityUpdate() {
//        SqlEntityTableExpression table = getTable(0);
//        //如果没有指定where那么就使用主键作为更新条件只构建一次where
//        if (!hasWhere()) {
//            PredicateSegment where = getWhere();
//            buildEntityPredicateSegment(where, table);
//        }
//        PredicateSegment sqlWhere = buildWherePredicateSegment(getWhere(), table);
//        buildAutoEntitySetColumns(table, sqlWhere);
//        if (getSetColumns().isEmpty()) {
//            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
//        }
//        return internalToSql(table, getSetColumns(), sqlWhere);
//    }

    protected String internalToSql(SqlEntityTableExpression table, SqlBuilderSegment updateSet, PredicateSegment sqlWhere) {

        EntityMetadata entityMetadata = table.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        return "UPDATE " + tableName + " SET " + updateSet.toSql() + " WHERE " +
                sqlWhere.toSql();
    }

    protected SqlBuilderSegment buildSetSqlSegment(SqlEntityTableExpression table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        SqlBuilderSegment updateSet = getSetColumns().cloneSqlBuilder();
        //如果更新拦截器不为空
        if (getSqlExpressionContext().isUseInterceptor() && ArrayUtil.isNotEmpty(entityMetadata.getUpdateSetInterceptors())) {
            EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
            SqlColumnSetter<Object> sqlColumnSetter = getRuntimeContext().getEasyQueryLambdaFactory().createSqlColumnSetter(0, this, updateSet);
            for (String updateSetInterceptor : entityMetadata.getUpdateSetInterceptors()) {
                GlobalUpdateSetInterceptor globalInterceptor = (GlobalUpdateSetInterceptor) easyQueryConfiguration.getGlobalInterceptor(updateSetInterceptor);
                globalInterceptor.configure(table.getClass(), this, sqlColumnSetter);

            }
        }
        return updateSet;
    }

    protected PredicateSegment buildWherePredicateSegment(PredicateSegment where, SqlEntityTableExpression table) {
        if (where.isEmpty()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        return sqlPredicateFilter(table, where);
    }

    @Override
    public String toSql(Object entity) {
        getSqlExpressionContext().clearParameters();
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        return entityToSql(entity);
    }

    private String entityToSql(Object entity){
        SqlEntityTableExpression table = getTables().get(0);
        AndPredicateSegment where = new AndPredicateSegment(true);
        buildEntityPredicateSegment(where, table);
        PredicateSegment sqlWhere = buildWherePredicateSegment(where, table);
        SqlBuilderSegment updateSet = null;
        if (hasSetColumns()) {
            updateSet = getSetColumns().cloneSqlBuilder();
        } else {
            updateSet = new UpdateSetSqlBuilderSegment();
            buildAutoEntitySetColumns(table, updateSet, sqlWhere,entity);
        }
        if (updateSet.isEmpty()) {
            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
        }
        return internalToSql(table, updateSet, sqlWhere);
    }

    protected void buildAutoEntitySetColumns(SqlEntityTableExpression table, SqlBuilderSegment updateSet, PredicateSegment sqlWhere, Object entity) {

        //如果用户没有指定set的列,那么就是set所有列,并且要去掉主键部分
        if (!hasSetColumns()) {
            Class<?> entityClass = table.entityClass();
            EntityMetadata entityMetadata = table.getEntityMetadata();
            EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
            EasyQueryLambdaFactory easyQueryLambdaFactory = runtimeContext.getEasyQueryLambdaFactory();
            SqlColumnSelector<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetSelector(table.getIndex(), this, updateSet);
            sqlColumnSetter.columnAll();
            //非手动指定的那么需要移除where的那一部分
            PredicateIndex predicateIndex = sqlWhere.buildPredicateIndex();
            TrackManager trackManager = runtimeContext.getTrackManager();
            Set<String> ignorePropertySet=new HashSet<>(entityMetadata.getProperties().size());

            if(entity!=null){
                //以下应该二选一
                //todo 获取更新策略按需更新
                UpdateStrategyEnum updateStrategy = sqlExpressionContext.getUpdateStrategy();
                if(!Objects.equals(UpdateStrategyEnum.DEFAULT,updateStrategy) ){
                    if(Objects.equals(UpdateStrategyEnum.ONLY_NOT_NULL_COLUMNS,updateStrategy)||Objects.equals(UpdateStrategyEnum.ONLY_NULL_COLUMNS,updateStrategy)){
                        Set<String> beanMatchProperties = BeanUtil.getBeanMatchProperties(runtimeContext.getEntityMetadataManager(), entity, Objects.equals(UpdateStrategyEnum.ONLY_NOT_NULL_COLUMNS,updateStrategy)?Objects::isNull:Objects::nonNull);
                        ignorePropertySet.addAll(beanMatchProperties);
                    }
                }else{
                    //todo 判断是否追踪
                    if (trackManager.currentThreadTracking()) {
                        TrackContext trackContext =trackManager.getCurrentTrackContext();
                        //如果当前对象是追踪的并且没有指定更新策略
                        EntityState trackEntityState = trackContext.getTrackEntityState(entity);
                        if (trackEntityState != null) {
                            Map<String, TrackDiffEntry> trackDiffProperty = TrackUtil.getTrackDiffProperty(runtimeContext.getEntityMetadataManager(), trackEntityState);
                            ignorePropertySet.addAll(trackDiffProperty.keySet());
                        }
                    }
                }
            }

            updateSet.getSqlSegments().removeIf(o -> {

                String propertyName = ((SqlEntitySegment) o).getPropertyName();

                return predicateIndex.contains(entityClass, propertyName)||ignorePropertySet.contains(propertyName);
            });
        }
    }

    /**
     * 构建where条件
     *
     * @param where
     * @param table
     */
    private void buildEntityPredicateSegment(PredicateSegment where, SqlEntityTableExpression table) {
        if (hasWhereColumns()) {
            buildPredicateWithWhereColumns(where, getWhereColumns(), table);
        } else {
            buildPredicateWithKeyColumns(where, table);
        }
    }

    /**
     * 根据指定where column构建where
     *
     * @param where
     * @param table
     */
    private void buildPredicateWithWhereColumns(PredicateSegment where, SqlBuilderSegment whereColumns, SqlEntityTableExpression table) {
        for (SqlSegment sqlSegment : whereColumns.getSqlSegments()) {
            if (!(sqlSegment instanceof SqlEntitySegment)) {
                throw new EasyQueryException("where 表达式片段不是SqlEntitySegment");
            }
            SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, sqlEntitySegment.getPropertyName(), this));
            where.addPredicateSegment(andPredicateSegment);
        }

    }

    private void buildPredicateWithKeyColumns(PredicateSegment where, SqlEntityTableExpression table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        if (keyProperties.isEmpty()) {
            throw new EasyQueryException("entity:" + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " not found primary key properties");
        }
        for (String keyProperty : keyProperties) {
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, keyProperty, this));
            where.addPredicateSegment(andPredicateSegment);
        }
    }
}
