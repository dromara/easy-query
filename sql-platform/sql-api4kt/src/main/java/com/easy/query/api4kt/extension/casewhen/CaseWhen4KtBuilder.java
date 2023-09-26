package com.easy.query.api4kt.extension.casewhen;

import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
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
public class CaseWhen4KtBuilder<T1,TR> {
    private final SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector;
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhen4KtBuilder(SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector){
        this.sqlColumnAsSelector = sqlColumnAsSelector;
        this.caseWhenBuilder=new CaseWhenBuilder(sqlColumnAsSelector.getRuntimeContext(),sqlColumnAsSelector.getExpressionContext());
    }
    public CaseWhen4KtBuilder<T1,TR> caseWhen(SQLExpression1<SQLKtWherePredicate<T1>> predicateExpression, Object then){
        caseWhenBuilder.caseWhen(filter->{
            predicateExpression.apply(new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector.getTable(),filter)));
        },then);
        return this;
    }
    public CaseWhen4KtBuilder<T1,TR> caseWhen(SQLExpression1<SQLKtWherePredicate<T1>> predicateExpression, KProperty1<? super T1,?> thenProperty){
        caseWhenBuilder.caseWhenColumn(filter->{
            predicateExpression.apply(new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector.getTable(),filter)));
        },sqlColumnAsSelector.getTable(), EasyKtLambdaUtil.getPropertyName(thenProperty));
        return this;
    }
    public <T2> CaseWhen4KtBuilder<T1,TR> caseWhen(SQLExpression1<SQLKtWherePredicate<T1>> predicateExpression, EntitySQLTableOwner<T2> sqlTableOwner, KProperty1<? super T2,?> thenProperty){
        caseWhenBuilder.caseWhenColumn(filter->{
            predicateExpression.apply(new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector.getTable(),filter)));
        },sqlTableOwner.getTable(), EasyKtLambdaUtil.getPropertyName(thenProperty));
        return this;
    }
    public CloneableSQLSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
    public CloneableSQLSegment elseEnd(KProperty1<? super T1,?> elseProperty){
        return caseWhenBuilder.elseEndColumn(sqlColumnAsSelector.getTable(), EasyKtLambdaUtil.getPropertyName(elseProperty));
    }
    public <T2> CloneableSQLSegment elseEnd(EntitySQLTableOwner<T2> sqlTableOwner, KProperty1<? super T2,?> elseProperty){
        return caseWhenBuilder.elseEndColumn(sqlTableOwner.getTable(),EasyKtLambdaUtil.getPropertyName(elseProperty));
    }
}