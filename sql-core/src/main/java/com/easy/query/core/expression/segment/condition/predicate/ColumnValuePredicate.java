package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

import java.util.Objects;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnValuePredicate implements Predicate {
    private final EntityTableExpression table;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final EntityExpression sqlEntityExpression;

    public ColumnValuePredicate(EntityTableExpression table, String propertyName, Object val, SqlPredicateCompare compare, EntityExpression sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, propertyName, val);
        String compareSql = compare.getSql();
        if(Objects.equals(SqlPredicateCompareEnum.LIKE.getSql(),compareSql)){
            sqlEntityExpression.addParameter(new ConstLikeSQLParameter(constSQLParameter));
        }else{
            sqlEntityExpression.addParameter(constSQLParameter);
        }
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " " + compareSql + " ?";
    }

    @Override
    public EntityTableExpression getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

}
