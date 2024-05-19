package com.easy.query.mssql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.ExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.SQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/8/1 09:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLRowNumberQuerySQLExpression extends MsSQLQuerySQLExpression {
    public MsSQLRowNumberQuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
    }

    /**
     * 分页部分代码参考 <a href="https://github.com/dotnetcore/FreeSql">FreeSQL</a>
     *
     * @param toSQLContext
     * @return
     */
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        boolean root = EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        if(root){
            if(entitySQLExpressionMetadata.getExpressionContext().hasDeclareExpressions()){
                StringBuilder sb = new StringBuilder();
                List<ExpressionBuilder> declareExpressions = entitySQLExpressionMetadata.getExpressionContext().getDeclareExpressions();
                for (ExpressionBuilder declareExpression : declareExpressions) {
                    SQLExpression expression = declareExpression.toExpression();
                    String sql = expression.toSQL(toSQLContext);
                    sb.append(sql).append(" ");
                }
                sb.append(toSQL0(true, toSQLContext));
                return sb.toString();
            }
        }
        return toSQL0(root, toSQLContext);

    }
    protected String toSQL0(boolean root,ToSQLContext toSQLContext){
        StringBuilder sql = new StringBuilder("SELECT ");
        if (this.distinct) {
            sql.append("DISTINCT ");
        }

        boolean useTop = offset <= 0 && rows > 0;
        if (useTop) {
            sql.append("TOP ").append(rows).append(" ");
        }
        sql.append(this.projects.toSQL(toSQLContext));


        Iterator<EntityTableSQLExpression> iterator = getTables().iterator();
        EntityTableSQLExpression firstTable = iterator.next();


        boolean hasOrderBy = this.order != null && this.order.isNotEmpty();
        boolean hasGroupBy = this.group != null && this.group.isNotEmpty();
        if (rows > 0 || offset > 0) {
            if (offset > 0) {// 注意这个判断，大于 0 才使用 ROW_NUMBER ，否则属于第一页直接使用 TOP
                sql.append(", ROW_NUMBER() OVER(");
                if (!hasOrderBy) {//top 1 不自动 order by
                    if (rows > 1 ) {
                        if (hasGroupBy) {
                            sql.append(" ORDER BY ").append(this.group.toSQL(toSQLContext));
                        } else {
                            SQLBuilderSegment columnOrder = getPrimaryKeyOrFirstColumnOrder(firstTable.getEntityTable());
                            if(columnOrder!=null){
                                sql.append(" ORDER BY ").append(columnOrder.toSQL(toSQLContext));
                            }else {
                                throw new EasyQueryInvalidOperationException("the pagination must include the 'order by' clause.");
                            }
                        }
                    }
                } else {
                    sql.append(" ORDER BY ").append(this.order.toSQL(toSQLContext));
                }
                sql.append(" ) AS ").append(KeywordTool.ROW_NUM);
            }
        }

        sql.append(firstTable.toSQL(toSQLContext));
        while (iterator.hasNext()) {
            EntityTableSQLExpression table = iterator.next();
            sql.append(table.toSQL(toSQLContext));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

            PredicateSegment on = table.getOn();
            if (on != null && on.isNotEmpty()) {
                sql.append(" ON ").append(on.toSQL(toSQLContext));
            }
        }
        boolean hasWhere = EasySQLSegmentUtil.isNotEmpty(this.where);
        if (hasWhere) {
            String whereSQL = this.where.toSQL(toSQLContext);
            sql.append(" WHERE ").append(whereSQL);
        }
        if (hasGroupBy) {
            sql.append(" GROUP BY ").append(this.group.toSQL(toSQLContext));
            if (this.having != null && this.having.isNotEmpty()) {
                sql.append(" HAVING ").append(this.having.toSQL(toSQLContext));
            }
        }

        if (offset <= 0) {
            if (hasOrderBy) {
                sql.append(" ORDER BY ").append(this.order.toSQL(toSQLContext));
            }
        } else {
            sql.insert(0, "WITH rt AS ( ").append(" ) SELECT rt.* FROM rt where rt.__rownum__");
            if (rows > 0) {
                sql.append(" BETWEEN ").append(offset + 1).append(" AND ").append(offset + rows);
            } else {
                sql.append(" > ").append(offset);
            }
        }

        return sql.toString();
    }
}
