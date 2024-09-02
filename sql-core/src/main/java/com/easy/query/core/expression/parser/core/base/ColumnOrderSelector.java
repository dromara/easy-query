package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
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

    ColumnOrderSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    /**
     * 请使用 sqlNativeSegment
     * @param columnConst
     * @return
     */
    @Deprecated
    default ColumnOrderSelector<T1> columnConst(String columnConst){
        return sqlNativeSegment(columnConst,c->{});
    }
    default <T2> ColumnOrderSelector<T2> then(ColumnOrderSelector<T2> sub) {
        return sub;
    }
}
