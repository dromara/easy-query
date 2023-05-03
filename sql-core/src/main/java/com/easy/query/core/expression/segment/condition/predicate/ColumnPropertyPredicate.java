package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.parser.abstraction.internal.EntityTableAvailable;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.SQLUtil;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnPropertyPredicate implements Predicate,ValuePredicate {
    protected final EntityTableAvailable table;
    protected final String propertyName;
    protected final EasyQueryRuntimeContext runtimeContext;

    public ColumnPropertyPredicate(EntityTableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext){
        this.table = table;
        this.propertyName = propertyName;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    @Override
    public EntityTableAvailable getTable() {
        return table;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public SqlEntitySegment cloneSqlEntitySegment() {
        throw new UnsupportedOperationException();
    }


    @Override
    public SqlPredicateCompare getOperator() {
        return SqlPredicateCompareEnum.EQ;
    }

    @Override
    public SQLParameter getParameter() {
        return new PropertySQLParameter(table,propertyName);
    }
}
