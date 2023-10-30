package com.easy.query.api4j.sql;

import com.easy.query.api4j.sql.core.SQLLambdaNative;
import com.easy.query.api4j.sql.core.available.LambdaSQLFuncAvailable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/6/16 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderBySelector<T1> extends EntitySQLTableOwner<T1>, LambdaSQLFuncAvailable<T1>, SQLLambdaNative<T1,SQLOrderBySelector<T1>> {
    ColumnOrderSelector<T1> getOrderBySelector();

    default TableAvailable getTable() {
        return getOrderBySelector().getTable();
    }

    default QueryRuntimeContext getRuntimeContext() {
        return getOrderBySelector().getRuntimeContext();
    }

    default <TProperty> SQLOrderBySelector<T1> column(Property<T1, TProperty> column) {
        getOrderBySelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLOrderBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getOrderBySelector().columnFunc(columnPropertyFunction);
        return this;
    }

    default <T2> SQLOrderBySelector<T2> then(SQLOrderBySelector<T2> sub) {
        return sub;
    }
}