package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.BooleanTypeExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;
import com.easy.query.core.proxy.grouping.FlatElementJoinSQLAnyQueryable;
import com.easy.query.core.proxy.grouping.FlatElementSQLAnyQueryable;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2025/3/8 21:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class RewritePredicateToSelectProvider<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    private final SubQueryContext<T1Proxy, T1> subQueryContext;
    private final AnonymousManyJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder;
    private final T1Proxy propertyProxy;
    private final TableAvailable manyGroupJoinTable;
    private final boolean required;


    public RewritePredicateToSelectProvider(SubQueryContext<T1Proxy, T1> subQueryContext, AnonymousManyJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder, T1Proxy propertyProxy, boolean required) {
        this.subQueryContext = subQueryContext;
        this.manyGroupJoinEntityTableExpressionBuilder = manyGroupJoinEntityTableExpressionBuilder;
        this.propertyProxy = propertyProxy;
        this.manyGroupJoinTable = manyGroupJoinEntityTableExpressionBuilder.getEntityTable();
        this.required = required;
    }

//    public void appendWhere(SQLExpression1<T1Proxy> whereExpression) {
//        if (whereExpression != null) {
//            if (manyGroupJoinWhereExpression == null) {
//                manyGroupJoinWhereExpression = o -> {
//                };
//            }
//            SQLExpression1<T1Proxy> oldWere = manyGroupJoinWhereExpression;
//            manyGroupJoinWhereExpression = o -> {
//                oldWere.apply(o);
//                whereExpression.apply(o);
//            };
//        }
//    }
//
//    public SQLExpression1<T1Proxy> getManyGroupJoinWhereExpression() {
//        return manyGroupJoinWhereExpression;
//    }

    public EntitySQLContext getEntitySQLContext() {
        return subQueryContext.getEntitySQLContext();
    }

    public AnonymousManyJoinEntityTableExpressionBuilder getManyGroupJoinEntityTableExpressionBuilder() {
        return manyGroupJoinEntityTableExpressionBuilder;
    }

    public SubQueryContext<T1Proxy, T1> getSubQueryContext() {
        return subQueryContext;
    }

    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return subQueryContext.getEntityExpressionBuilder();
    }

    public TableAvailable getOriginalTable() {
        return subQueryContext.getLeftTable();
    }

    public T1Proxy getPropertyProxy() {
        return propertyProxy;
    }

    public String getNavValue() {
        return subQueryContext.getFullName();
    }

    public TableAvailable getManyGroupJoinTable() {
        return manyGroupJoinTable;
    }


    public String getOrAppendGroupProjects(SQLSelectAsExpression sqlSelectAsExpression, String methodName) {

        EntityExpressionBuilder entityExpressionBuilder = subQueryContext.getEntityExpressionBuilder();
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = manyGroupJoinEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();
        AsSelectorImpl asSelector = new AsSelectorImpl(entityQueryExpressionBuilder, projectSQLBuilderSegment, manyGroupJoinTable.getEntityMetadata());
        sqlSelectAsExpression.accept(asSelector);
        //表达式可能使用了隐式join需要将表进行对齐
        entityExpressionBuilder.getExpressionContext().getTableContext().extract(entityQueryExpressionBuilder.getExpressionContext().getTableContext());
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(entityExpressionBuilder.getExpressionContext().getTableContext());
        String sql = projectSQLBuilderSegment.toSQL(toSQLContext);
        String parameterString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());

        Integer projectIndex = manyGroupJoinEntityTableExpressionBuilder.addManyGroupJoinProjectExpression(new ManyGroupJoinProjectKey(sql, parameterString, methodName));
        if (projectIndex == null) {
            SQLSegment sqlSegment = projectSQLBuilderSegment.getSQLSegments().get(0);
            int i = entityQueryExpressionBuilder.getProjects().getSQLSegments().size();
            projectIndex = i + 1;
            SQLEntityAliasSegment aliasSegment = (SQLEntityAliasSegment) sqlSegment;
            aliasSegment.setAlias("__" + methodName + projectIndex + "__");
            entityQueryExpressionBuilder.getProjects().getSQLSegments().add(sqlSegment);
        }
        SQLSegment sqlSegment = entityQueryExpressionBuilder.getProjects().getSQLSegments().get(projectIndex - 1);
        SQLEntityAliasSegment aliasSegment = (SQLEntityAliasSegment) sqlSegment;
        return aliasSegment.getAlias();
    }


    private DefaultSQLGroupQueryable<T1Proxy> getDefaultSQLGroupQueryable() {
        DefaultSQLGroupQueryable<T1Proxy> t1ProxyDefaultSQLGroupQueryable = new DefaultSQLGroupQueryable<>(getPropertyProxy(), getPropertyProxy().getEntitySQLContext(), getSubQueryContext().getWhereExpression());
        GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext = t1ProxyDefaultSQLGroupQueryable.getGroupJoinPredicateSegmentContext();
        manyGroupJoinEntityTableExpressionBuilder.addGroupJoinPredicateSegmentContext(groupJoinPredicateSegmentContext);
        return t1ProxyDefaultSQLGroupQueryable;
    }

    /**
     * 返回非null的boolean
     *
     * @return
     */
    public BooleanTypeExpression<Boolean> anyValue() {
        DefaultSQLGroupQueryable<T1Proxy> defaultSQLGroupQueryable = getDefaultSQLGroupQueryable();
        NumberTypeExpression<Long> count = defaultSQLGroupQueryable.count();
//        BooleanTypeExpression<Boolean> any = Expression.of(getPropertyProxy().getEntitySQLContext()).valueOf(() -> {
//            count.gt(0L);
//        });
        BooleanTypeExpressionImpl<Boolean> any = new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), null, f -> f.booleanSQLFunction("({0} > 0)", c -> {
            PropTypeColumn.columnFuncSelector(c, count);
        }), Boolean.class);
        String alias = getOrAppendGroupProjects(any, "any");
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).value(false));
            }
        }, Boolean.class);
    }

    public BooleanTypeExpression<Boolean> noneValue() {
        DefaultSQLGroupQueryable<T1Proxy> defaultSQLGroupQueryable = getDefaultSQLGroupQueryable();
        NumberTypeExpression<Long> count = defaultSQLGroupQueryable.count();
        BooleanTypeExpressionImpl<Boolean> none = new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), null, f -> f.booleanSQLFunction("({0} <= 0)", c -> {
            PropTypeColumn.columnFuncSelector(c, count);
        }), Boolean.class);
        String alias = getOrAppendGroupProjects(none, "none");
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).value(true));
            }
        }, Boolean.class);
    }


    public BooleanTypeExpression<Boolean> flatElementFilterValue(SQLPredicateExpression sqlPredicateExpression) {
        FlatElementSQLAnyQueryable flatElementSQLAnyQueryable = new FlatElementSQLAnyQueryable(getPropertyProxy().getEntitySQLContext(), sqlPredicateExpression);
        GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext = flatElementSQLAnyQueryable.getGroupJoinPredicateSegmentContext();
        manyGroupJoinEntityTableExpressionBuilder.addGroupJoinPredicateSegmentContext(groupJoinPredicateSegmentContext);
        NumberTypeExpression<Long> count = flatElementSQLAnyQueryable.count();
        BooleanTypeExpressionImpl<Boolean> any = new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), null, f -> f.booleanSQLFunction("({0} > 0)", c -> {
            PropTypeColumn.columnFuncSelector(c, count);
        }), Boolean.class);
        String alias = getOrAppendGroupProjects(any, "any");
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).value(false));
            }
        }, Boolean.class);
    }

    public BooleanTypeExpression<Boolean> flatElementAggregateFilterValue(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        FlatElementJoinSQLAnyQueryable flatElementJoinSQLAnyQueryable = new FlatElementJoinSQLAnyQueryable(getPropertyProxy().getEntitySQLContext(), sqlAggregatePredicateExpression);
        GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext = flatElementJoinSQLAnyQueryable.getGroupJoinPredicateSegmentContext();
        manyGroupJoinEntityTableExpressionBuilder.addGroupJoinPredicateSegmentContext(groupJoinPredicateSegmentContext);
        NumberTypeExpression<Long> count = flatElementJoinSQLAnyQueryable.count();
        BooleanTypeExpressionImpl<Boolean> any = new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), null, f -> f.booleanSQLFunction("({0} <= 0)", c -> {
            PropTypeColumn.columnFuncSelector(c, count);
        }), Boolean.class);
        String alias = getOrAppendGroupProjects(any, "any");
        return new BooleanTypeExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), alias, f -> {
            if (required) {
                return f.anySQLFunction("{0}", c -> c.column(alias));
            } else {
                return f.nullOrDefault(c -> c.column(alias).value(false));
            }
        }, Boolean.class);
    }
}
