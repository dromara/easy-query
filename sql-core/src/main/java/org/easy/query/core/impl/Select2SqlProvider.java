package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider2;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.impl.*;
import org.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.util.EasyUtil;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select2SqlProvider<T1,T2> extends Select1SqlProvider<T1> implements EasyQuerySqlBuilderProvider2<T1,T2> {

    private final SqlEntityQueryExpression sqlEntityExpression;
    private DefaultSqlGroupColumnSelector<T2> group;
    private DefaultSqlOrderColumnSelector<T2> order;
    private DefaultSqlPredicate<T2> where;
    private DefaultSqlAggregatePredicate<T2> having;
    private DefaultSqlPredicate<T2> on;

    public Select2SqlProvider(SqlEntityQueryExpression sqlEntityExpression){
        super(sqlEntityExpression);

        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SqlColumnSelector<T2> getSqlGroupColumnSelector2() {
        if(group==null){
            group= new DefaultSqlGroupColumnSelector<>(1, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T2> getSqlOrderColumnSelector2(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(1, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T2> getSqlWherePredicate2() {
        if(where==null){
            where=new DefaultSqlPredicate<>(1, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SqlAggregatePredicate<T2> getSqlAggregatePredicate2() {
        if(having ==null){
            having =new DefaultSqlAggregatePredicate<>(1, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SqlPredicate<T2> getSqlOnPredicate2() {
        if(on==null){
            on=new DefaultSqlPredicate<>(1, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T2> getSqlColumnSelector2(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(1, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T2, TR> getSqlColumnAsSelector2(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnAsSelector<>(1, sqlEntityExpression,sqlSegment0Builder);
    }
}
