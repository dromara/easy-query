package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * @author xuejiaming
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * @Date: 2023/2/14 23:34
 */
public class ColumnValuePredicate implements ValuePredicate, ShardingPredicate {
    private final TableAvailable table;
    private final ColumnMetadata columnMetadata;
    private final Object val;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;

    public ColumnValuePredicate(TableAvailable table, ColumnMetadata columnMetadata, Object val, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.val = val;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        SQLParameter sqlParameter = getParameter();
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,true,false);
        if(columnValueSQLConverter==null){
            EasySQLUtil.addParameter(toSQLContext, sqlParameter);
            return sqlColumnSegment + " " + compare.getSQL() + " ?";
        }else{
            DefaultSQLPropertyConverter sqlValueConverter = new DefaultSQLPropertyConverter(table, expressionContext);
            columnValueSQLConverter.valueConvert(table,columnMetadata,sqlParameter,sqlValueConverter,expressionContext.getRuntimeContext(),true);
            String valSQLParameter = sqlValueConverter.toSQL(toSQLContext);
            return sqlColumnSegment + " " + compare.getSQL() + " "+valSQLParameter;
        }
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return columnMetadata.getPropertyName();
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnValuePredicate(table,columnMetadata,val,compare,expressionContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public SQLParameter getParameter() {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, columnMetadata.getPropertyName(), val);
        if (SQLPredicateCompareEnum.LIKE == compare || SQLPredicateCompareEnum.NOT_LIKE == compare) {
            return new ConstLikeSQLParameter(constSQLParameter);
        }
        return constSQLParameter;
    }
}
