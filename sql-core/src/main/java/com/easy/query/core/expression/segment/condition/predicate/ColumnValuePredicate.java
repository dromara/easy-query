package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.util.SQLUtil;
import com.easy.query.core.util.SQLExpressionUtil;

import java.util.Objects;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnValuePredicate implements ValuePredicate, ShardingPredicate {
    private final TableAvailable table;
    private final String propertyName;
    private final Object val;
    private final SQLPredicateCompare compare;
    private final EasyQueryRuntimeContext runtimeContext;

    public ColumnValuePredicate(TableAvailable table, String propertyName, Object val, SQLPredicateCompare compare, EasyQueryRuntimeContext runtimeContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
        String compareSql = compare.getSQL();
        if(Objects.equals(SQLPredicateCompareEnum.LIKE.getSQL(),compareSql)){
            SQLUtil.addParameter(sqlParameterCollector,new ConstLikeSQLParameter(constSQLParameter));
        }else{
            SQLUtil.addParameter(sqlParameterCollector,constSQLParameter);
        }
        String sqlColumnSegment = SQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
        return sqlColumnSegment + " " + compareSql + " ?";
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
    public SQLEntitySegment cloneSQLEntitySegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public SQLParameter getParameter() {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
        String compareSql = compare.getSQL();
        if(Objects.equals(SQLPredicateCompareEnum.LIKE.getSQL(),compareSql)) {
             return new ConstLikeSQLParameter(constSQLParameter);
        }
        return constSQLParameter;
    }
}
