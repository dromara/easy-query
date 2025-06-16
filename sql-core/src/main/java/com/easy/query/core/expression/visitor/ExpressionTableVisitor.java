package com.easy.query.core.expression.visitor;

import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.parser.core.available.RelationTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.HashSet;
import java.util.Set;

/**
 * create time 2024/12/7 09:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionTableVisitor implements TableVisitor {
    private final Set<TableAvailable> tables = new HashSet<>();

    @Override
    public Set<TableAvailable> getTables() {
        return tables;
    }

    @Override
    public void visit(TableAvailable table) {
        if (table != null) {
            visit0(table);
        }
    }

    private void visit0(TableAvailable table){
        if(table instanceof RelationTableAvailable){
            RelationTableAvailable relationTableAvailable = (RelationTableAvailable) table;
            tables.add(table);
            RelationTableKey relationTableKey = relationTableAvailable.getRelationTableKey();
            TableAvailable previewTable = relationTableKey.getTable();
            visit0(previewTable);
        }else{
            tables.add(table);
        }
    }

    @Override
    public void addAll(TableVisitor tableVisitor) {
        tables.addAll(tableVisitor.getTables());
    }

    @Override
    public boolean containsTable(TableAvailable table) {
        return tables.contains(table);
    }
}
