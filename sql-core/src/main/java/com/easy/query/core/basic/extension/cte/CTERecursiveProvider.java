package com.easy.query.core.basic.extension.cte;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.base.tree.TreeCTEOption;

/**
 * create time 2026/2/25 08:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CTERecursiveProvider {
   <T> ClientQueryable<T> createCteQueryable(ClientQueryable<T> fromQueryable,ClientQueryable<T> queryable, TreeCTEOption treeCTEOption, String[] codeProperties, String[] parentCodeProperties);
}
