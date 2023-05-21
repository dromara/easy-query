package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 * @author xuejiaming
 */
public interface SQLColumnSelector<T1> {
    TableAvailable getTable();
    SQLColumnSelector<T1> column(Property<T1,?> column);
    SQLColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);
    SQLColumnSelector<T1> columnIgnore(Property<T1,?> column);
    SQLColumnSelector<T1> columnAll();
   default  <T2> SQLColumnSelector<T2> then(SQLColumnSelector<T2> sub){
        return sub;
    }
}
