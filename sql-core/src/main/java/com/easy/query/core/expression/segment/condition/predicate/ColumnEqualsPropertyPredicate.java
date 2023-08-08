package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverterImpl;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnEqualsPropertyPredicate implements Predicate,ValuePredicate {
    protected final TableAvailable table;
    protected final String propertyName;
    protected final QueryRuntimeContext runtimeContext;

    public ColumnEqualsPropertyPredicate(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        PropertySQLParameter sqlParameter = new PropertySQLParameter(table, propertyName);
        ColumnMetadata columnMetadata = this.table.getEntityMetadata().getColumnNotNull(propertyName);
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, table, columnMetadata, toSQLContext);
        if(columnValueSQLConverter==null){
            EasySQLUtil.addParameter(toSQLContext, sqlParameter);
            return sqlColumnSegment + " = ?";
        }else{
            SQLPropertyConverterImpl sqlPropertyConverter = new SQLPropertyConverterImpl(table, runtimeContext);
            columnValueSQLConverter.valueConverter(table,propertyName,sqlParameter,sqlPropertyConverter);
            String valSQLParameter = sqlPropertyConverter.toSQL(toSQLContext);
            return sqlColumnSegment + " = "+valSQLParameter;
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
    public SQLEntitySegment cloneSQLColumnSegment() {
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
