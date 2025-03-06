package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;

/**
 * create time 2023/6/23 14:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderSelector extends SQLNative<OrderSelector>, RuntimeContextAvailable {
    EntityQueryExpressionBuilder getEntityQueryExpressionBuilder();
    void setAsc(boolean asc);
    boolean isAsc();
    OrderSelector column(TableAvailable table, String property);
    OrderSelector func(TableAvailable table, SQLFunction sqlFunction,boolean appendASC);

    OrderSelector columnFunc(TableAvailable table,ColumnPropertyFunction columnPropertyFunction);
//    OrderSelector columnConst(String columnConst);
}
