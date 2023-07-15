package com.easy.query.api4j.sql;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnUpdateSetSelector;

/**
 * create time 2023/6/16 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnSetSelector<T1> extends EntitySQLTableOwner<T1> {
    ColumnUpdateSetSelector<T1> getColumnSetSelector();

    default TableAvailable getTable() {
        return getColumnSetSelector().getTable();
    }

    default SQLColumnSetSelector<T1> column(Property<T1, ?> column) {
        getColumnSetSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnSetSelector<T1> columnIgnore(Property<T1, ?> column) {
        getColumnSetSelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnSetSelector<T1> columnAll() {
        getColumnSetSelector().columnAll();
        return this;
    }

    default <T2> SQLColumnSetSelector<T2> then(SQLColumnSetSelector<T2> sub) {
        return sub;
    }
}
