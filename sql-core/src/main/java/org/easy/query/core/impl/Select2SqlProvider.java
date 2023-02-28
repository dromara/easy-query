package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider2;
import org.easy.query.core.basic.api.context.SelectContext;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.impl.*;
import org.easy.query.core.expression.segment.predicate.DefaultSqlPredicate;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select2SqlProvider<T1,T2> extends Select1SqlProvider<T1> implements EasyQuerySqlBuilderProvider2<T1,T2> {

    private final SelectContext selectContext;
    private DefaultSqlGroupColumnSelector<T2> group;
    private DefaultSqlOrderColumnSelector<T2> order;
    private DefaultSqlPredicate<T2> where;
    private DefaultSqlAggregatePredicate<T2> having;
    private DefaultSqlPredicate<T2> on;

    public Select2SqlProvider(SelectContext selectContext){
        super(selectContext);

        this.selectContext = selectContext;
    }

    @Override
    public SqlColumnSelector<T2> getSqlGroupColumnSelector2() {
        if(group==null){
            group= new DefaultSqlGroupColumnSelector<>(1,selectContext);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T2> getSqlOrderColumnSelector2(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(1,selectContext);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T2> getSqlWherePredicate2() {
        if(where==null){
            where=new DefaultSqlPredicate<>(1,selectContext,selectContext.getWhere());
        }
        return where;
    }

    @Override
    public SqlAggregatePredicate<T2> getSqlAggregatePredicate2() {
        if(having ==null){
            having =new DefaultSqlAggregatePredicate<>(1,selectContext,selectContext.getHaving());
        }
        return having;
    }

    @Override
    public SqlPredicate<T2> getSqlOnPredicate2() {
        if(on==null){
            on=new DefaultSqlPredicate<>(1,selectContext,selectContext.getCurrentPredicateTable().getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T2> getSqlColumnSelector2(SqlSegmentBuilder sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(1,selectContext,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T2, TR> getSqlColumnAsSelector2(SqlSegmentBuilder sqlSegment0Builder) {
        return new DefaultSqlColumnAsSelector<>(1,selectContext,sqlSegment0Builder);
    }
}
