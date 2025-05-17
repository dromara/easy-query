package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.SQLFxAvailable;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;

/**
 * create time 2023/6/16 21:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnOrderSelector<T1> extends EntitySQLTableOwner<T1>, SQLPropertyNative<ColumnOrderSelector<T1>>, SQLFxAvailable {
    OrderSelector getOrderSelector();
    void setAsc(boolean asc);
    default QueryRuntimeContext getRuntimeContext(){
        return getOrderSelector().getRuntimeContext();
    }

    ColumnOrderSelector<T1> column(String property);

    default <T2> ColumnOrderSelector<T2> then(ColumnOrderSelector<T2> sub) {
        return sub;
    }
}
