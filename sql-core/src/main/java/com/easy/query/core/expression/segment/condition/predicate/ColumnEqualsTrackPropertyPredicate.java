package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertyTrackSQLParameter;
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
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnEqualsTrackPropertyPredicate implements Predicate,ValuePredicate {
    protected final TableAvailable table;
    protected final String propertyName;
    protected final ExpressionContext expressionContext;
    protected final SQLPredicateCompare sqlPredicateCompare;

    public ColumnEqualsTrackPropertyPredicate(TableAvailable table, String propertyName, ExpressionContext expressionContext,SQLPredicateCompare sqlPredicateCompare){
        this.table = table;
        this.propertyName = propertyName;
        this.expressionContext = expressionContext;
        this.sqlPredicateCompare = sqlPredicateCompare;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        PropertyTrackSQLParameter sqlParameter = new PropertyTrackSQLParameter(table, propertyName,expressionContext.getRuntimeContext());
        ColumnMetadata columnMetadata = this.table.getEntityMetadata().getColumnNotNull(propertyName);
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,true,false);
        if(columnValueSQLConverter==null){
            EasySQLUtil.addParameter(toSQLContext, sqlParameter);
            return sqlColumnSegment + " "+sqlPredicateCompare.getSQL()+" ?";
        }else{
            DefaultSQLPropertyConverter sqlValueConverter = new DefaultSQLPropertyConverter(table, expressionContext);
            columnValueSQLConverter.valueConvert(table,columnMetadata,sqlParameter,sqlValueConverter,expressionContext.getRuntimeContext(),true);
            String valSQLParameter = sqlValueConverter.toSQL(toSQLContext);
            return sqlColumnSegment + " "+sqlPredicateCompare.getSQL()+" "+valSQLParameter;
        }
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        throw new UnsupportedOperationException();
    }


    @Override
    public SQLPredicateCompare getOperator() {
        return SQLPredicateCompareEnum.EQ;
    }

    @Override
    public SQLParameter getParameter() {
        return new PropertySQLParameter(table,propertyName);
    }
}
