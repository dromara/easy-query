package org.easy.query.mysql.base;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import org.easy.query.core.query.EasySqlDeleteExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlExpressionContext;
import org.easy.query.core.util.ClassUtil;

import java.util.Collection;

/**
 * @FileName: MySQLDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:36
 * @Created by xuejiaming
 */
public class MySQLDeleteExpression extends EasySqlDeleteExpression {

    private final boolean expressionDelete;

    public MySQLDeleteExpression(SqlExpressionContext queryExpressionContext, boolean expressionDelete) {
        super(queryExpressionContext);
        this.expressionDelete = expressionDelete;
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
        if(expressionDelete){
            return expressionDeleteSql();
        }
        else {
            return entityDeleteSql();
        }
    }
    private String expressionDeleteSql(){


        if (!hasWhere()) {
            throw new EasyQueryException("'Delete' statement without 'where' clears all data in the table");
        }
        if(getTables().isEmpty()){
            throw new EasyQueryException("删除请先添加表信息");
        }

        StringBuilder sql;
        SqlEntityTableExpression table = getTables().get(0);
        String tableName = table.getEntityMetadata().getTableName();
        SqlExpression<SqlColumnSetter<?>> deletedSqlExpression = table.getDeletedSqlExpression();
        //逻辑删除
        if (deletedSqlExpression != null) {
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            UpdateSetSqlBuilderSegment setSqlSegmentBuilder = new UpdateSetSqlBuilderSegment();
            SqlColumnSetter<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetter(table.getIndex(), this, setSqlSegmentBuilder);
            deletedSqlExpression.apply(sqlColumnSetter);//获取set的值

            sql = new StringBuilder("UPDATE ").append(tableName);
            sql.append(" SET ").append(setSqlSegmentBuilder.toSql());//生成的表达式带有参数会传入到上下文
        } else {
            sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName);
        }
        sql.append(" WHERE ");
        sql.append(getWhere().toSql());
        return sql.toString();
    }
    private String entityDeleteSql(){

        //如果没有指定where那么就使用主键作为更新条件
        SqlBuilderSegment whereColumns = getWhereColumns();

        SqlEntityTableExpression table = getTables().get(0);
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if(keyProperties.isEmpty()){
            throw new EasyQueryException("对象:"+ ClassUtil.getSimpleName(table.getEntityMetadata().getEntityClass())+" 未找到主键信息");
        }
        for (String keyProperty : keyProperties) {
            whereColumns.append(new ColumnPropertyPredicate(table, keyProperty,this));
        }
        if (!hasWhereColumns()) {
            throw new EasyQueryException("'Delete' statement without 'where' clears all data in the table");
        }

        StringBuilder sql = null;
        String tableName = table.getEntityMetadata().getTableName();
        SqlExpression<SqlColumnSetter<?>> deletedSqlExpression = table.getDeletedSqlExpression();
        //逻辑删除
        if (deletedSqlExpression != null) {
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            UpdateSetSqlBuilderSegment setSqlSegmentBuilder = new UpdateSetSqlBuilderSegment();
            SqlColumnSetter<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetter(table.getIndex(), this, setSqlSegmentBuilder);
            deletedSqlExpression.apply(sqlColumnSetter);//获取set的值

            sql = new StringBuilder("UPDATE ").append(tableName);
            sql.append(" SET ").append(setSqlSegmentBuilder.toSql());//生成的表达式带有参数会传入到上下文
        } else {
            sql = new StringBuilder("DELETE FROM ");
            sql.append(tableName);
        }
        sql.append(" WHERE ");
        sql.append(getWhereColumns().toSql());
        return sql.toString();
    }
}
