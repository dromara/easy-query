package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.parser.impl.DefaultAutoSQLColumnAsSelector;
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
 import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;

/**
 * @FileName: Select1SqlPredicateProvider.java
 * @Description: 文件说明
 * @Date: 2023/2/7 23:45
 * @author xuejiaming
 */
public class Select1SQLProvider<T1> implements EasyQuerySQLBuilderProvider<T1> {

    private final EntityQueryExpressionBuilder sqlEntityExpression;
    private final int index=0;
    private DefaultSQLGroupColumnSelector<T1> group;
    private DefaultSQLOrderColumnSelector<T1> order;
    private DefaultSQLPredicate<T1> where;
    private DefaultSQLPredicate<T1> allPredicate;
    private DefaultSQLAggregatePredicate<T1> having;
    private DefaultSQLPredicate<T1> on;

    public Select1SQLProvider(EntityQueryExpressionBuilder sqlEntityExpression){

        this.sqlEntityExpression = sqlEntityExpression;
    }
    @Override
    public SQLGroupBySelector<T1> getSQLGroupColumnSelector1() {
        if(group==null){
            group= new DefaultSQLGroupColumnSelector<>(index, sqlEntityExpression);
        }
        return group;
    }

    @Override
    public DefaultSQLOrderColumnSelector<T1> getSQLOrderColumnSelector1(boolean asc) {
        if(order==null){
            order= new DefaultSQLOrderColumnSelector<>(index, sqlEntityExpression);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SQLWherePredicate<T1> getSQLWherePredicate1() {
        if(where==null){
            where=new DefaultSQLPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getWhere());
        }
        return where;
    }

    @Override
    public SQLWherePredicate<T1> getSQLAllPredicate1() {
        if(allPredicate==null){
            allPredicate=new DefaultSQLPredicate<>(index, sqlEntityExpression, sqlEntityExpression.getAllPredicate(),true);
        }
        return allPredicate;
    }

    @Override
    public SQLAggregatePredicate<T1> getSQLAggregatePredicate1() {
        if(having ==null){
            having =new DefaultSQLAggregatePredicate<>(index, sqlEntityExpression, sqlEntityExpression.getHaving());
        }
        return having;
    }

    @Override
    public SQLWherePredicate<T1> getSQLOnPredicate1() {
        if(on==null){
            on=new DefaultSQLPredicate<>(index, sqlEntityExpression, EasyUtil.getCurrentPredicateTable(sqlEntityExpression).getOn());
        }
        return on;
    }

    @Override
    public SQLColumnSelector<T1> getSQLColumnSelector1(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnSelector<>(index, sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public <TR> SQLColumnAsSelector<T1, TR> getSQLColumnAsSelector1(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new DefaultSQLColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
    }

    @Override
    public <TR> SQLColumnAsSelector<T1, TR> getSQLAutoColumnAsSelector1(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new DefaultAutoSQLColumnAsSelector<>(index, sqlEntityExpression,sqlSegment0Builder,resultClass);
    }

    @Override
    public <TR> SQLColumnResultSelector<T1, TR> getSQLColumnResultSelector1(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnResultSelector<T1,TR>(index,sqlEntityExpression,sqlSegment0Builder);
    }
}
