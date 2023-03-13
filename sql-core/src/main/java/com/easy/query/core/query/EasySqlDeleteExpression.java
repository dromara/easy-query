package com.easy.query.core.query;

import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.interceptor.delete.GlobalDeleteInterceptorStrategy;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.List;

/**
 * @FileName: EasySqlDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:32
 * @Created by xuejiaming
 */
public abstract class EasySqlDeleteExpression extends AbstractSqlEntityExpression implements SqlEntityDeleteExpression {
    protected final PredicateSegment where;
    protected final boolean expressionDelete;
    protected SqlBuilderSegment whereColumns;

    public EasySqlDeleteExpression(SqlExpressionContext sqlExpressionContext, boolean expressionDelete) {
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
        SqlEntityTableExpression table = getTables().get(0);
        String tableName = table.getEntityMetadata().getTableName();
        UpdateSetSqlBuilderSegment updateSetSqlBuilderSegment = getUpdateSetSqlBuilderSegment(table);
        //逻辑删除
        if (updateSetSqlBuilderSegment != null) {

            sql = new StringBuilder("UPDATE ").append(tableName);
            sql.append(" SET ").append(updateSetSqlBuilderSegment.toSql());//生成的表达式带有参数会传入到上下文
        } else {
            if (sqlExpressionContext.isDeleteThrow()) {
                throw new EasyQueryException("can't execute delete statement");
            }
            sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName);
        }
        sql.append(" WHERE ");
        PredicateSegment where = getSqlPredicateSegment(table, getWhere());
        sql.append(where.toSql());
        return sql.toString();
    }
    private PredicateSegment getSqlPredicateSegment(SqlEntityTableExpression table, PredicateSegment originalPredicate) {

        EntityMetadata entityMetadata = table.getEntityMetadata();
        List<String> deleteInterceptors = entityMetadata.getDeleteInterceptors();
        boolean useInterceptor = !deleteInterceptors.isEmpty() && sqlExpressionContext.isUseInterceptor();
        if(useInterceptor){
            PredicateSegment predicateSegment = new AndPredicateSegment(true);
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            SqlPredicate<?> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);
                EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
                for (String deleteInterceptor : deleteInterceptors) {
                    GlobalDeleteInterceptorStrategy globalDeleteInterceptorStrategy = (GlobalDeleteInterceptorStrategy)easyQueryConfiguration.getGlobalInterceptorStrategy(deleteInterceptor);
                    if(globalDeleteInterceptorStrategy!=null){
                        globalDeleteInterceptorStrategy.configure(table.entityClass(),this,sqlPredicate);
                    }
                }

            if(predicateSegment.isNotEmpty()){
                if (originalPredicate != null && originalPredicate.isNotEmpty()) {
                    predicateSegment.addPredicateSegment(originalPredicate);
                }
                return predicateSegment;
            }
        }
        return originalPredicate;
    }

    private UpdateSetSqlBuilderSegment getUpdateSetSqlBuilderSegment(SqlEntityTableExpression table) {
        EntityMetadata entityMetadata = table.getEntityMetadata();
        boolean useLogicDelete = entityMetadata.enableLogicDelete() && sqlExpressionContext.isUseLogicDelete();
        if (useLogicDelete) {
            SqlExpression<SqlColumnSetter<?>> logicDeletedSqlExpression = table.getLogicDeletedSqlExpression();
            if (logicDeletedSqlExpression != null) {
                EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
                UpdateSetSqlBuilderSegment setSqlSegmentBuilder = new UpdateSetSqlBuilderSegment();
                SqlColumnSetter<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetter(table.getIndex(), this, setSqlSegmentBuilder);
                logicDeletedSqlExpression.apply(sqlColumnSetter);//获取set的值
                return setSqlSegmentBuilder;
            }
        }
        return null;

    }

    /**
     * 后续代码合并抽离优化
     *
     * @return
     */
    protected String entityDeleteSql() {

        //如果没有指定where那么就使用主键作为更新条件
        SqlBuilderSegment whereColumns = getWhereColumns();

        SqlEntityTableExpression table = getTables().get(0);
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if (keyProperties.isEmpty()) {
            throw new EasyQueryException("entity:" + ClassUtil.getSimpleName(table.getEntityMetadata().getEntityClass()) + "  not found primary key properties");
        }
        for (String keyProperty : keyProperties) {
            whereColumns.append(new ColumnPropertyPredicate(table, keyProperty, this));
        }
        if (!hasWhereColumns()) {
            throw new EasyQueryException("'DELETE' statement without 'WHERE' clears all data in the table");
        }

        StringBuilder sql = null;
        String tableName = table.getEntityMetadata().getTableName();
        UpdateSetSqlBuilderSegment updateSetSqlBuilderSegment = getUpdateSetSqlBuilderSegment(table);
        //逻辑删除
        if (updateSetSqlBuilderSegment != null) {
            sql = new StringBuilder("UPDATE ").append(tableName);
            sql.append(" SET ").append(updateSetSqlBuilderSegment.toSql());//生成的表达式带有参数会传入到上下文
        } else {
            if (sqlExpressionContext.isDeleteThrow()) {
                throw new EasyQueryException("can't execute delete statement");
            }
            sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName);
        }
        sql.append(" WHERE ");
        sql.append(getWhereColumns().toSql());
        return sql.toString();
    }

}
