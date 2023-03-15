package com.easy.query.core.query;

import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
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
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;

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
            return entityUpdate();
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
        PredicateSegment sqlWhere = buildWherePredicateSegment(table);

        SqlBuilderSegment updateSet = buildSetSqlSegment(table);
        return internalToSql(table,updateSet,sqlWhere);
    }

    protected String entityUpdate() {
        SqlEntityTableExpression table = getTable(0);
        buildEntityPredicateSegmentOnce(table);
        PredicateSegment sqlWhere = buildWherePredicateSegment(table);
        buildEntitySetColumns(table,sqlWhere);
        if (getSetColumns().isEmpty()) {
            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
        }
        return internalToSql(table,getSetColumns(),sqlWhere);
    }
    private String internalToSql(SqlEntityTableExpression table,SqlBuilderSegment updateSet,PredicateSegment sqlWhere){

        EntityMetadata entityMetadata = table.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        return "UPDATE " + tableName + " SET " +updateSet.toSql() + " WHERE " +
                sqlWhere.toSql();
    }

    protected SqlBuilderSegment buildSetSqlSegment(SqlEntityTableExpression table){
        EntityMetadata entityMetadata = table.getEntityMetadata();
        SqlBuilderSegment updateSet = getSetColumns().cloneSqlBuilder();
        //如果更新拦截器不为空
        if(getSqlExpressionContext().isUseInterceptor()&&ArrayUtil.isNotEmpty(entityMetadata.getUpdateSetInterceptors())){
            EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
            SqlColumnSetter<Object> sqlColumnSetter = getRuntimeContext().getEasyQueryLambdaFactory().createSqlColumnSetter(0, this, updateSet);
            for (String updateSetInterceptor : entityMetadata.getUpdateSetInterceptors()) {
                GlobalUpdateSetInterceptor globalInterceptor = (GlobalUpdateSetInterceptor)easyQueryConfiguration.getGlobalInterceptor(updateSetInterceptor);
                globalInterceptor.configure(table.getClass(),this,sqlColumnSetter);

            }
        }
        return updateSet;
    }

    protected void buildEntityPredicateSegmentOnce(SqlEntityTableExpression table){

        //如果没有指定where那么就使用主键作为更新条件只构建一次where
        if (!hasWhere()) {
            PredicateSegment wherePredicate = getWhere();
            if (hasWhereColumns()) {
                for (SqlSegment sqlSegment : whereColumns.getSqlSegments()) {
                    if (!(sqlSegment instanceof SqlEntitySegment)) {
                        throw new EasyQueryException("where 表达式片段不是SqlEntitySegment");
                    }
                    SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, sqlEntitySegment.getPropertyName(), this));
                    wherePredicate.addPredicateSegment(andPredicateSegment);
                }
            } else {
                EntityMetadata entityMetadata = table.getEntityMetadata();
                Collection<String> keyProperties = entityMetadata.getKeyProperties();
                if (keyProperties.isEmpty()) {
                    throw new EasyQueryException("entity:" + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " not found primary key properties");
                }
                for (String keyProperty : keyProperties) {
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, keyProperty, this));
                    wherePredicate.addPredicateSegment(andPredicateSegment);
                }
            }
        }
    }
    protected PredicateSegment buildWherePredicateSegment(SqlEntityTableExpression table) {
        PredicateSegment wherePredicate = getWhere();
        if (wherePredicate.isEmpty()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        return sqlPredicateFilter(table, wherePredicate);
    }
    protected void buildEntitySetColumns(SqlEntityTableExpression table,PredicateSegment sqlWhere){

        //如果用户没有指定set的列,那么就是set所有列,并且要去掉主键部分
        if (!hasSetColumns()) {
            Class<?> entityClass = table.entityClass();
            EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
            EasyQueryLambdaFactory easyQueryLambdaFactory = runtimeContext.getEasyQueryLambdaFactory();
            SqlExpression<SqlColumnSelector<?>> selectExpression = ColumnSelector::columnAll;
            SqlColumnSelector<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetSelector(table.getIndex(), this, getSetColumns());
            selectExpression.apply(sqlColumnSetter);//获取set的值
            //非手动指定的那么需要移除where的那一部分
            PredicateIndex predicateIndex = sqlWhere.buildPredicateIndex();
            getSetColumns().getSqlSegments().removeIf(o -> {
                String propertyName = ((SqlEntitySegment) o).getPropertyName();
                return predicateIndex.contains(entityClass, propertyName);
            });
        }
    }

}
