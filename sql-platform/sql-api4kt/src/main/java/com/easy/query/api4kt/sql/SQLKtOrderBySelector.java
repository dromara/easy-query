package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.sql.core.SQLLambdaKtNative;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/16 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderBySelector<T1> extends EntitySQLTableOwner<T1>, SQLLambdaKtNative<T1,SQLKtOrderBySelector<T1>> {
    ColumnOrderSelector<T1> getOrderBySelector();

    default TableAvailable getTable() {
        return getOrderBySelector().getTable();
    }

    default SQLKtOrderBySelector<T1> column(KProperty1<T1, ?> column) {
        getOrderBySelector().column(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * 请使用 sqlNativeSegment
     * @param columnConst
     * @return
     */
    @Deprecated
    default SQLKtOrderBySelector<T1> columnConst(String columnConst){
        return sqlNativeSegment(columnConst,c->{});
    }


    default SQLKtOrderBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getOrderBySelector().columnFunc(columnPropertyFunction);
        return this;
    }

    default <T2> SQLKtOrderBySelector<T2> then(SQLKtOrderBySelector<T2> sub) {
        return sub;
    }
}