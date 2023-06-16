package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupBySelector<T1> {
    GroupBySelector<T1> getGroupBySelector();

    default TableAvailable getTable() {
        return getGroupBySelector().getTable();
    }

    default SQLKtGroupBySelector<T1> column(KProperty1<T1, ?> column) {
        getGroupBySelector().column(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }
    default SQLKtGroupBySelector<T1> columnConst(String columnConst) {
        getGroupBySelector().columnConst(columnConst);
        return this;
    }

    default SQLKtGroupBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getGroupBySelector().columnFunc(columnPropertyFunction);
        return this;
    }

    default <T2> SQLKtGroupBySelector<T2> then(SQLKtGroupBySelector<T2> sub) {
        return sub;
    }
}
