package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupBySelector<T1>  {
    TableAvailable getTable();
    SQLGroupBySelector<T1> column(Property<T1,?> column);
    SQLGroupBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);
    default  <T2> SQLGroupBySelector<T2> and(SQLGroupBySelector<T2> sub){
        return sub;
    }
}
