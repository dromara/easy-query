package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.abstraction.*;
import com.easy.query.core.expression.parser.impl.*;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyUtil;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @author xuejiaming
 */
public class Select2SqlProvider<T1,T2> extends Select1SqlProvider<T1> implements EasyQuerySqlBuilderProvider2<T1,T2> {

    private final EntityQueryExpressionBuilder sqlEntityExpression;
    private final int index=1;
    private DefaultSqlGroupColumnSelector<T2> group;
    private DefaultSqlOrderColumnSelector<T2> order;
    private DefaultSqlPredicate<T2> where;
    private DefaultSqlAggregatePredicate<T2> having;
    private DefaultSqlPredicate<T2> on;

    public Select2SqlProvider(EntityQueryExpressionBuilder sqlEntityExpression){
        super(sqlEntityExpression);

        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SqlGroupByColumnSelector<T2> getSqlGroupColumnSelector2() {
        if(group==null){
            group= new DefaultSqlGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T2> getSqlOrderColumnSelector2(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T2> getSqlWherePredicate2() {
        if(where==null){
            where=new DefaultSqlPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SqlAggregatePredicate<T2> getSqlAggregatePredicate2() {
        if(having ==null){
            having =new DefaultSqlAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SqlPredicate<T2> getSqlOnPredicate2() {
        if(on==null){
            on=new DefaultSqlPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T2> getSqlColumnSelector2(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T2, TR> getSqlColumnAsSelector2(SqlBuilderSegment sqlSegment0Builder,Class<TR> resultClass) {
        return new DefaultSqlColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
    }

//    @Override
//    public <TR> SqlColumnAsSelector<T2, TR> getSqlAutoColumnAsSelector2(SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
//        return new DefaultAutoSqlColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
//    }

    @Override
    public <TR> SqlColumnResultSelector<T2, TR> getSqlColumnResultSelector2(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnResultSelector<T2,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
