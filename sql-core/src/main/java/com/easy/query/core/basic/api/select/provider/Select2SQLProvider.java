package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.parser.impl.DefaultSQLAggregatePredicate;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnAsSelector;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnResultSelector;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSQLGroupColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSQLOrderColumnSelector;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.DefaultSQLPredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyUtil;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @author xuejiaming
 */
public class Select2SQLProvider<T1,T2> extends Select1SQLProvider<T1> implements EasyQuerySQLBuilderProvider2<T1,T2> {

    private final EntityQueryExpressionBuilder sqlEntityExpression;
    private final int index=1;
    private DefaultSQLGroupColumnSelector<T2> group;
    private DefaultSQLOrderColumnSelector<T2> order;
    private DefaultSQLPredicate<T2> where;
    private DefaultSQLAggregatePredicate<T2> having;
    private DefaultSQLPredicate<T2> on;

    public Select2SQLProvider(EntityQueryExpressionBuilder sqlEntityExpression){
        super(sqlEntityExpression);

        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SQLGroupBySelector<T2> getSQLGroupColumnSelector2() {
        if(group==null){
            group= new DefaultSQLGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSQLOrderColumnSelector<T2> getSQLOrderColumnSelector2(boolean asc) {
        if(order==null){
            order= new DefaultSQLOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SQLWherePredicate<T2> getSQLWherePredicate2() {
        if(where==null){
            where=new DefaultSQLPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SQLAggregatePredicate<T2> getSQLAggregatePredicate2() {
        if(having ==null){
            having =new DefaultSQLAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SQLWherePredicate<T2> getSQLOnPredicate2() {
        if(on==null){
            on=new DefaultSQLPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SQLColumnSelector<T2> getSQLColumnSelector2(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SQLColumnAsSelector<T2, TR> getSQLColumnAsSelector2(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new DefaultSQLColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
    }

//    @Override
//    public <TR> SqlColumnAsSelector<T2, TR> getSqlAutoColumnAsSelector2(SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
//        return new DefaultAutoSqlColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
//    }

    @Override
    public <TR> SQLColumnResultSelector<T2, TR> getSQLColumnResultSelector2(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnResultSelector<T2,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
