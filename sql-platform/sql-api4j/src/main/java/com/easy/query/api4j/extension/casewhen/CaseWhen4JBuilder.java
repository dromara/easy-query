package com.easy.query.api4j.extension.casewhen;

import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;

/**
 * create time 2023/7/3 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhen4JBuilder<T1, TR> {
    private final SQLColumnAsSelector<T1, TR> sqlColumnAsSelector;
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhen4JBuilder(SQLColumnAsSelector<T1, TR> sqlColumnAsSelector) {
        this.sqlColumnAsSelector = sqlColumnAsSelector;
        this.caseWhenBuilder = new CaseWhenBuilder(sqlColumnAsSelector.getRuntimeContext(), sqlColumnAsSelector.getExpressionContext());
    }

    public CaseWhen4JBuilder<T1, TR> caseWhen(SQLExpression1<SQLWherePredicate<T1>> predicateExpression, Object then) {
        caseWhenBuilder.caseWhen(filter -> {
            predicateExpression.apply(new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector.getTable(), new FilterContext(filter,sqlColumnAsSelector.getColumnAsSelector().getAsSelector().getEntityQueryExpressionBuilder()))));
        }, then);
        return this;
    }

    public CaseWhen4JBuilder<T1, TR> caseWhen(SQLExpression1<SQLWherePredicate<T1>> predicateExpression, Property<T1, ?> thenProperty) {
        caseWhenBuilder.caseWhenColumn(filter -> {
            predicateExpression.apply(new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector.getTable(), new FilterContext(filter,sqlColumnAsSelector.getColumnAsSelector().getAsSelector().getEntityQueryExpressionBuilder()))));
        }, sqlColumnAsSelector.getTable(), EasyLambdaUtil.getPropertyName(thenProperty));
        return this;
    }

    public <T2> CaseWhen4JBuilder<T1, TR> caseWhen(SQLExpression1<SQLWherePredicate<T1>> predicateExpression, EntitySQLTableOwner<T2> tableOwner, Property<T2, ?> thenProperty) {
        caseWhenBuilder.caseWhenColumn(filter -> {
            predicateExpression.apply(new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector.getTable(), new FilterContext(filter,sqlColumnAsSelector.getColumnAsSelector().getAsSelector().getEntityQueryExpressionBuilder()))));
        }, tableOwner.getTable(), EasyLambdaUtil.getPropertyName(thenProperty));
        return this;
    }

    public CloneableSQLSegment elseEnd(Object elseValue) {
        return caseWhenBuilder.elseEnd(elseValue);
    }

    public CloneableSQLSegment elseEnd(Property<T1, ?> elseProperty) {
        return caseWhenBuilder.elseEndColumn(sqlColumnAsSelector.getTable(), EasyLambdaUtil.getPropertyName(elseProperty));
    }

    public <T2> CloneableSQLSegment elseEnd(EntitySQLTableOwner<T2> tableOwner, Property<T2, ?> elseProperty) {
        return caseWhenBuilder.elseEndColumn(tableOwner.getTable(), EasyLambdaUtil.getPropertyName(elseProperty));
    }
}