package com.easy.query.api4j.sql;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import com.easy.query.api4j.util.EasyLambdaUtil;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupBySelector<T1> {
    GroupBySelector<T1> getGroupBySelector();

    default TableAvailable getTable() {
        return getGroupBySelector().getTable();
    }

    default SQLGroupBySelector<T1> column(Property<T1, ?> column) {
        getGroupBySelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }
    default SQLGroupBySelector<T1> columnConst(String columnConst) {
        getGroupBySelector().columnConst(columnConst);
        return this;
    }

    default SQLGroupBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getGroupBySelector().columnFunc(columnPropertyFunction);
        return this;
    }

    default <T2> SQLGroupBySelector<T2> then(SQLGroupBySelector<T2> sub) {
        return sub;
    }
}
