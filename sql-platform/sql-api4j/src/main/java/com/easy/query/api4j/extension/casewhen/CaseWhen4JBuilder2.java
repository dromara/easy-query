package com.easy.query.api4j.extension.casewhen;

import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;

/**
 * create time 2023/7/3 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhen4JBuilder2<T1,T2,TR> {
    private final SQLColumnAsSelector<T1, TR> sqlColumnAsSelector1;
    private final SQLColumnAsSelector<T2, TR> sqlColumnAsSelector2;
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhen4JBuilder2(SQLColumnAsSelector<T1, TR> sqlColumnAsSelector1,SQLColumnAsSelector<T2, TR> sqlColumnAsSelector2){
        this.sqlColumnAsSelector1 = sqlColumnAsSelector1;
        this.caseWhenBuilder=new CaseWhenBuilder(sqlColumnAsSelector1.getRuntimeContext(),sqlColumnAsSelector1.getExpressionContext());
        this.sqlColumnAsSelector2 = sqlColumnAsSelector2;
    }
    public CaseWhen4JBuilder2<T1,T2,TR> caseWhen(SQLExpression2<SQLWherePredicate<T1>,SQLWherePredicate<T2>> predicateExpression, Object then){
        caseWhenBuilder.caseWhen(filter->{
            FilterContext filterContext = new FilterContext(filter);
            predicateExpression.apply(new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector1.getTable(),filterContext)),new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector2.getTable(),filterContext)));
        },then);
        return this;
    }
    public CaseWhen4JBuilder2<T1,T2,TR> caseWhen(SQLExpression2<SQLWherePredicate<T1>,SQLWherePredicate<T2>> predicateExpression, Property<T1, ?> thenProperty){
        caseWhenBuilder.caseWhenColumn(filter->{
            FilterContext filterContext = new FilterContext(filter);
            predicateExpression.apply(new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector1.getTable(),filterContext)),new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector2.getTable(),filterContext)));
        },sqlColumnAsSelector1.getTable(), EasyLambdaUtil.getPropertyName(thenProperty));
        return this;
    }
    public <T3> CaseWhen4JBuilder2<T1,T2,TR> caseWhen(SQLExpression2<SQLWherePredicate<T1>,SQLWherePredicate<T2>> predicateExpression, EntitySQLTableOwner<T3> sqlTableOwner, Property<T3, ?> thenProperty){
        caseWhenBuilder.caseWhenColumn(filter->{
            FilterContext filterContext = new FilterContext(filter);
            predicateExpression.apply(new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector1.getTable(),filterContext)),new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector2.getTable(),filterContext)));
        },sqlTableOwner.getTable(), EasyLambdaUtil.getPropertyName(thenProperty));
        return this;
    }
    public CloneableSQLSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
    public CloneableSQLSegment elseEnd(Property<T1, ?> elseProperty){
        return caseWhenBuilder.elseEndColumn(sqlColumnAsSelector1.getTable(),EasyLambdaUtil.getPropertyName(elseProperty));
    }
    public <T3> CloneableSQLSegment elseEnd(EntitySQLTableOwner<T3> sqlTableOwner, Property<T3, ?> elseProperty){
        return caseWhenBuilder.elseEndColumn(sqlTableOwner.getTable(),EasyLambdaUtil.getPropertyName(elseProperty));
    }
}