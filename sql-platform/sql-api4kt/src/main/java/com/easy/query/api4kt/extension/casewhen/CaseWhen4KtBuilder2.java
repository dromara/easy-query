package com.easy.query.api4kt.extension.casewhen;

import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/7/3 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhen4KtBuilder2<T1, T2, TR> {
    private final SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector1;
    private final CaseWhenBuilder caseWhenBuilder;
    private final SQLKtColumnAsSelector<T2, TR> sqlColumnAsSelector2;

    public CaseWhen4KtBuilder2(SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector1, SQLKtColumnAsSelector<T2, TR> sqlColumnAsSelector2) {
        this.sqlColumnAsSelector1 = sqlColumnAsSelector1;
        this.sqlColumnAsSelector2 = sqlColumnAsSelector2;
        this.caseWhenBuilder = new CaseWhenBuilder(sqlColumnAsSelector1.getRuntimeContext(), sqlColumnAsSelector1.getExpressionContext());
    }

    public CaseWhen4KtBuilder2<T1, T2, TR> caseWhen(SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> predicateExpression, Object then) {
        caseWhenBuilder.caseWhen(filter -> {
            FilterContext filterContext = new FilterContext(filter);
            predicateExpression.apply(new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector1.getTable(), filterContext)), new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector2.getTable(), filterContext)));
        }, then);
        return this;
    }

    public CaseWhen4KtBuilder2<T1, T2, TR> caseWhen(SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> predicateExpression, KProperty1<? super T1, ?> thenProperty) {
        caseWhenBuilder.caseWhenColumn(filter -> {
            FilterContext filterContext = new FilterContext(filter);
            predicateExpression.apply(new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector1.getTable(), filterContext)), new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector2.getTable(), filterContext)));
        }, sqlColumnAsSelector1.getTable(), EasyKtLambdaUtil.getPropertyName(thenProperty));
        return this;
    }
    public <T3> CaseWhen4KtBuilder2<T1, T2, TR> caseWhen(SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> predicateExpression, EntitySQLTableOwner<T3> sqlTableOwner, KProperty1<? super T3, ?> thenProperty) {
        caseWhenBuilder.caseWhenColumn(filter -> {
            FilterContext filterContext = new FilterContext(filter);
            predicateExpression.apply(new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector1.getTable(), filterContext)), new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector2.getTable(), filterContext)));
        }, sqlTableOwner.getTable(), EasyKtLambdaUtil.getPropertyName(thenProperty));
        return this;
    }

    public CloneableSQLSegment elseEnd(Object elseValue) {
        return caseWhenBuilder.elseEnd(elseValue);
    }

    public CloneableSQLSegment elseEnd(KProperty1<? super T1, ?> elseProperty) {
        return caseWhenBuilder.elseEndColumn(sqlColumnAsSelector1.getTable(), EasyKtLambdaUtil.getPropertyName(elseProperty));
    }

    public <T3> CloneableSQLSegment elseEnd(EntitySQLTableOwner<T3> sqlTableOwner, KProperty1<? super T3, ?> elseProperty) {
        return caseWhenBuilder.elseEndColumn(sqlTableOwner.getTable(),EasyKtLambdaUtil.getPropertyName(elseProperty));
    }
}