package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/16 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtColumnOnlySelector<T1> extends EntitySQLTableOwner<T1> {
    ColumnOnlySelector<T1> getColumnOnlySelector();

    default TableAvailable getTable() {
        return getColumnOnlySelector().getTable();
    }
    default SQLKtColumnOnlySelector<T1> columnKeys() {
        getColumnOnlySelector().columnKeys();
        return this;
    }


    default <TProperty> SQLKtColumnOnlySelector<T1> column(KProperty1<? super T1, TProperty> column) {
        getColumnOnlySelector().column(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default <TProperty> SQLKtColumnOnlySelector<T1> columnIgnore(KProperty1<? super T1, TProperty> column) {
        getColumnOnlySelector().columnIgnore(EasyKtLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtColumnOnlySelector<T1> columnAll() {
        getColumnOnlySelector().columnAll();
        return this;
    }

    default <T2> SQLKtColumnOnlySelector<T2> then(SQLKtColumnOnlySelector<T2> sub) {
        return sub;
    }
}
