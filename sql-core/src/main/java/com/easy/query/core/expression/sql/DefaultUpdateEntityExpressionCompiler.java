//package com.easy.query.core.expression.sql;
//
//import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
//import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
//import com.easy.query.core.basic.plugin.interceptor.EasyPredicateFilterInterceptor;
//import com.easy.query.core.configuration.EasyQueryConfiguration;
//import com.easy.query.core.enums.SqlPredicateCompareEnum;
//import com.easy.query.core.exception.EasyQueryException;
//import com.easy.query.core.expression.lambda.SqlExpression;
//import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
//import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
//import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
//import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
//import com.easy.query.core.expression.segment.condition.PredicateSegment;
//import com.easy.query.core.expression.segment.condition.predicate.ColumnPredicate0;
//import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
//import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate0;
//import com.easy.query.core.expression.segment.condition.predicate.Predicate;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.metadata.VersionMetadata;
//import com.easy.query.core.util.ArrayUtil;
//import com.easy.query.core.util.EasyUtil;
//
//import java.util.List;
//import java.util.Objects;
//
///**
// * create time 2023/3/27 16:50
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DefaultUpdateEntityExpressionCompiler implements EntityExpressionCompiler {
//
//    protected final SqlBuilderSegment updateSet;
//    protected final PredicateSegment where;
//    protected final SqlEntityUpdateExpression sqlEntityUpdateExpression;
//    protected final SqlEntityTableExpression table;
//    protected final EntityMetadata entityMetadata;
//    protected final SqlExpressionContext sqlExpressionContext;
//    protected final EasyQueryRuntimeContext runtimeContext;
//    protected final Object entity;
//
//    public DefaultUpdateEntityExpressionCompiler(SqlEntityUpdateExpression sqlEntityUpdateExpression) {
//        this(sqlEntityUpdateExpression, null);
//    }
//
//    public DefaultUpdateEntityExpressionCompiler(SqlEntityUpdateExpression sqlEntityUpdateExpression, Object entity) {
//
//        int tableCount = sqlEntityUpdateExpression.getTables().size();
//        if (tableCount == 0) {
//            throw new EasyQueryException("未找到查询表信息");
//        }
//        if (tableCount > 1) {
//            throw new EasyQueryException("找到多张表信息");
//        }
//        this.sqlEntityUpdateExpression = sqlEntityUpdateExpression;
//        this.sqlExpressionContext = sqlEntityUpdateExpression.getSqlExpressionContext();
//        this.runtimeContext = sqlExpressionContext.getRuntimeContext();
//        this.table = sqlEntityUpdateExpression.getTable(0);
//        this.entityMetadata = table.getEntityMetadata();
//        this.entity = entity;
//        this.updateSet = new UpdateSetSqlBuilderSegment();
//        this.where = new AndPredicateSegment(true);
//    }
//
//
//    @Override
//    public String compile() {
//        sqlExpressionContext.clearParameters();
//        if (sqlEntityUpdateExpression.isExpression()) {
//            lambdaExpressionCompile();
//        } else {
//            entityExpressionCompile();
//        }
//
//        if (updateSet.isEmpty()) {
//            return null;
//        }
//
//        EntityMetadata entityMetadata = table.getEntityMetadata();
//        String tableName = entityMetadata.getTableName();
//        return "UPDATE " + tableName + " SET " + updateSet.toSql() + " WHERE " +
//                where.toSql();
//    }
//
//    public void lambdaExpressionCompile() {
//
//        if (!sqlEntityUpdateExpression.hasSetColumns()) {
//            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
//        }
//        if (!sqlEntityUpdateExpression.hasWhere()) {
//            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
//        }
//
//        //逻辑删除
//        PredicateSegment sqlWhere = buildWherePredicateSegment();
//
//        SqlBuilderSegment updateSet = buildSetSqlSegment(table);
//    }
//
//
//    protected void buildWherePredicateSegment() {
//
//
//       EasyQueryLambdaFactory easyQueryLambdaFactory = runtimeContext.getEasyQueryLambdaFactory();
//       SqlPredicate<Object> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), sqlEntityUpdateExpression, where);
//        buildPredicateLogicDelete(sqlPredicate);
//        buildPredicateInterceptor(sqlPredicate);
//        return sqlPredicateFilter(table, where);
//    }
//
//    public void entityExpressionCompile() {
//
//    }
//
//
//    /**
//     * 处理条件逻辑删除
//     */
//    protected void buildPredicateLogicDelete(SqlEntityTableExpression table, PredicateSegment originalPredicate,SqlPredicate<Object> sqlPredicate) {
//        if (!(table instanceof AnonymousEntityTableExpression)) {
//            boolean useLogicDelete = sqlExpressionContext.isUseLogicDelete() && entityMetadata.enableLogicDelete();
//            if (useLogicDelete) {
////                EasyQueryLambdaFactory easyQueryLambdaFactory = runtimeContext.getEasyQueryLambdaFactory();
////                SqlPredicate<Object> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), sqlEntityUpdateExpression, where);
//                SqlExpression<SqlPredicate<Object>> logicDeleteQueryFilterExpression = table.getLogicDeleteQueryFilterExpression();
//                logicDeleteQueryFilterExpression.apply(sqlPredicate);
//            }
//
//        }
//    }
//    protected void buildPredicateInterceptor(SqlPredicate<Object> sqlPredicate){
//        if (!(table instanceof AnonymousEntityTableExpression)) {
//            boolean useInterceptor = sqlExpressionContext.isUseInterceptor() && ArrayUtil.isNotEmpty(entityMetadata.getPredicateFilterInterceptors());
//            if (useInterceptor) {
//                List<String> predicateFilterInterceptors = entityMetadata.getPredicateFilterInterceptors();
//                EasyQueryConfiguration easyQueryConfiguration = runtimeContext.getEasyQueryConfiguration();
//                for (String predicateFilterInterceptor : predicateFilterInterceptors) {
//                    EasyPredicateFilterInterceptor globalSelectInterceptorStrategy = (EasyPredicateFilterInterceptor) easyQueryConfiguration.getGlobalInterceptor(predicateFilterInterceptor);
//                    if (globalSelectInterceptorStrategy != null) {
//                        globalSelectInterceptorStrategy.configure(table.entityClass(), sqlEntityUpdateExpression, sqlPredicate);
//                    }
//                }
//            }
//
//        }
//    }
//    protected void buildPredicateVersion(SqlPredicate<Object> sqlPredicate){
//
//        VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
//        Predicate predicate = null;
//        if (sqlEntityUpdateExpression.isExpression()) {
//            if (sqlExpressionContext.hasVersion()) {
//                Object version = sqlExpressionContext.getVersion();
//                if (Objects.isNull(version)) {
//                    sqlPredicate.isNull(EasyUtil.getPropertyLambda(table.entityClass()))
//                    predicate = new ColumnPredicate0(table, versionMetadata.getPropertyName(), SqlPredicateCompareEnum.IS_NULL, this);
//                } else {
//                    predicate = new ColumnValuePredicate0(table, versionMetadata.getPropertyName(), version, SqlPredicateCompareEnum.EQ, this);
//                }
//            }
//
//        } else {
//            predicate = new ColumnPropertyPredicate(table, versionMetadata.getPropertyName(), this);
//        }
//
//        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(predicate);
//        predicateSegment.addPredicateSegment(andPredicateSegment);
//    }
//
//    protected void sqlPredicateFilter() {
////        PredicateSegment originalPredicate
//        if (!(table instanceof AnonymousEntityTableExpression)) {
//            EntityMetadata entityMetadata = table.getEntityMetadata();
//            boolean useLogicDelete = sqlExpressionContext.isUseLogicDelete() && entityMetadata.enableLogicDelete();
//            boolean hasVersionColumn = entityMetadata.hasVersionColumn();
//            boolean useInterceptor = sqlExpressionContext.isUseInterceptor() && ArrayUtil.isNotEmpty(entityMetadata.getPredicateFilterInterceptors());
//            if (useLogicDelete || hasVersionColumn || useInterceptor) {
//                PredicateSegment predicateSegment = new AndPredicateSegment(true);
//                EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
//                SqlPredicate<Object> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);
//
//                if (useLogicDelete) {
//                    SqlExpression<SqlPredicate<Object>> logicDeleteQueryFilterExpression = table.getLogicDeleteQueryFilterExpression();
//                    logicDeleteQueryFilterExpression.apply(sqlPredicate);
//                }
//
//                if (hasVersionColumn) {
//                    VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
//                    Predicate predicate = null;
//                    if (isExpression()) {
//                        if (sqlExpressionContext.hasVersion()) {
//                            Object version = sqlExpressionContext.getVersion();
//                            if (Objects.isNull(version)) {
//                                predicate = new ColumnPredicate0(table, versionMetadata.getPropertyName(), SqlPredicateCompareEnum.IS_NULL, this);
//                            } else {
//                                predicate = new ColumnValuePredicate0(table, versionMetadata.getPropertyName(), version, SqlPredicateCompareEnum.EQ, this);
//                            }
//                        }
//
//                    } else {
//                        predicate = new ColumnPropertyPredicate(table, versionMetadata.getPropertyName(), this);
//                    }
//
//                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(predicate);
//                    predicateSegment.addPredicateSegment(andPredicateSegment);
//
//                }
//                if (useInterceptor) {
//                    List<String> predicateFilterInterceptors = entityMetadata.getPredicateFilterInterceptors();
//                    EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
//                    for (String predicateFilterInterceptor : predicateFilterInterceptors) {
//                        EasyPredicateFilterInterceptor globalSelectInterceptorStrategy = (EasyPredicateFilterInterceptor) easyQueryConfiguration.getGlobalInterceptor(predicateFilterInterceptor);
//                        if (globalSelectInterceptorStrategy != null) {
//                            globalSelectInterceptorStrategy.configure(table.entityClass(), this, sqlPredicate);
//                        }
//                    }
//                }
//
//                if (predicateSegment.isNotEmpty()) {
//                    if (originalPredicate != null && originalPredicate.isNotEmpty()) {
//                        predicateSegment.addPredicateSegment(originalPredicate);
//                    }
//                    return predicateSegment;
//                }
//            }
//        }
//        return originalPredicate;
//    }
//}
