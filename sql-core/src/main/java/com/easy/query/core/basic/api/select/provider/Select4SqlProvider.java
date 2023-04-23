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
public class Select4SqlProvider<T1,T2,T3,T4> extends Select3SqlProvider<T1,T2,T3> implements EasyQuerySqlBuilderProvider4<T1,T2,T3,T4> {

    private final EntityQueryExpressionBuilder sqlEntityExpression;
    private final int index=3;
    private DefaultSqlGroupColumnSelector<T4> group;
    private DefaultSqlOrderColumnSelector<T4> order;
    private DefaultSqlPredicate<T4> where;
    private DefaultSqlAggregatePredicate<T4> having;
    private DefaultSqlPredicate<T4> on;


    public Select4SqlProvider(EntityQueryExpressionBuilder sqlEntityExpression){
        super(sqlEntityExpression);

        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SqlColumnSelector<T4> getSqlGroupColumnSelector4() {
        if(group==null){
            group= new DefaultSqlGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T4> getSqlOrderColumnSelector4(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T4> getSqlWherePredicate4() {
        if(where==null){
            where=new DefaultSqlPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SqlAggregatePredicate<T4> getSqlAggregatePredicate4() {
        if(having ==null){
            having =new DefaultSqlAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SqlPredicate<T4> getSqlOnPredicate4() {
        if(on==null){
            on=new DefaultSqlPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T4> getSqlColumnSelector4(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T4, TR> getSqlColumnAsSelector4(SqlBuilderSegment sqlSegment0Builder,Class<TR> resultClass) {
        return new DefaultSqlColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
    }

//    @Override
//    public <TR> SqlColumnAsSelector<T4, TR> getAutoSqlColumnAsSelector4(SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
//        return new DefaultAutoSqlColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
//    }

    @Override
    public <TR> SqlColumnResultSelector<T4, TR> getSqlColumnResultSelector4(SqlBuilderSegment sqlSegment0Builder) {
        return new DefaultSqlColumnResultSelector<T4,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
