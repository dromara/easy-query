package com.easy.query.core.query;

import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.abstraction.metadata.LogicDeleteMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.List;

/**
 * @FileName: EasySqlUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:05
 * @Created by xuejiaming
 */
public abstract class EasySqlUpdateExpression extends AbstractSqlEntityExpression implements SqlEntityUpdateExpression {

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
        EntityMetadata entityMetadata = table.getEntityMetadata();
        //逻辑删除
        PredicateSegment sqlWhere = getSqlWhereLogicDelete(entityMetadata, where);

        String tableName = table.getEntityMetadata().getTableName();
        return "UPDATE " + tableName + " SET " + setColumns.toSql() + " WHERE " + sqlWhere.toSql();
    }

    protected String entityUpdate() {
        SqlEntityTableExpression table = getTable(0);
        EntityMetadata entityMetadata = table.getEntityMetadata();

        PredicateSegment wherePredicate =  getWhere();
//如果没有指定where那么就使用主键作为更新条件只构建一次where
        if(!hasWhere()){
            if (!hasWhereColumns()) {
                Collection<String> keyProperties = entityMetadata.getKeyProperties();
                if (keyProperties.isEmpty()) {
                    throw new EasyQueryException("entity:" + ClassUtil.getSimpleName(entityMetadata.getEntityClass()) + " not found primary key properties");
                }
                for (String keyProperty : keyProperties) {
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, keyProperty, this));
                    wherePredicate.addPredicateSegment(andPredicateSegment);
                }
            }
            else {
                for (SqlSegment sqlSegment : whereColumns.getSqlSegments()) {
                    if (!(sqlSegment instanceof SqlEntitySegment)) {
                        throw new EasyQueryException("where 表达式片段不是SqlEntitySegment");
                    }
                    SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                    AndPredicateSegment andPredicateSegment = new AndPredicateSegment(new ColumnPropertyPredicate(table, sqlEntitySegment.getPropertyName(), this));
                    wherePredicate.addPredicateSegment(andPredicateSegment);
                }
            }
        }
        if (wherePredicate.isEmpty()) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }
        PredicateSegment sqlWhere = getSqlWhereLogicDelete(entityMetadata, wherePredicate);


        //如果用户没有指定set的列,那么就是set所有列,并且要去掉主键部分

        if (!hasSetColumns()) {
            Class<?> entityClass = table.entityClass();
            EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
            EasyQueryLambdaFactory easyQueryLambdaFactory = runtimeContext.getEasyQueryLambdaFactory();
            SqlExpression<SqlColumnSelector<?>> selectExpression = ColumnSelector::columnAll;
            SqlColumnSelector<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetSelector(table.getIndex(), this, getSetColumns());
            selectExpression.apply(sqlColumnSetter);//获取set的值
            //非手动指定的那么需要移除where的那一部分
            List<SqlSegment> sqlSegments = getSetColumns().getSqlSegments();
            sqlSegments.removeIf(o -> {
                String propertyName = ((SqlEntitySegment) o).getPropertyName();
                return sqlWhere.contains(entityClass,propertyName);
            });
        }

        if (getSetColumns().isEmpty()) {
            throw new EasyQueryException("'UPDATE' statement without 'SET' execute wrong");
        }
        String tableName = entityMetadata.getTableName();
        return "UPDATE " + tableName + " SET " + getSetColumns().toSql() + " WHERE " +
                sqlWhere.toSql();
    }
    protected PredicateSegment getSqlWhereLogicDelete(EntityMetadata entityMetadata, PredicateSegment originalWhere){
        if(entityMetadata.enableLogicDelete()){
            AndPredicateSegment where = new AndPredicateSegment(true);
            LogicDeleteMetadata logicDeleteMetadata = entityMetadata.getLogicDeleteMetadata();
            SqlExpression<SqlPredicate<?>> logicDeleteQueryFilterExpression = logicDeleteMetadata.getLogicDeleteQueryFilterExpression();
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            SqlPredicate<Object> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(this, where);
            logicDeleteQueryFilterExpression.apply(sqlPredicate);

            if(where.isNotEmpty()){
                if (originalWhere != null && originalWhere.isNotEmpty()) {
                    where.addPredicateSegment(originalWhere);
                }
                return where;
            }
        }
        return originalWhere;
    }
}
