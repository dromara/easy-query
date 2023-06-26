package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.builder.impl.AggregateFilterImpl;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.builder.impl.AutoAsSelectorImpl;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.builder.impl.GroupSelectorImpl;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.builder.impl.SelectorImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.impl.ColumnAsSelectorImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnAutoAsSelectorImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnGroupSelectorImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnOrderSelectorImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnResultSelectorImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnSelectorImpl;
import com.easy.query.core.expression.parser.core.base.impl.NavigateIncludeImpl;
import com.easy.query.core.expression.parser.core.base.impl.WhereAggregatePredicateImpl;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyUtil;

/**
 * create time 2023/5/20 11:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLExpressionProviderImpl<TEntity> implements SQLExpressionProvider<TEntity> {

    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final TableAvailable table;
    private ColumnGroupSelectorImpl<TEntity> group;
    private ColumnOrderSelectorImpl<TEntity> order;
    private WherePredicateImpl<TEntity> where;
    private NavigateIncludeImpl<TEntity> navigateInclude;
    private WherePredicateImpl<TEntity> allPredicate;
    private WhereAggregatePredicateImpl<TEntity> having;
    private WherePredicateImpl<TEntity> on;

    public SQLExpressionProviderImpl(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        EntityTableExpressionBuilder tableExpressionBuilder = entityQueryExpressionBuilder.getTable(index);
        this.table = tableExpressionBuilder.getEntityTable();
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
    }

    @Override
    public ColumnGroupSelector<TEntity> getGroupColumnSelector() {
        if (group == null) {
            group = new ColumnGroupSelectorImpl<>(table, new GroupSelectorImpl(entityQueryExpressionBuilder));
        }
        return group;
    }

    @Override
    public ColumnOrderSelectorImpl<TEntity> getOrderColumnSelector(boolean asc) {
        if (order == null) {
            order = new ColumnOrderSelectorImpl<>(table, new OrderSelectorImpl(entityQueryExpressionBuilder, entityQueryExpressionBuilder.getOrder()));
        }
        order.setAsc(asc);
        return order;
    }

    @Override
    public WherePredicate<TEntity> getWherePredicate() {
        if (where == null) {
            where = new WherePredicateImpl<>(table, new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getWhere(), false));
        }
        return where;
    }

    @Override
    public NavigateInclude<TEntity> getNavigateInclude() {
        if(navigateInclude==null){
            navigateInclude=new NavigateIncludeImpl<>(table,entityQueryExpressionBuilder);
        }
        return navigateInclude;
    }

    @Override
    public WherePredicate<TEntity> getAllWherePredicate() {
        if (allPredicate == null) {
            allPredicate = new WherePredicateImpl<>(table, new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getAllPredicate(), true));
        }
        return allPredicate;
    }

    @Override
    public WhereAggregatePredicate<TEntity> getAggregatePredicate() {
        if (having == null) {
            having = new WhereAggregatePredicateImpl<>(table, new AggregateFilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getHaving()));
        }
        return having;
    }

    @Override
    public WherePredicate<TEntity> getOnPredicate() {
        if (on == null) {
            on = new WherePredicateImpl<>(table, new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), EasyUtil.getCurrentPredicateTable(entityQueryExpressionBuilder).getOn(), false));
        }
        return on;
    }

    @Override
    public ColumnSelector<TEntity> getColumnSelector(SQLBuilderSegment sqlSegmentBuilder) {
        return new ColumnSelectorImpl<>(table, new SelectorImpl(entityQueryExpressionBuilder, sqlSegmentBuilder));
    }

    @Override
    public <TR> ColumnAsSelector<TEntity, TR> getColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new ColumnAsSelectorImpl<>(entityQueryExpressionBuilder,table, new AsSelectorImpl(entityQueryExpressionBuilder, sqlSegment0Builder, resultClass));
    }

    @Override
    public <TR> ColumnAsSelector<TEntity, TR> getAutoColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        return new ColumnAutoAsSelectorImpl<>(table, new AutoAsSelectorImpl(entityQueryExpressionBuilder, sqlSegment0Builder, resultClass));

    }

    @Override
    public <TR> ColumnResultSelector<TEntity> getColumnResultSelector(SQLBuilderSegment sqlSegment0Builder) {
        return new ColumnResultSelectorImpl<TEntity>(table, entityQueryExpressionBuilder, sqlSegment0Builder);
    }
}
