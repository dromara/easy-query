package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.ColumnSegment;

import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectable1<T1> {

    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    Queryable<T1> select(SQLExpression1<SQLColumnSelector<T1>> selectExpression);

    /**
     * 将当前T1对象转成TR对象，select会将T1属性所对应的列名映射到TR对象的列名上(忽略大小写)
     * T1.property1列名叫做column1,T1.property2列名叫做column2，TR.property3的列名也叫column1
     * 那么生成的sql为:select column1 from t1
     * 如果当前存在join，那么join的子表一律不会映射到resultClass上,如果需要那么请手动调用双参数select
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    <TR> Queryable<TR> select(Class<TR> resultClass);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     * 多次select会在前一次基础上进行对上次结果进行匿名表处理
     *
     * @param resultClass
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression1<SQLColumnAsSelector<T1, TR>> selectExpression);

    default Queryable<T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    Queryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

}
