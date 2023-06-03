package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import kotlin.reflect.KProperty1;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface SQLKtColumnResultSelector<T1, TMember> {
    ColumnResultSelector<T1> getColumnResultSelector();

    default TableAvailable getTable() {
        return getColumnResultSelector().getTable();
    }

    default void column(KProperty1<T1, TMember> column) {
        getColumnResultSelector().column(EasyKtLambdaUtil.getPropertyName(column));
    }
}
