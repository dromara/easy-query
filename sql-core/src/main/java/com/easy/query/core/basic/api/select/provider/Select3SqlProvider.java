package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.abstraction.*;
import com.easy.query.core.expression.parser.impl.*;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.parser.abstraction.*;
import com.easy.query.core.expression.parser.impl.*;
import com.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.util.EasyUtil;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select3SqlProvider<T1,T2,T3> extends Select2SqlProvider<T1,T2> implements EasyQuerySqlBuilderProvider3<T1,T2,T3> {

    private final SqlEntityQueryExpression sqlEntityExpression;
    private final int index=2;
    private DefaultSqlGroupColumnSelector<T3> group;
    private DefaultSqlOrderColumnSelector<T3> order;
    private DefaultSqlPredicate<T3> where;
    private DefaultSqlAggregatePredicate<T3> having;
    private DefaultSqlPredicate<T3> on;


    public Select3SqlProvider(SqlEntityQueryExpression sqlEntityExpression){
        super(sqlEntityExpression);

        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SqlColumnSelector<T3> getSqlGroupColumnSelector3() {
        if(group==null){
            group= new DefaultSqlGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T3> getSqlOrderColumnSelector3(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T3> getSqlWherePredicate3() {
        if(where==null){
            where=new DefaultSqlPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SqlAggregatePredicate<T3> getSqlAggregatePredicate3() {
        if(having ==null){
            having =new DefaultSqlAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SqlPredicate<T3> getSqlOnPredicate3() {
        if(on==null){
            on=new DefaultSqlPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T3> getSqlColumnSelector3(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T3, TR> getSqlColumnAsSelector3(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnResultSelector<T3, TR> getSqlColumnResultSelector3(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnResultSelector<T3,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
