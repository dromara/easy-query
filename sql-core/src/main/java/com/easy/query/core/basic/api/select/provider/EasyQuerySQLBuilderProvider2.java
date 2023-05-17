package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @author xuejiaming
 */
public interface EasyQuerySQLBuilderProvider2<T1,T2> extends EasyQuerySQLBuilderProvider<T1> {
    SQLGroupBySelector<T2> getSQLGroupColumnSelector2();
    SQLColumnSelector<T2> getSQLOrderColumnSelector2(boolean asc);
    SQLWherePredicate<T2> getSQLWherePredicate2();
    SQLAggregatePredicate<T2> getSQLAggregatePredicate2();
    SQLWherePredicate<T2> getSQLOnPredicate2();

    SQLColumnSelector<T2> getSQLColumnSelector2(SQLBuilderSegment sqlSegment0Builder);
    <TR> SQLColumnAsSelector<T2,TR> getSQLColumnAsSelector2(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SQLColumnResultSelector<T2,TR> getSQLColumnResultSelector2(SQLBuilderSegment sqlSegment0Builder);
}
