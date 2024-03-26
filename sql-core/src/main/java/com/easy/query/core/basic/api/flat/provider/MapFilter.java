package com.easy.query.core.basic.api.flat.provider;

import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2024/3/26 15:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapFilter extends MapTable{
    WherePredicate<?> getWherePredicate(int tableIndex);
}
