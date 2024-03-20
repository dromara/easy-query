package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.segment.ColumnSegment;

import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable1<T1> {

    /**
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    ClientQueryable<T1> select(SQLExpression1<ColumnSelector<T1>> selectExpression);

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
    <TR> ClientQueryable<TR> select(Class<TR> resultClass);

    /**
     * 自动构建dto结果会自动执行include,如果您有独立的include那么会覆盖掉之前的以当前的为准
     * @param resultClass 返回的结果类型
     * @return 返回一个可以获取结果的表达式
     * @param <TR> 返回结果的泛型类型
     */
    <TR> Query<TR> selectAutoInclude(Class<TR> resultClass);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     * @param resultClass
     * @param selectExpression
     * @return
     * @param <TR>
     */
    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression1<ColumnAsSelector<T1, TR>> selectExpression);
    default ClientQueryable<T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    ClientQueryable<T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

}
