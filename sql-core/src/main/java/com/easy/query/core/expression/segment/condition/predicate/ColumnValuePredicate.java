package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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
    private final String propertyName;
    private final Object val;
    private final SQLPredicateCompare compare;
    private final QueryRuntimeContext runtimeContext;

    public ColumnValuePredicate(TableAvailable table, String propertyName, Object val, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        SQLParameter sqlParameter = getParameter();
        ColumnMetadata columnMetadata = this.table.getEntityMetadata().getColumnNotNull(propertyName);
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if(columnValueSQLConverter==null){
            String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, table, columnMetadata, toSQLContext);
            EasySQLUtil.addParameter(toSQLContext, sqlParameter);
            return sqlColumnSegment + " " + compare.getSQL() + " ?";
        }else{
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
            DefaultSQLPropertyConverter sqlValueConverter = new DefaultSQLPropertyConverter(table, runtimeContext);
            columnValueSQLConverter.propertyColumnConvert(table,columnMetadata,sqlPropertyConverter,runtimeContext);
            columnValueSQLConverter.valueConvert(table,columnMetadata,sqlParameter,sqlValueConverter,runtimeContext);
            String sqlColumnSegment = sqlPropertyConverter.toSQL(toSQLContext);
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
        return propertyName;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnValuePredicate(table,propertyName,val,compare,runtimeContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public SQLParameter getParameter() {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
        if (SQLPredicateCompareEnum.LIKE == compare || SQLPredicateCompareEnum.NOT_LIKE == compare) {
            return new ConstLikeSQLParameter(constSQLParameter);
        }
        return constSQLParameter;
    }
}
