package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.builder.core.SQLColumnNative;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/25 17:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface UpdateSetSelector extends SQLColumnNative<UpdateSetSelector> {
    UpdateSetSelector column(TableAvailable table, String property);
    UpdateSetSelector columnAll(TableAvailable table);

    UpdateSetSelector columnIgnore(TableAvailable table, String property);
}
