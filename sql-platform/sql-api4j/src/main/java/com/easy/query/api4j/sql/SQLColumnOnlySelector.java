package com.easy.query.api4j.sql;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;

/**
 * create time 2023/6/16 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnOnlySelector<T1> extends EntitySQLTableOwner<T1> {
    ColumnOnlySelector<T1> getColumnOnlySelector();

    default TableAvailable getTable() {
        return getColumnOnlySelector().getTable();
    }

    default SQLColumnOnlySelector<T1> columnKeys() {
        getColumnOnlySelector().columnKeys();
        return this;
    }
    default <TProperty> SQLColumnOnlySelector<T1> column(Property<T1, TProperty> column) {
        getColumnOnlySelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default <TProperty> SQLColumnOnlySelector<T1> columnIgnore(Property<T1, TProperty> column) {
        getColumnOnlySelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnOnlySelector<T1> columnAll() {
        getColumnOnlySelector().columnAll();
        return this;
    }

    default <T2> SQLColumnOnlySelector<T2> then(SQLColumnOnlySelector<T2> sub) {
        return sub;
    }
}
