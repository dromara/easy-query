package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/22 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelector {
    Selector getSelector();

    SQLSelector columns(SQLColumn<?>... column);
    SQLSelector column(SQLColumn<?> column);

    SQLSelector columnFunc(SQLColumnPropertyFunction sqlColumnPropertyFunction);

    SQLSelector columnIgnore(SQLColumn<?> column);

    SQLSelector columnAll(TableAvailable table);
}
