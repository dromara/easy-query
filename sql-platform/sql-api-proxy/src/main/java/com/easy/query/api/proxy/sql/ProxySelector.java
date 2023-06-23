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
public interface ProxySelector {
    Selector getSelector();

    ProxySelector columns(SQLColumn<?>... column);
    ProxySelector column(SQLColumn<?> column);

    ProxySelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction);

    ProxySelector columnIgnore(SQLColumn<?> column);

    ProxySelector columnAll(TableAvailable table);
}
