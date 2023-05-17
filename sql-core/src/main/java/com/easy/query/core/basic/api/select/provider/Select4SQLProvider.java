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
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @author xuejiaming
 */
public class Select4SQLProvider<T1,T2,T3,T4> extends Select3SQLProvider<T1,T2,T3> implements EasyQuerySQLBuilderProvider4<T1,T2,T3,T4> {

    private final EntityQueryExpressionBuilder sqlEntityExpression;
    private final int index=3;
    private DefaultSQLGroupColumnSelector<T4> group;
    private DefaultSQLOrderColumnSelector<T4> order;
    private DefaultSQLPredicate<T4> where;
    private DefaultSQLAggregatePredicate<T4> having;
    private DefaultSQLPredicate<T4> on;


    public Select4SQLProvider(EntityQueryExpressionBuilder sqlEntityExpression){
        super(sqlEntityExpression);

        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public SQLGroupBySelector<T4> getSQLGroupColumnSelector4() {
        if(group==null){
            group= new DefaultSQLGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSQLOrderColumnSelector<T4> getSQLOrderColumnSelector4(boolean asc) {
        if(order==null){
            order= new DefaultSQLOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SQLWherePredicate<T4> getSQLWherePredicate4() {
        if(where==null){
            where=new DefaultSQLPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SQLAggregatePredicate<T4> getSQLAggregatePredicate4() {
        if(having ==null){
            having =new DefaultSQLAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SQLWherePredicate<T4> getSQLOnPredicate4() {
        if(on==null){
            on=new DefaultSQLPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SQLColumnSelector<T4> getSQLColumnSelector4(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SQLColumnAsSelector<T4, TR> getSQLColumnAsSelector4(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new DefaultSQLColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
    }
    @Override
    public <TR> SQLColumnResultSelector<T4, TR> getSQLColumnResultSelector4(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnResultSelector<T4,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
