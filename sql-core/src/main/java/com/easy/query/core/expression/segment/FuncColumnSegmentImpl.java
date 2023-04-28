package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.sharding.merge.result.aggregation.AggregationType;

/**
 * @FileName: FuncColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/19 22:17
 * @author xuejiaming
 */
public class FuncColumnSegmentImpl implements AggregationColumnSegment {


    protected final EntityTableExpressionBuilder table;
    protected final String propertyName;
    protected final EntityExpressionBuilder sqlEntityExpression;
    protected final EasyFunc easyFunc;
    protected String alias;

    public FuncColumnSegmentImpl(EntityTableExpressionBuilder table, String propertyName, EntityExpressionBuilder sqlEntityExpression, EasyFunc easyFunc){
        this(table,propertyName,sqlEntityExpression,easyFunc,null);
    }
    public FuncColumnSegmentImpl(EntityTableExpressionBuilder table, String propertyName, EntityExpressionBuilder sqlEntityExpression, EasyFunc easyFunc, String alias){
        this.table = table;
        this.propertyName = propertyName;
        this.sqlEntityExpression = sqlEntityExpression;
        this.easyFunc = easyFunc;
        this.alias = alias;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        String funcColumn = easyFunc.getFuncColumn(sqlColumnSegment);
        StringBuilder sql = new StringBuilder().append(funcColumn);
        if(alias!=null){
            sql.append(" AS ").append(sqlEntityExpression.getQuoteName(alias));
        }
        return sql.toString();
    }

    @Override
    public EntityTableExpressionBuilder getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public AggregationType getAggregationType() {
        return easyFunc.getAggregationType();
    }
}
