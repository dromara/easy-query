package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: EasySqlUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:05
 * @Created by xuejiaming
 */
public abstract class EasySqlUpdateExpression extends AbstractSqlEntityExpression implements SqlEntityUpdateExpression{

    protected final boolean isExpressionUpdate;
    private  SqlBuilderSegment setColumns;
    private  PredicateSegment where;
    private SqlBuilderSegment setIgnoreColumns;
    private SqlBuilderSegment whereColumns;
    public EasySqlUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext);
        this.isExpressionUpdate = isExpressionUpdate;
    }

    @Override
    public SqlBuilderSegment getSetColumns() {
        if(setColumns==null){
            setColumns=new UpdateSetSqlBuilderSegment();
        }
        return setColumns;
    }

    @Override
    public boolean hasSetColumns() {
        return setColumns!=null&&setColumns.isNotEmpty();
    }

    @Override
    public boolean hasWhere() {
        return where!=null&&where.isNotEmpty();
    }

    @Override
    public PredicateSegment getWhere() {
        if(where==null)
        {
            where=new AndPredicateSegment(true);
        }
        return where;
    }

    @Override
    public SqlBuilderSegment getSetIgnoreColumns() {
        if(setIgnoreColumns==null){
            setIgnoreColumns=new UpdateSetSqlBuilderSegment();
        }
        return setIgnoreColumns;
    }

    @Override
    public boolean hasSetIgnoreColumns() {
        return setIgnoreColumns!=null&&setIgnoreColumns.isNotEmpty();
    }


    @Override
    public SqlBuilderSegment getWhereColumns() {
        if(whereColumns==null){
            whereColumns=new ProjectSqlBuilderSegment();
        }
        return whereColumns;
    }
    @Override
    public boolean hasWhereColumns() {
        return whereColumns!=null&&whereColumns.isNotEmpty();
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
        if(isExpressionUpdate){
            return expressionUpdate();
        }else{
            return entityUpdate();
        }
    }
    private String expressionUpdate(){

        if (!hasSetColumns()) {
            throw new EasyQueryException("更新需要设置更新列");
        }
        if (!hasWhere()) {
            throw new EasyQueryException("更新需要设置条件");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        SqlEntityTableExpression table = getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" SET ").append(getSetColumns().toSql());
        sql.append(" WHERE ").append(getWhere().toSql());
        return sql.toString();
    }
    private String entityUpdate(){
        SqlEntityTableExpression table = getTable(0);

        if (!hasWhereColumns()) {
            throw new EasyQueryException("更新需要指定条件列");
        }
        String tableName = table.getEntityMetadata().getTableName();
        return "UPDATE " + tableName + " SET " + getSetColumns().toSql() + " WHERE " +
                getWhereColumns().toSql();
    }
}
