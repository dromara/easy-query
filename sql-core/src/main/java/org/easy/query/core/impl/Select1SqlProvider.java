package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider;
import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
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
public class Select1SqlProvider<T1> implements EasyQuerySqlBuilderProvider<T1> {

    private final SqlEntityQueryExpression sqlEntityExpression;
    private DefaultSqlGroupColumnSelector<T1> group;
    private DefaultSqlOrderColumnSelector<T1> order;
    private DefaultSqlPredicate<T1> where;
    private DefaultSqlAggregatePredicate<T1> having;
    private DefaultSqlPredicate<T1> on;

    public Select1SqlProvider(SqlEntityQueryExpression sqlEntityExpression){

        this.sqlEntityExpression = sqlEntityExpression;
    }
    @Override
    public SqlColumnSelector<T1> getSqlGroupColumnSelector1() {
        if(group==null){
            group= new DefaultSqlGroupColumnSelector<>(0, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T1> getSqlOrderColumnSelector1(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(0, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T1> getSqlWherePredicate1() {
        if(where==null){
            where=new DefaultSqlPredicate<>(0, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SqlAggregatePredicate<T1> getSqlAggregatePredicate1() {
        if(having ==null){
            having =new DefaultSqlAggregatePredicate<>(0, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SqlPredicate<T1> getSqlOnPredicate1() {
        if(on==null){
            on=new DefaultSqlPredicate<>(0, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T1> getSqlColumnSelector1(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(0, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T1, TR> getSqlColumnAsSelector1(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnAsSelector<>(0, sqlEntityExpression,sqlSegment0Builder);
    }

}
