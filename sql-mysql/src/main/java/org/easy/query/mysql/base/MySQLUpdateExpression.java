package org.easy.query.mysql.base;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnSelector;
import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import org.easy.query.core.query.EasySqlUpdateExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlExpressionContext;
import org.easy.query.core.util.ClassUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @FileName: MySQLUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:11
 * @Created by xuejiaming
 */
public class MySQLUpdateExpression extends EasySqlUpdateExpression {
    private final boolean isExpressionUpdate;

    public MySQLUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext);
        this.isExpressionUpdate = isExpressionUpdate;
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
        //如果没有指定where那么就使用主键作为更新条件
        if (!hasWhereColumns()) {
            SqlBuilderSegment whereColumns = getWhereColumns();
            EntityMetadata entityMetadata = table.getEntityMetadata();
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            if(keyProperties.isEmpty()){
                throw new EasyQueryException("对象:"+ ClassUtil.getSimpleName(entityMetadata.getEntityClass())+" 未找到主键信息");
            }
            for (String keyProperty : keyProperties) {
                whereColumns.append(new ColumnPropertyPredicate(table,keyProperty,this));
            }
        }
        //如果用户没有指定set的列,那么就是set所有列,并且要去掉主键部分
        if (getSetColumns().isEmpty()) {
            EasyQueryRuntimeContext runtimeContext =getRuntimeContext();
            EasyQueryLambdaFactory easyQueryLambdaFactory = runtimeContext.getEasyQueryLambdaFactory();
            SqlExpression<SqlColumnSelector<?>> selectExpression = ColumnSelector::columnAll;
            SqlColumnSelector<?> sqlColumnSetter = easyQueryLambdaFactory.createSqlColumnSetSelector(table.getIndex(), this, getSetColumns());
            selectExpression.apply(sqlColumnSetter);//获取set的值
            //非手动指定的那么需要移除where的那一部分
            List<SqlSegment> sqlSegments = getSetColumns().getSqlSegments();
            List<SqlSegment> whereSqlSegments = getWhereColumns().getSqlSegments();
            HashSet<String> whereProperties = new HashSet<>(whereSqlSegments.size());
            for (SqlSegment sqlSegment : whereSqlSegments) {
                if(sqlSegment instanceof ColumnPropertyPredicate){
                    whereProperties.add(((ColumnPropertyPredicate)sqlSegment).getPropertyName());
                }
            }
            int size = sqlSegments.size();
            int offsetIndex=0;
            for (int i = 0; i < size; i++) {
                int realIndex = i - offsetIndex;
                SqlSegment sqlSegment = sqlSegments.get(realIndex);
                if(sqlSegment instanceof ColumnPropertyPredicate){
                    String propertyName = ((ColumnPropertyPredicate) sqlSegment).getPropertyName();
                    if(whereProperties.contains(propertyName)){
//                        getParameters().remove(realIndex);
                        sqlSegments.remove(realIndex);
                        offsetIndex++;
                    }
                }

            }
        }
        if (!hasWhereColumns()) {
            throw new EasyQueryException("更新需要指定条件列");
        }
        String tableName = table.getEntityMetadata().getTableName();
        return "UPDATE " + tableName + " SET " + getSetColumns().toSql() + " WHERE " +
                getWhereColumns().toSql();
    }
}
