package com.easy.query.api.proxy.extension.window;

import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/10/11 10:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class OverExpression {
    private final SQLFunc sqlFunc;
    private final List<SQLFunction> orderFunctions;
    private final List<PropTypeColumn<?>> columns;

    public OverExpression(SQLFunc sqlFunc, List<SQLFunction> orderCollectors,List<PropTypeColumn<?>> columns){
        this.sqlFunc = sqlFunc;
        this.orderFunctions = orderCollectors;
        this.columns = columns;
    }
    public OverExpression partitionBy(PropTypeColumn<?>... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }
    /**
     * 排序列 ASC
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    public <TProperty> OverExpression orderBy(PropTypeColumn<TProperty> propTypeColumn) {
        return orderBy(true, propTypeColumn);
    }

    /**
     * 排序列 ASC
     * @param condition 是否生效
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    public <TProperty> OverExpression orderBy(boolean condition, PropTypeColumn<TProperty> propTypeColumn){
        if(condition){
            SQLFunction orderFunction = sqlFunc.anySQLFunction("{0} ASC", c -> {
                PropTypeColumn.columnFuncSelector(c, propTypeColumn);
            });
            this.orderFunctions.add(orderFunction);
        }
        return this;
    }
    /**
     * 排序列 DESC
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    public <TProperty> OverExpression orderByDescending(PropTypeColumn<TProperty> propTypeColumn) {
        return orderByDescending(true, propTypeColumn);
    }

    /**
     * 排序列 DESC
     * @param condition 是否生效
     * @param propTypeColumn
     * @return
     * @param <TProperty>
     */
    public <TProperty> OverExpression orderByDescending(boolean condition, PropTypeColumn<TProperty> propTypeColumn){

        if(condition){
            SQLFunction orderFunction = sqlFunc.anySQLFunction("{0} DESC", c -> {
                PropTypeColumn.columnFuncSelector(c, propTypeColumn);
            });
            this.orderFunctions.add(orderFunction);
        }
        return this;
    }
}
