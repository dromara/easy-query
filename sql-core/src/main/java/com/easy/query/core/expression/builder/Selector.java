package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/22 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selector extends SQLNative<Selector>, RuntimeContextAvailable {

    /**
     * 快速选择之前group的列,不需要重新再写一遍
     * @param index
     * @return
     */
    Selector groupKeys(int index);
    Selector column(TableAvailable table, String property);

    Selector columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction);
//    Selector columnFunc(TableAvailable table, SQLFunction sqlFunction);

    Selector columnIgnore(TableAvailable table, String property);

    Selector columnAll(TableAvailable table);
}
