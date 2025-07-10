package com.easy.query.core.expression.parser.core.base.many;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2025/3/9 22:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubQueryPropertySelectorImpl implements SubQueryPropertySelector {
    private final TableAvailable table;

    public SubQueryPropertySelectorImpl(TableAvailable table) {
        this.table = table;
    }

    @Override
    public SubQueryProperty subQueryProperty(String property) {
        return new SubQueryPropertyImpl(table, property);
    }

    @Override
    public SubQueryProperty subQueryProperty(TableAvailable table, String property) {
        return new SubQueryPropertyImpl(table, property);
    }
}
