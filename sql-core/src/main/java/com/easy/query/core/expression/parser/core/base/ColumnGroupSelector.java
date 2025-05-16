package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.SQLFxAvailable;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnGroupSelector<T1> extends EntitySQLTableOwner<T1>, SQLPropertyNative<ColumnGroupSelector<T1>>, SQLFxAvailable {
    GroupSelector getGroupSelector();
    default QueryRuntimeContext getRuntimeContext(){
        return getGroupSelector().getRuntimeContext();
    }
    ColumnGroupSelector<T1> column(String property);


    ColumnGroupSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    default <T2> ColumnGroupSelector<T2> then(ColumnGroupSelector<T2> sub) {
        return sub;
    }
}
