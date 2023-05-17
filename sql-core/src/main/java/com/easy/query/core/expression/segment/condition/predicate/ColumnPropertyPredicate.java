package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.util.SQLUtil;
import com.easy.query.core.util.SQLExpressionUtil;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnPropertyPredicate implements Predicate,ValuePredicate {
    protected final TableAvailable table;
    protected final String propertyName;
    protected final EasyQueryRuntimeContext runtimeContext;

    public ColumnPropertyPredicate(TableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = SQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SQLEntitySegment cloneSQLEntitySegment() {
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
