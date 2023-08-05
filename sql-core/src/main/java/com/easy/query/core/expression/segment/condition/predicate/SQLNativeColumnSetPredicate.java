package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/5 12:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeColumnSetPredicate extends AbstractSQLNativeSegmentImpl implements Predicate {
    protected final TableAvailable table;
    protected final String propertyName;

    public SQLNativeColumnSetPredicate(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpressionContext sqlConstExpressionContext) {
        super(runtimeContext, sqlSegment, sqlConstExpressionContext);
        this.table = table;
        this.propertyName = propertyName;
    }

    @Override
    public TableAvailable getTable() {
        return this.table;
    }

    @Override
    public String getPropertyName() {
        return this.propertyName;
    }

    @Override
    public SQLColumnSegment cloneSQLColumnSegment() {
        return new SQLNativeColumnSetPredicate(this.table,this.propertyName,runtimeContext,sqlSegment,sqlConstExpressionContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return SQLPredicateCompareEnum.EQ;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String column =  EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
        return column +" "+getOperator().getSQL()+" "+ super.toSQL(toSQLContext);
    }
}
