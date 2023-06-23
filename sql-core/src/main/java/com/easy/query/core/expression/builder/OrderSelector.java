package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/23 14:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderSelector {

    OrderSelector column(TableAvailable table, String property);

    OrderSelector columnFunc(TableAvailable table,ColumnPropertyFunction columnPropertyFunction);
    OrderSelector columnConst(TableAvailable table,String columnConst);

    OrderSelector columnIgnore(TableAvailable table,String property);

    OrderSelector columnAll(TableAvailable table);
}
