package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;

/**
 * @FileName: EasyQueryLambdaBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/12 10:02
 * @author xuejiaming
 */
public interface EasyQuerySQLBuilderProvider1<T1> {
    SQLGroupBySelector<T1> getSQLGroupColumnSelector1();
    SQLColumnSelector<T1> getSQLOrderColumnSelector1(boolean asc);
    SQLWherePredicate<T1> getSQLWherePredicate1();
    SQLWherePredicate<T1> getSQLAllPredicate1();
    SQLAggregatePredicate<T1> getSQLAggregatePredicate1();
    SQLWherePredicate<T1> getSQLOnPredicate1();

    SQLColumnSelector<T1> getSQLColumnSelector1(SQLBuilderSegment sqlSegment0Builder);
    <TR> SQLColumnAsSelector<T1,TR> getSQLColumnAsSelector1(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SQLColumnAsSelector<T1,TR> getSQLAutoColumnAsSelector1(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SQLColumnResultSelector<T1,TR> getSQLColumnResultSelector1(SQLBuilderSegment sqlSegment0Builder);
}
