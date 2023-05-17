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
public interface EasyQuerySQLBuilderProvider3<T1,T2,T3> extends EasyQuerySQLBuilderProvider2<T1,T2> {
    SQLGroupBySelector<T3> getSQLGroupColumnSelector3();
    SQLColumnSelector<T3> getSQLOrderColumnSelector3(boolean asc);
    SQLWherePredicate<T3> getSQLWherePredicate3();
    SQLAggregatePredicate<T3> getSQLAggregatePredicate3();
    SQLWherePredicate<T3> getSQLOnPredicate3();

    SQLColumnSelector<T3> getSQLColumnSelector3(SQLBuilderSegment sqlSegment0Builder);
    <TR> SQLColumnAsSelector<T3,TR> getSQLColumnAsSelector3(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
//    <TR> ColumnAsSelector<T3,TR> getAutoSqlColumnAsSelector3(SqlBuilderSegment sqlSegment0Builder,Class<TR> resultClass);
    <TR> SQLColumnResultSelector<T3,TR> getSQLColumnResultSelector3(SQLBuilderSegment sqlSegment0Builder);
}
