package com.easy.query.mssql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.sql.builder.ExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.SQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpressionImpl;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * create time 2023/7/27 17:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLQuerySQLExpression extends QuerySQLExpressionImpl {
    public MsSQLQuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
    }

    /**
     * 分页部分代码参考 <a href="https://github.com/dotnetcore/FreeSql">FreeSQL</a>
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
            if (root && notExistsSQL) {
                sql.append(" WHERE ").append("( ").append(whereSQL).append(" )");
            } else {
                sql.append(" WHERE ").append(whereSQL);
            }
        }
        boolean onlyWhere = true;
        boolean hasGroupBy = this.group != null && this.group.isNotEmpty();
        if (hasGroupBy) {
            onlyWhere = false;
            sql.append(" GROUP BY ").append(this.group.toSQL(toSQLContext));
        }
        if (this.having != null && this.having.isNotEmpty()) {
            onlyWhere = false;
            sql.append(" HAVING ").append(this.having.toSQL(toSQLContext));
        }
        boolean hasOrderBy = this.order != null && this.order.isNotEmpty();
        if (hasOrderBy) {
            onlyWhere = false;
            sql.append(" ORDER BY ").append(this.order.toSQL(toSQLContext));
        }
        //分页必须要有order by
        if (this.offset > 0) {
            onlyWhere = false;
            if (!hasOrderBy) {
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

            sql.append(" OFFSET ").append(offset).append(" ROWS");
            if (this.rows > 0) {
                sql.append(" FETCH NEXT ").append(rows).append(" ROWS ONLY");
            }
        }

        String resultSQL = sql.toString();
        if (root && notExistsSQL) {
            StringBuilder notExistsResultSQL = new StringBuilder("SELECT NOT EXISTS ( ");
            if (onlyWhere) {

                notExistsResultSQL.append(resultSQL).append(hasWhere ? " AND " : " WHERE ").append("( ").append(allPredicate.toSQL(toSQLContext))
                        .append(" )").append(" )");
            } else {
                notExistsResultSQL.append("SELECT 1 FROM ( ").append(resultSQL).append(" ) rrt ").append(" WHERE ").append(allPredicate.toSQL(toSQLContext))
                        .append(" )");
            }
            return notExistsResultSQL.toString();
        } else {
            return resultSQL;
        }
    }

    protected SQLBuilderSegment getPrimaryKeyOrFirstColumnOrder(TableAvailable table) {
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        String property = getPrimaryKeyOrFirstColumn(table);
        if(property==null){
            return null;
        }
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        orderBySQLBuilderSegment.append(new ColumnSegmentImpl(table, columnMetadata, getExpressionMetadata().getExpressionContext()));
        return orderBySQLBuilderSegment;
    }

    protected String getPrimaryKeyOrFirstColumn(TableAvailable table) {
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if (EasyCollectionUtil.isEmpty(keyProperties)) {
            Collection<String> properties = table.getEntityMetadata().getProperties();
            if(EasyCollectionUtil.isEmpty(properties)){
                return null;
//                throw new EasyQueryInvalidOperationException("no property mapping to column");
            }
            return EasyCollectionUtil.first(properties);
        } else {
            return EasyCollectionUtil.first(keyProperties);
        }
    }
}
