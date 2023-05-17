package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.parser.impl.*;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.DefaultSQLPredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyUtil;

/**
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @author xuejiaming
 */
public class Select3SQLProvider<T1,T2,T3> extends Select2SQLProvider<T1,T2> implements EasyQuerySQLBuilderProvider3<T1,T2,T3> {

    private final EntityQueryExpressionBuilder sqlEntityExpression;
    private final int index=2;
    private DefaultSQLGroupColumnSelector<T3> group;
    private DefaultSQLOrderColumnSelector<T3> order;
    private DefaultSQLPredicate<T3> where;
    private DefaultSQLAggregatePredicate<T3> having;
    private DefaultSQLPredicate<T3> on;


    public Select3SQLProvider(EntityQueryExpressionBuilder sqlEntityExpression){
        super(sqlEntityExpression);

        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SQLGroupBySelector<T3> getSQLGroupColumnSelector3() {
        if(group==null){
            group= new DefaultSQLGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSQLOrderColumnSelector<T3> getSQLOrderColumnSelector3(boolean asc) {
        if(order==null){
            order= new DefaultSQLOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SQLWherePredicate<T3> getSQLWherePredicate3() {
        if(where==null){
            where=new DefaultSQLPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SQLAggregatePredicate<T3> getSQLAggregatePredicate3() {
        if(having ==null){
            having =new DefaultSQLAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SQLWherePredicate<T3> getSQLOnPredicate3() {
        if(on==null){
            on=new DefaultSQLPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SQLColumnSelector<T3> getSQLColumnSelector3(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SQLColumnAsSelector<T3, TR> getSQLColumnAsSelector3(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new DefaultSQLColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
    }


    @Override
    public <TR> SQLColumnResultSelector<T3, TR> getSQLColumnResultSelector3(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnResultSelector<T3,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
