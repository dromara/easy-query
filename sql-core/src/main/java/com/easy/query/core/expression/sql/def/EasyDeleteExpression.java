package com.easy.query.core.expression.sql.def;

import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnVersionPropertyPredicate;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.internal.AbstractPredicateEntityExpression;
import com.easy.query.core.expression.sql.EntityDeleteExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.metadata.VersionMetadata;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;

/**
 * @FileName: EasySqlDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:32
 * @author xuejiaming
 */
public abstract class EasyDeleteExpression extends AbstractPredicateEntityExpression implements EntityDeleteExpression {
    protected final PredicateSegment where;
    protected final boolean expressionDelete;
    protected SqlBuilderSegment whereColumns;

    public EasyDeleteExpression(ExpressionContext sqlExpressionContext, boolean expressionDelete) {
        super(sqlExpressionContext);
        this.expressionDelete = expressionDelete;
        this.where = new AndPredicateSegment(true);
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }

    @Override
    public boolean hasWhere() {
        return where.isNotEmpty();
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
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        if (expressionDelete) {
            return expressionDeleteSql();
        } else {
            return entityDeleteSql();
        }
    }

    protected String expressionDeleteSql() {
        if (!hasWhere()) {
            throw new EasyQueryException("'DELETE' statement without 'WHERE' clears all data in the table");
        }
        if (getTables().isEmpty()) {
            throw new EasyQueryException("not table found for delete");
        }

        StringBuilder sql;
        EntityTableExpression table = getTables().get(0);
        EntityMetadata entityMetadata = table.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        UpdateSetSqlBuilderSegment updateSetSqlBuilderSegment = getUpdateSetSqlBuilderSegment(table);

        //逻辑删除
        if (updateSetSqlBuilderSegment != null) {

            sql = new StringBuilder("UPDATE ").append(tableName);
            sql.append(" SET ").append(updateSetSqlBuilderSegment.toSql());//生成的表达式带有参数会传入到上下文
        } else {
            if (sqlExpressionContext.isDeleteThrow()) {
                throw new EasyQueryInvalidOperationException("can't execute delete statement");
            }
            sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName);
        }
        sql.append(" WHERE ");
        PredicateSegment where = buildWherePredicateSegment(table);
        sql.append(where.toSql());
        return sql.toString();
    }
//    protected PredicateSegment getSqlWhereLogicDeleteFilter(EntityMetadata entityMetadata, PredicateSegment originalWhere){
//        boolean logicDelete = entityMetadata.enableLogicDelete() && sqlExpressionContext.isUseLogicDelete();
//        boolean useInterceptor = ArrayUtil.isNotEmpty(entityMetadata.getPredicateFilterInterceptors()) && sqlExpressionContext.isUseInterceptor();
//        if(logicDelete||useInterceptor){
//            AndPredicateSegment where = new AndPredicateSegment(true);
//            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
//            SqlPredicate<Object> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(this, where);
//            if(logicDelete){
//                SqlExpression<SqlPredicate<Object>> logicDeleteQueryFilterExpression = entityMetadata.getLogicDeleteMetadata().getLogicDeleteQueryFilterExpression();
//                logicDeleteQueryFilterExpression.apply(sqlPredicate);
//            }
//
//            if(useInterceptor){
//                List<String> predicateFilterInterceptors = entityMetadata.getPredicateFilterInterceptors();
//                EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
//                for (String predicateFilterInterceptor : predicateFilterInterceptors) {
//                    GlobalPredicateFilterInterceptor globalSelectInterceptorStrategy = (GlobalPredicateFilterInterceptor)easyQueryConfiguration.getGlobalInterceptor(predicateFilterInterceptor);
//                    if(globalSelectInterceptorStrategy!=null){
//                        globalSelectInterceptorStrategy.configure(entityMetadata.getEntityClass(),this,sqlPredicate);
//                    }
//                }
//            }
//            if(where.isNotEmpty()){
//                if (originalWhere != null && originalWhere.isNotEmpty()) {
//                    where.addPredicateSegment(originalWhere);
//                }
//                return where;
//            }
//        }
//        return originalWhere;
//    }
//    private PredicateSegment getSqlPredicateSegment(SqlEntityTableExpression table, PredicateSegment originalPredicate) {
//
//        EntityMetadata entityMetadata = table.getEntityMetadata();
//        List<String> deleteInterceptors = entityMetadata.getDeleteInterceptors();
//        boolean useInterceptor = !deleteInterceptors.isEmpty() && sqlExpressionContext.isUseInterceptor();
//        if(useInterceptor){
//            PredicateSegment predicateSegment = new AndPredicateSegment(true);
//            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
//            SqlPredicate<?> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);
//                EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
//                for (String deleteInterceptor : deleteInterceptors) {
//                    GlobalDeleteInterceptorStrategy globalDeleteInterceptorStrategy = (GlobalDeleteInterceptorStrategy)easyQueryConfiguration.getGlobalInterceptorStrategy(deleteInterceptor);
//                    if(globalDeleteInterceptorStrategy!=null){
//                        globalDeleteInterceptorStrategy.configure(table.entityClass(),this,sqlPredicate);
//                    }
//                }
//
//            if(predicateSegment.isNotEmpty()){
//                if (originalPredicate != null && originalPredicate.isNotEmpty()) {
//                    predicateSegment.addPredicateSegment(originalPredicate);
//                }
//                return predicateSegment;
//            }
//        }
//        return originalPredicate;
//    }

    private UpdateSetSqlBuilderSegment getUpdateSetSqlBuilderSegment(EntityTableExpression table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        boolean useLogicDelete = entityMetadata.enableLogicDelete() && sqlExpressionContext.getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        if (useLogicDelete) {
            SqlExpression<SqlColumnSetter<Object>> logicDeletedSqlExpression = table.getLogicDeletedSqlExpression();
            if (logicDeletedSqlExpression != null) {
                UpdateSetSqlBuilderSegment setSqlSegmentBuilder = new UpdateSetSqlBuilderSegment();
                EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
                SqlColumnSetter<Object> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetter(table.getIndex(), this, setSqlSegmentBuilder);
                logicDeletedSqlExpression.apply(sqlColumnSetter);//获取set的值
                //todo 非表达式添加行版本信息
                if(!isExpression()){
                    if(entityMetadata.hasVersionColumn()){
                        VersionMetadata versionMetadata = entityMetadata.getVersionMetadata();
                        Class<? extends EasyVersionStrategy> versionStrategy = versionMetadata.getVersionStrategy();
                        EasyVersionStrategy easyVersionStrategy = getRuntimeContext().getEasyQueryConfiguration().getEasyVersionStrategyNotNull(versionStrategy);
                        setSqlSegmentBuilder.append(new ColumnVersionPropertyPredicate(table, versionMetadata.getPropertyName(),easyVersionStrategy,this));
                    }
                }
                return setSqlSegmentBuilder;
            }
        }
        return null;
    }

    protected PredicateSegment buildWherePredicateSegment(EntityTableExpression table){
        EntityMetadata entityMetadata = table.getEntityMetadata();

        PredicateSegment wherePredicate =  getWhere();
        if(!expressionDelete){

            //如果没有指定where那么就使用主键作为更新条件只构建一次where
            if(!hasWhere()){
                if (hasWhereColumns()) {
                    for (SqlSegment sqlSegment : whereColumns.getSqlSegments()) {
                        if (!(sqlSegment instanceof SqlEntitySegment)) {
                            throw new EasyQueryException("where 表达式片段不是SqlEntitySegment");
                        }
                        SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, sqlEntitySegment.getPropertyName(), this));
                        wherePredicate.addPredicateSegment(andPredicateSegment);
                    }
                }
                else {
                    //如果没有指定where那么就使用主键作为更新条件
                    Collection<String> keyProperties = entityMetadata.getKeyProperties();
                    if (keyProperties.isEmpty()) {
                        throw new EasyQueryException("entity:" + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + "  not found primary key properties");
                    }
                    for (String keyProperty : keyProperties) {
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, keyProperty, this));
                        wherePredicate.addPredicateSegment(andPredicateSegment);
                    }
                }
            }
        }
        if (wherePredicate.isEmpty()) {
            throw new EasyQueryException("'DELETE' statement without 'WHERE'");
        }
        return sqlPredicateFilter(table, wherePredicate);

    }

    @Override
    public boolean isExpression() {
        return expressionDelete;
    }

    @Override
    protected boolean hasVersionColumn(EntityMetadata entityMetadata) {
        return entityMetadata.hasVersionColumn()&&!hasWhereColumns();
    }

    /**
     * 后续代码合并抽离优化
     *
     * @return
     */
    protected String entityDeleteSql() {


        EntityTableExpression table = getTables().get(0);
        EntityMetadata entityMetadata = table.getEntityMetadata();
        PredicateSegment sqlWhere = buildWherePredicateSegment(table);

        StringBuilder sql = null;
        String tableName = entityMetadata.getTableName();
        UpdateSetSqlBuilderSegment updateSetSqlBuilderSegment = getUpdateSetSqlBuilderSegment(table);
        //逻辑删除
        if (updateSetSqlBuilderSegment != null) {

            sql = new StringBuilder("UPDATE ").append(tableName);
            sql.append(" SET ").append(updateSetSqlBuilderSegment.toSql());//生成的表达式带有参数会传入到上下文
        } else {
            if (sqlExpressionContext.isDeleteThrow()) {
                throw new EasyQueryInvalidOperationException("can't execute delete statement");
            }
            sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName);
        }
        sql.append(" WHERE ");
        sql.append(sqlWhere.toSql());
        return sql.toString();
    }

}
