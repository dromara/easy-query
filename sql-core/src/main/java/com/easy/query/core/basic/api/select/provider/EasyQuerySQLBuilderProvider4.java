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
public interface EasyQuerySQLBuilderProvider4<T1,T2,T3,T4> extends EasyQuerySQLBuilderProvider3<T1,T2,T3> {
    SQLGroupBySelector<T4> getSQLGroupColumnSelector4();
    SQLColumnSelector<T4> getSQLOrderColumnSelector4(boolean asc);
    SQLWherePredicate<T4> getSQLWherePredicate4();
    SQLAggregatePredicate<T4> getSQLAggregatePredicate4();
    SQLWherePredicate<T4> getSQLOnPredicate4();

    SQLColumnSelector<T4> getSQLColumnSelector4(SQLBuilderSegment sqlSegment0Builder);
    <TR> SQLColumnAsSelector<T4,TR> getSQLColumnAsSelector4(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SQLColumnResultSelector<T4,TR> getSQLColumnResultSelector4(SQLBuilderSegment sqlSegment0Builder);
}
