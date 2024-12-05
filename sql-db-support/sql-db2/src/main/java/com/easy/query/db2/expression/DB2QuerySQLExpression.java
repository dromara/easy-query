package com.easy.query.db2.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
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
import com.easy.query.core.util.EasyColumnSegmentUtil;
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
public class DB2QuerySQLExpression extends QuerySQLExpressionImpl {
    public DB2QuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
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
        boolean hasWhere = EasySQLSegmentUtil.isNotEmpty(this.where);
        if (hasWhere) {
            String whereSQL = this.where.toSQL(toSQLContext);
            sql.append(" WHERE ").append(whereSQL);
        }
        boolean hasGroupBy = this.group != null && this.group.isNotEmpty();
        if (hasGroupBy) {
            sql.append(" GROUP BY ").append(this.group.toSQL(toSQLContext));
        }
        if (this.having != null && this.having.isNotEmpty()) {
            sql.append(" HAVING ").append(this.having.toSQL(toSQLContext));
        }
        boolean hasOrderBy = this.order != null && this.order.isNotEmpty();
        if (hasOrderBy) {
            sql.append(" ORDER BY ").append(this.order.toSQL(toSQLContext));
        }
        //分页必须要有order by
        if (this.offset > 0) {
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
        }else if(rows > 0){
            sql.append(" FETCH FIRST ").append(rows).append(" ROWS ONLY");
        }

        return sql.toString();
    }

    protected SQLBuilderSegment getPrimaryKeyOrFirstColumnOrder(TableAvailable table) {
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        String property = getPrimaryKeyOrFirstColumn(table);
        if(property==null){
            return null;
        }
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        Column2Segment column2Segment = EasyColumnSegmentUtil.createColumn2Segment(table, columnMetadata, getExpressionMetadata().getExpressionContext());
        orderBySQLBuilderSegment.append(new ColumnSegmentImpl(column2Segment,null));
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
