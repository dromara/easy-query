package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/23 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupSelector {

    GroupSelector column(TableAvailable table,String property);
    GroupSelector columnConst(String columnConst);

    GroupSelector columnFunc(TableAvailable table,ColumnPropertyFunction columnPropertyFunction);
}
