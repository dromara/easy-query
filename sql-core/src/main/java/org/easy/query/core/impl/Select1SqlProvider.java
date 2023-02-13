package org.easy.query.core.impl;

import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider;
import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.impl.lambda.*;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @Created by xuejiaming
 */
public class Select1SqlProvider<T1> implements EasyQuerySqlBuilderProvider<T1> {

    private final SelectContext selectContext;
    private  DefaultSqlGroupColumnSelector<T1> group;
    private  DefaultSqlOrderColumnSelector<T1> order;
    private DefaultSqlPredicate<T1> where;
    private DefaultSqlPredicate<T1> on;

    public Select1SqlProvider(SelectContext selectContext){

        this.selectContext = selectContext;
    }
    @Override
    public SqlColumnSelector<T1> getSqlGroupColumnSelector1() {
        if(group==null){
            group= new DefaultSqlGroupColumnSelector<>(0,selectContext);
        }
        return group;
    }

    @Override
    public DefaultSqlOrderColumnSelector<T1> getSqlOrderColumnSelector1(boolean asc) {
        if(order==null){
            order= new DefaultSqlOrderColumnSelector<>(0,selectContext);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SqlPredicate<T1> getSqlWherePredicate1() {
        if(where==null){
            where=new DefaultSqlPredicate<>(0,selectContext,selectContext.getWhere());
        }
        return where;
    }
    @Override
    public SqlPredicate<T1> getSqlOnPredicate1() {
        if(on==null){
            on=new DefaultSqlPredicate<>(0,selectContext,selectContext.getCurrentPredicateTable().getOn());
        }
        return on;
    }

    @Override
    public SqlColumnSelector<T1> getSqlColumnSelector1(SqlSegment0Builder sqlSegment0Builder) {
        return new DefaultSqlColumnSelector<>(0,selectContext,sqlSegment0Builder);
    }

    @Override
    public <TR> SqlColumnAsSelector<T1, TR> getSqlColumnAsSelector1(SqlSegment0Builder sqlSegment0Builder) {
        return new DefaultSqlColumnAsSelector<>(0,selectContext,sqlSegment0Builder);
    }

}
