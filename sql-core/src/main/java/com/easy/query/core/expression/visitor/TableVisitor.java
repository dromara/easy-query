package com.easy.query.core.expression.visitor;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Set;

/**
 * create time 2024/12/7 09:04
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableVisitor {
    Set<TableAvailable> getTables();

    void visit(@Nullable TableAvailable table);

    void addAll(TableVisitor tableVisitor);
    boolean containsTable(TableAvailable table);
}
