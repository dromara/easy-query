package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.SQLUtil;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnPropertyPredicate implements Predicate {
    protected final EntityTableExpressionBuilder table;
    protected final String propertyName;
    protected final EntityExpressionBuilder sqlEntityExpression;

    public ColumnPropertyPredicate(EntityTableExpressionBuilder table, String propertyName, EntityExpressionBuilder sqlEntityExpression){
        this.table = table;
        this.propertyName = propertyName;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    @Override
    public EntityTableExpressionBuilder getTable() {
        return table;
    }

    public String getPropertyName() {
        return propertyName;
    }


    @Override
    public SqlPredicateCompare getOperator() {
        return SqlPredicateCompareEnum.EQ;
    }
}
