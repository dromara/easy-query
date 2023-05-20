package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
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

/**
 * create time 2023/5/20 11:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SelectSQLProviderImpl<TEntity> implements SQLExpressionProvider<TEntity> {

    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final int index;
    private DefaultSQLGroupColumnSelector<TEntity> group;
    private DefaultSQLOrderColumnSelector<TEntity> order;
    private DefaultSQLPredicate<TEntity> where;
    private DefaultSQLPredicate<TEntity> allPredicate;
    private DefaultSQLAggregatePredicate<TEntity> having;
    private DefaultSQLPredicate<TEntity> on;

    public SelectSQLProviderImpl(int index,EntityQueryExpressionBuilder entityQueryExpressionBuilder){
        this.index = index;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
    }
    @Override
    public SQLGroupBySelector<TEntity> getSQLGroupColumnSelector() {
        if(group==null){
            group= new DefaultSQLGroupColumnSelector<>(index, entityQueryExpressionBuilder);
        }
        return group;
    }

    @Override
    public DefaultSQLOrderColumnSelector<TEntity> getSQLOrderColumnSelector(boolean asc) {
        if(order==null){
            order= new DefaultSQLOrderColumnSelector<>(index, entityQueryExpressionBuilder);
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public SQLWherePredicate<TEntity> getSQLWherePredicate() {
        if(where==null){
            where=new DefaultSQLPredicate<>(index, entityQueryExpressionBuilder, entityQueryExpressionBuilder.getWhere());
        }
        return where;
    }

    @Override
    public SQLWherePredicate<TEntity> getSQLAllPredicate() {
        if(allPredicate==null){
            allPredicate=new DefaultSQLPredicate<>(index, entityQueryExpressionBuilder, entityQueryExpressionBuilder.getAllPredicate(),true);
        }
        return allPredicate;
    }

    @Override
    public SQLAggregatePredicate<TEntity> getSQLAggregatePredicate() {
        if(having ==null){
            having =new DefaultSQLAggregatePredicate<>(index, entityQueryExpressionBuilder, entityQueryExpressionBuilder.getHaving());
        }
        return having;
    }

    @Override
    public SQLWherePredicate<TEntity> getSQLOnPredicate() {
        if(on==null){
            on=new DefaultSQLPredicate<>(index, entityQueryExpressionBuilder, EasyUtil.getCurrentPredicateTable(entityQueryExpressionBuilder).getOn());
        }
        return on;
    }

    @Override
    public SQLColumnSelector<TEntity> getSQLColumnSelector(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnSelector<>(index, entityQueryExpressionBuilder,sqlSegment0Builder);
    }

    @Override
    public <TR> SQLColumnAsSelector<TEntity, TR> getSQLColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new DefaultSQLColumnAsSelector<>(index, entityQueryExpressionBuilder,sqlSegment0Builder,resultClass);
    }

    @Override
    public <TR> SQLColumnAsSelector<TEntity, TR> getSQLAutoColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new DefaultAutoSQLColumnAsSelector<>(index, entityQueryExpressionBuilder,sqlSegment0Builder,resultClass);
    }

    @Override
    public <TR> SQLColumnResultSelector<TEntity, TR> getSQLColumnResultSelector(SQLBuilderSegment sqlSegment0Builder) {
        return new DefaultSQLColumnResultSelector<TEntity,TR>(index, entityQueryExpressionBuilder,sqlSegment0Builder);
    }
}
