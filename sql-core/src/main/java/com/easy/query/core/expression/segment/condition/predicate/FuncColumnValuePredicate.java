package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * @FileName: FuncColumnValuePredicate.java
 * @Description: column加方法函数和对应的值比较
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class FuncColumnValuePredicate implements Predicate {
    private final TableAvailable table;
    private final ColumnFunction func;
    private final String propertyName;
    private final Object val;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;

    public FuncColumnValuePredicate(TableAvailable table, ColumnFunction func, String propertyName, Object val, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.func = func;
        this.val = val;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext,new EasyConstSQLParameter(table,propertyName,val));
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,true,false);
        return func.getFuncColumn(sqlColumnSegment) +" "+ compare.getSQL() + " ?";
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {

        throw new UnsupportedOperationException();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }
}
