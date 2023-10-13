package com.easy.query.api4j.sql;

import com.easy.query.api4j.sql.core.SQLLambdaNative;
import com.easy.query.api4j.sql.core.available.LambdaSQLFuncAvailable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface SQLColumnSelector<T1> extends EntitySQLTableOwner<T1>, LambdaSQLFuncAvailable<T1>, SQLLambdaNative<T1,SQLColumnSelector<T1>> {
    ColumnSelector<T1> getColumnSelector();

    default TableAvailable getTable() {
        return getColumnSelector().getTable();
    }

    default QueryRuntimeContext getRuntimeContext() {
        return getColumnSelector().getRuntimeContext();
    }

    default <TProperty> SQLColumnSelector<T1> columns(Collection<Property<T1, TProperty>> columns) {
        if(EasyCollectionUtil.isNotEmpty(columns)){
            for (Property<T1, TProperty> column : columns) {
                this.column(column);
            }
        }
        return this;
    }

    default <TProperty> SQLColumnSelector<T1> column(Property<T1, TProperty> column) {
        getColumnSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    /**
     * 请使用 sqlNativeSegment
     * @param sqlSegment
     * @return
     */
    @Deprecated
    default SQLColumnSelector<T1> columnConst(String sqlSegment){
        return sqlNativeSegment(sqlSegment,c->{});
    }

    default SQLColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getColumnSelector().columnFunc(columnPropertyFunction);
        return this;
    }

    default <TProperty> SQLColumnSelector<T1> columnIgnore(Property<T1, TProperty> column) {
        getColumnSelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnSelector<T1> columnAll() {
        getColumnSelector().columnAll();
        return this;
    }

    default <T2> SQLColumnSelector<T2> then(SQLColumnSelector<T2> sub) {
        return sub;
    }
}
