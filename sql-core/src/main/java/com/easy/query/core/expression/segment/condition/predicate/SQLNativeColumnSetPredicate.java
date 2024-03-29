package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.impl.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.metadata.ColumnMetadata;
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

    public SQLNativeColumnSetPredicate(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(runtimeContext, sqlSegment, sqlNativeExpression);
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
    public Predicate cloneSQLColumnSegment() {
        return new SQLNativeColumnSetPredicate(this.table,this.propertyName,runtimeContext,sqlSegment, sqlNativeExpression);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return SQLPredicateCompareEnum.EQ;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(runtimeContext, table, columnMetadata, toSQLContext,true,false);
        return sqlColumnSegment +" "+getOperator().getSQL()+" "+ super.toSQL(toSQLContext);
    }
}
