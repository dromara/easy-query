package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.builder.impl.AsSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.many2group.ManyGroupJoinProjectKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.AnonymousManyGroupJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableBooleanChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableBooleanChainExpressionImpl;
import com.easy.query.core.proxy.grouping.DefaultSQLGroupQueryable;
import com.easy.query.core.proxy.grouping.FlatElementSQLAnyQueryable;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2025/3/8 21:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyJoinPredicateToGroupProjectProvider<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    private final EntitySQLContext entitySQLContext;
    private final AnonymousManyGroupJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final TableAvailable originalTable;
    private final T1Proxy t1Proxy;
    private final String navValue;
    private final TableAvailable manyGroupJoinTable;

    private SQLExpression1<T1Proxy> manyGroupJoinWhereExpression;

    public ManyJoinPredicateToGroupProjectProvider(EntitySQLContext entitySQLContext, AnonymousManyGroupJoinEntityTableExpressionBuilder manyGroupJoinEntityTableExpressionBuilder, EntityExpressionBuilder entityExpressionBuilder, TableAvailable originalTable, String navValue, T1Proxy t1Proxy) {
        this.entitySQLContext = entitySQLContext;
        this.manyGroupJoinEntityTableExpressionBuilder = manyGroupJoinEntityTableExpressionBuilder;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.originalTable = originalTable;
        this.t1Proxy = t1Proxy;
        this.navValue = navValue;
        this.manyGroupJoinTable = manyGroupJoinEntityTableExpressionBuilder.getEntityTable();
    }

    public void appendWhere(SQLExpression1<T1Proxy> whereExpression) {
        if (whereExpression != null) {
            if (manyGroupJoinWhereExpression == null) {
                manyGroupJoinWhereExpression = o -> {
                };
            }
            SQLExpression1<T1Proxy> oldWere = manyGroupJoinWhereExpression;
            manyGroupJoinWhereExpression = o -> {
                oldWere.apply(o);
                whereExpression.apply(o);
            };
        }
    }

    public SQLExpression1<T1Proxy> getManyGroupJoinWhereExpression() {
        return manyGroupJoinWhereExpression;
    }

    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    public AnonymousManyGroupJoinEntityTableExpressionBuilder getManyGroupJoinEntityTableExpressionBuilder() {
        return manyGroupJoinEntityTableExpressionBuilder;
    }

    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }

    public TableAvailable getOriginalTable() {
        return originalTable;
    }

    public T1Proxy getT1Proxy() {
        return t1Proxy;
    }

    public String getNavValue() {
        return navValue;
    }

    public TableAvailable getManyGroupJoinTable() {
        return manyGroupJoinTable;
    }

    public String getOrAppendGroupProjects(SQLSelectAsExpression sqlSelectAsExpression, String methodName) {

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


    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> anyValue(SQLExpression1<T1Proxy> whereExpression) {
        appendWhere(whereExpression);
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(getT1Proxy(), getT1Proxy().getEntitySQLContext(), manyGroupJoinWhereExpression).count();
        ColumnFunctionCompareComparableBooleanChainExpressionImpl<Boolean> any = new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), null, f -> f.anySQLFunction("(CASE WHEN {0} > 0 THEN {1} ELSE {2} END)", c -> {
            PropTypeColumn.columnFuncSelector(c, count);
            c.sqlFunc(f.booleanConstantSQLFunction(true));
            c.sqlFunc(f.booleanConstantSQLFunction(false));
        }), Boolean.class);
        String alias = getOrAppendGroupProjects(any, "any");
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Boolean.class);
    }

    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> noneValue(SQLExpression1<T1Proxy> whereExpression) {
        appendWhere(whereExpression);
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new DefaultSQLGroupQueryable<>(getT1Proxy(), getT1Proxy().getEntitySQLContext(), manyGroupJoinWhereExpression).count();
        ColumnFunctionCompareComparableBooleanChainExpressionImpl<Boolean> none = new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), null, f -> f.anySQLFunction("(CASE WHEN {0} > 0 THEN {1} ELSE {2} END)", c -> {
            PropTypeColumn.columnFuncSelector(c, count);
            c.sqlFunc(f.booleanConstantSQLFunction(false));
            c.sqlFunc(f.booleanConstantSQLFunction(true));
        }), Boolean.class);
        String alias = getOrAppendGroupProjects(none, "none");
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Boolean.class);
    }


    public ColumnFunctionCompareComparableBooleanChainExpression<Boolean> flatElementFilterValue(SQLPredicateExpression sqlPredicateExpression) {
        ColumnFunctionCompareComparableNumberChainExpression<Long> count = new FlatElementSQLAnyQueryable(getT1Proxy().getEntitySQLContext(), sqlPredicateExpression).count();
        ColumnFunctionCompareComparableBooleanChainExpressionImpl<Boolean> any = new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), null, f -> f.anySQLFunction("(CASE WHEN {0} > 0 THEN {1} ELSE {2} END)", c -> {
            PropTypeColumn.columnFuncSelector(c, count);
            c.sqlFunc(f.booleanConstantSQLFunction(true));
            c.sqlFunc(f.booleanConstantSQLFunction(false));
        }), Boolean.class);
        String alias = getOrAppendGroupProjects(any, "any");
        return new ColumnFunctionCompareComparableBooleanChainExpressionImpl<>(this.getEntitySQLContext(), getManyGroupJoinTable(), alias, f -> f.anySQLFunction("{0}", c -> c.column(alias)), Boolean.class);
    }
}
