package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SqlGroupBySelector<T1>  {
    TableAvailable getTable();
    SqlGroupBySelector<T1> column(Property<T1,?> column);
    default  <T2> SqlGroupBySelector<T2> and(SqlGroupBySelector<T2> sub){
        return sub;
    }
}
