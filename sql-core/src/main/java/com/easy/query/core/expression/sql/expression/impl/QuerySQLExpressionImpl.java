package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.ExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.SQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/4/22 20:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class QuerySQLExpressionImpl implements EntityQuerySQLExpression {

    private final EntitySQLExpressionMetadata entitySQLExpressionMetadata;
    protected SQLBuilderSegment projects;
    protected PredicateSegment where;
    protected PredicateSegment allPredicate;
    protected SQLBuilderSegment group;
    protected PredicateSegment having;
    protected SQLBuilderSegment order;
    protected long offset;
    protected long rows;
    protected boolean distinct;
    //    protected List<EntityQuerySQLExpression> includes;
    protected final List<EntityTableSQLExpression> tables = new ArrayList<>();

    public QuerySQLExpressionImpl(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        this.entitySQLExpressionMetadata = entitySQLExpressionMetadata;
    }

    @Override
    public EntitySQLExpressionMetadata getExpressionMetadata() {
        return entitySQLExpressionMetadata;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return entitySQLExpressionMetadata.getRuntimeContext();
    }

    @Override
    public SQLBuilderSegment getProjects() {
        return projects;
    }

    @Override
    public void setProjects(SQLBuilderSegment projects) {
        this.projects = projects;
    }

    @Override
    public PredicateSegment getWhere() {
        return where;
    }

    @Override
    public void setWhere(PredicateSegment where) {
        this.where = where;
    }

    @Override
    public SQLBuilderSegment getGroup() {
        return group;
    }

    @Override
    public void setGroup(SQLBuilderSegment group) {
        this.group = group;
    }

    @Override
    public PredicateSegment getHaving() {
        return having;
    }

    @Override
    public void setHaving(PredicateSegment having) {
        this.having = having;
    }

    @Override
    public SQLBuilderSegment getOrder() {
        return order;
    }

    @Override
    public void setOrder(SQLBuilderSegment order) {
        this.order = order;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public long getRows() {
        return rows;
    }

    @Override
    public void setRows(long rows) {
        this.rows = rows;
    }

    @Override
    public boolean hasLimit() {
        return this.rows > 0;
    }

    @Override
    public void setAllPredicate(PredicateSegment allPredicate) {
        this.allPredicate = allPredicate;
    }

    @Override
    public PredicateSegment getAllPredicate() {
        return allPredicate;
    }

//    @Override
//    public List<EntityQuerySQLExpression> getIncludes() {
//        return includes;
//    }
//
//    @Override
//    public void setIncludes(List<EntityQuerySQLExpression> includes) {
//        this.includes = includes;
//    }

    @Override
    public boolean isDistinct() {
        return distinct;
    }

    @Override
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public List<EntityTableSQLExpression> getTables() {
        return tables;
    }

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

        sql.append(this.projects.toSQL(toSQLContext));
        List<EntityTableSQLExpression> tables = getTables();
        buildSQLTableOrJoin(sql, tables, toSQLContext);

        boolean notExistsSQL = EasySQLSegmentUtil.isNotEmpty(this.allPredicate);
        boolean hasWhere = EasySQLSegmentUtil.isNotEmpty(this.where);
        if (hasWhere) {
            String whereSQL = this.where.toSQL(toSQLContext);
            if (root && notExistsSQL) {
                sql.append(" WHERE ").append("( ").append(whereSQL).append(" )");
            } else {
                sql.append(" WHERE ").append(whereSQL);
            }
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
        if (this.order != null && this.order.isNotEmpty()) {
            onlyWhere = false;
            sql.append(" ORDER BY ").append(this.order.toSQL(toSQLContext));
        }
        if (this.rows > 0) {
            onlyWhere = false;
            sql.append(" LIMIT ");
            sql.append(this.rows);
            if (this.offset > 0) {
                sql.append(" OFFSET ").append(this.offset);
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

    protected void buildSQLTableOrJoin(StringBuilder sql, List<EntityTableSQLExpression> tables, ToSQLContext toSQLContext) {

        if (EasyCollectionUtil.isSingle(tables)) {
            EntityTableSQLExpression firstTable = tables.get(0);
            sql.append(firstTable.toSQL(toSQLContext));
        } else {
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
        }
    }

    @Override
    public EntityQuerySQLExpression cloneSQLExpression() {

        ExpressionFactory expressionFactory = getRuntimeContext().getExpressionFactory();
        EntityQuerySQLExpression easyQuerySQLExpression = expressionFactory.createEasyQuerySQLExpression(entitySQLExpressionMetadata);

        if (EasySQLSegmentUtil.isNotEmpty(this.where)) {
            easyQuerySQLExpression.setWhere(where.clonePredicateSegment());
        }
        if (EasySQLSegmentUtil.isNotEmpty(this.group)) {
            easyQuerySQLExpression.setGroup(group.cloneSQLBuilder());
        }
        if (EasySQLSegmentUtil.isNotEmpty(this.having)) {
            easyQuerySQLExpression.setHaving(having.clonePredicateSegment());
        }
        if (EasySQLSegmentUtil.isNotEmpty(this.order)) {
            easyQuerySQLExpression.setOrder(order.cloneSQLBuilder());
        }
        if (EasySQLSegmentUtil.isNotEmpty(this.projects)) {
            easyQuerySQLExpression.setProjects(projects.cloneSQLBuilder());
        }
        if (EasySQLSegmentUtil.isNotEmpty(this.allPredicate)) {
            easyQuerySQLExpression.setAllPredicate(allPredicate.clonePredicateSegment());
        }
//        if(EasyCollectionUtil.isNotEmpty(this.includes)){
//            ArrayList<EntityQuerySQLExpression> entityQuerySQLExpressions = new ArrayList<>(this.includes.size());
//            for (EntityQuerySQLExpression include : this.includes) {
//                entityQuerySQLExpressions.add(include.cloneSQLExpression());
//            }
//            easyQuerySQLExpression.setIncludes(entityQuerySQLExpressions);
//        }
        easyQuerySQLExpression.setOffset(this.offset);
        easyQuerySQLExpression.setRows(this.rows);
        for (EntityTableSQLExpression table : this.tables) {
            easyQuerySQLExpression.getTables().add(table.cloneSQLExpression());
        }
        return easyQuerySQLExpression;
    }
}
