package com.easy.query.core.expression.visitor;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Collections;
import java.util.Set;

/**
 * create time 2025/3/9 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EmptyTableVisitor implements TableVisitor {
    public static final TableVisitor DEFAULT = new EmptyTableVisitor();

    @Override
    public Set<TableAvailable> getTables() {
        return Collections.emptySet();
    }

    @Override
    public void visit(TableAvailable table) {

    }

    @Override
    public void addAll(TableVisitor tableVisitor) {

    }

    @Override
    public boolean containsTable(TableAvailable table) {
        return false;
    }
}
