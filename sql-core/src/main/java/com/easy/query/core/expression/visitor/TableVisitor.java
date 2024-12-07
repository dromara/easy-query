package com.easy.query.core.expression.visitor;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2024/12/7 09:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableVisitor {
    void visit(@Nullable TableAvailable table);
}
