package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;

/**
 * create time 2023/6/22 14:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class FilterImpl implements Filter {
    private final QueryRuntimeContext runtimeContext;
    private final PredicateSegment rootPredicateSegment;
    private final boolean reverse;
    private PredicateSegment nextPredicateSegment;

    public FilterImpl(QueryRuntimeContext runtimeContext, PredicateSegment predicateSegment, boolean reverse){

        this.runtimeContext = runtimeContext;
        this.rootPredicateSegment = predicateSegment;
        this.reverse = reverse;
        this.nextPredicateSegment = new AndPredicateSegment();
    }
    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextOr() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new OrPredicateSegment();
    }

    protected void next() {
        if (reverse) {
            nextOr();
        } else {
            nextAnd();
        }
    }

    protected SQLPredicateCompare getReallyPredicateCompare(SQLPredicateCompare sqlPredicateCompare) {
        return reverse ? sqlPredicateCompare.toReverse() : sqlPredicateCompare;
    }

    protected void appendThisPredicate(TableAvailable table, String property, Object val, SQLPredicateCompare condition) {

        nextPredicateSegment.setPredicate(new ColumnValuePredicate(table, property, val, getReallyPredicateCompare(condition), runtimeContext));
    }

    protected void appendThisFuncPredicate(TableAvailable table,String propertyName, ColumnFunction func, SQLPredicateCompare compare, Object val) {
        nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(table, func, propertyName, val, compare, runtimeContext));
    }
}
