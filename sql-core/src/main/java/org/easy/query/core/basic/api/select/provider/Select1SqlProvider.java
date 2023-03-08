package org.easy.query.core.basic.api.select.provider;

import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
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
    private final int index=0;
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
            group= new DefaultSqlGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T1> getSqlOrderColumnSelector1(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T1> getSqlWherePredicate1() {
        if(where==null){
            where=new DefaultSqlPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SqlAggregatePredicate<T1> getSqlAggregatePredicate1() {
        if(having ==null){
            having =new DefaultSqlAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SqlPredicate<T1> getSqlOnPredicate1() {
        if(on==null){
            on=new DefaultSqlPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T1> getSqlColumnSelector1(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T1, TR> getSqlColumnAsSelector1(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnResultSelector<T1, TR> getSqlColumnResultSelector1(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnResultSelector<T1,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
