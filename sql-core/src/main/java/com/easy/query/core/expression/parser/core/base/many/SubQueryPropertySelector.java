package com.easy.query.core.expression.parser.core.base.many;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2025/3/9 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryPropertySelector {
    /**
     * 支持多级比如[school.user.books]
     * @param property
     * @return
     */
    SubQueryProperty subQueryProperty(String property);
    SubQueryProperty subQueryProperty(TableAvailable table, String property);
}
