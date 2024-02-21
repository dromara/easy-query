package com.easy.query.core.expression.builder.core;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2024/2/21 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SelectorColumn<TChain> {
    TChain column(TableAvailable table, String property);
    TChain columnIgnore(TableAvailable table, String property);

    TChain columnIfAbsent(TableAvailable table, String property);
}
