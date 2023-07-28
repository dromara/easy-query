package com.easy.query.dameng.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpressionImpl;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Iterator;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengQuerySQLExpression extends QuerySQLExpressionImpl {
    public DamengQuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
    }


    /**
     * 分页部分代码参考 FreeSQL https://github.com/dotnetcore/FreeSql
     * @param toSQLContext
     * @return
     */
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        boolean root = EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        StringBuilder sql = new StringBuilder("SELECT ");
        if (this.distinct) {
            sql.append("DISTINCT ");
        }

        sql.append(this.projects.toSQL(toSQLContext));
        boolean hasOrderBy = this.order != null && this.order.isNotEmpty();
        if (!hasOrderBy && offset > 0) {
            sql.append(", ROWNUM AS \""+ KeywordTool.ROW_NUM +"\"");
        }

        Iterator<EntityTableSQLExpression> iterator = getTables().iterator();
        EntityTableSQLExpression firstTable = iterator.next();
        sql.append(firstTable.toSQL(toSQLContext));
        while (iterator.hasNext()) {
            EntityTableSQLExpression table = iterator.next();
            sql.append(table.toSQL(toSQLContext));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

            PredicateSegment on = table.getOn();
            if (on != null && on.isNotEmpty()) {
                sql.append(" ON ").append(on.toSQL(toSQLContext));
            }
        }
        boolean notExistsSQL = EasySQLSegmentUtil.isNotEmpty(this.allPredicate);
        boolean hasWhere = EasySQLSegmentUtil.isNotEmpty(this.where);
        if (hasWhere) {
            String whereSQL = this.where.toSQL(toSQLContext);
            sql.append(" WHERE ");
            if (root && notExistsSQL) {
                sql.append("( ").append(whereSQL).append(" )");
            } else {
                sql.append(whereSQL);
            }
        }
        if (!hasOrderBy && (offset > 0 || rows > 0)) {
            if(!hasWhere){
                sql.append(" WHERE ROWNUM < ");
            }else{
                sql.append(" AND ROWNUM < ");
            }
            sql.append(offset+rows+1);
        }
        boolean onlyWhere = true;
        if (this.group != null && this.group.isNotEmpty()) {
            onlyWhere = false;
            sql.append(" GROUP BY ").append(this.group.toSQL(toSQLContext));
        }
        if (this.having != null && this.having.isNotEmpty()) {
            onlyWhere = false;
            sql.append(" HAVING ").append(this.having.toSQL(toSQLContext));
        }
        if (hasOrderBy) {
            onlyWhere = false;
            sql.append(" ORDER BY ").append(this.order.toSQL(toSQLContext));
        }

        if(!hasOrderBy){
            if(offset>0){
                sql.insert(0,"SELECT rt.* FROM(").append(") rt WHERE rt.\""+KeywordTool.ROW_NUM+"\" > ").append(offset);
            }
        }else{
            if(offset>0&&rows>0){
                sql.insert(0,"SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \""+KeywordTool.ROW_NUM+"\" FROM (")
                        .append(") rt WHERE ROWNUM < ").append(offset+rows+1)
                        .append(") rt1 WHERE rt1.\""+KeywordTool.ROW_NUM+"\" > ").append(offset);
            }else if(offset>0){
                sql.insert(0,"SELECT rt.* FROM (").append(") rt WHERE ROWNUM > ").append(offset);
            }else if(rows>0){
                sql.insert(0,"SELECT rt.* FROM (").append(") t WHERE ROWNUM < ").append(rows + 1);
            }
        }

        String resultSQL = sql.toString();
        if (root && notExistsSQL) {
            StringBuilder notExistsResultSQL = new StringBuilder("SELECT NOT EXISTS ( ");
            if (onlyWhere) {

                notExistsResultSQL.append(resultSQL).append(hasWhere ? " AND " : " WHERE ").append("( ").append(allPredicate.toSQL(toSQLContext))
                        .append(" )").append(" )");
            } else {
                notExistsResultSQL.append("SELECT 1 FROM ( ").append(resultSQL).append(" ) t ").append(" WHERE ").append(allPredicate.toSQL(toSQLContext))
                        .append(" )");
            }
            return notExistsResultSQL.toString();
        } else {
            return resultSQL;
        }
    }
}
