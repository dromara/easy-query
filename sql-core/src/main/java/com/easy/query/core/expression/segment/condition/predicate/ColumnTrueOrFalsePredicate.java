package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/12/15 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnTrueOrFalsePredicate implements Predicate{
    private final SQLPredicateCompare operator;
    private final TableAvailable table;
    private final boolean trueOrFalse;

    public ColumnTrueOrFalsePredicate(boolean trueOrFalse,SQLPredicateCompare operator){
        this(trueOrFalse,operator,null);

    }
    public ColumnTrueOrFalsePredicate(boolean trueOrFalse,SQLPredicateCompare operator,TableAvailable table){

        this.trueOrFalse = trueOrFalse;
        this.operator = operator;
        this.table = table;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return trueOrFalse?" 1 = 1 ":" 1 = 2 ";
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return operator;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnTrueOrFalsePredicate(trueOrFalse,operator,table);
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
