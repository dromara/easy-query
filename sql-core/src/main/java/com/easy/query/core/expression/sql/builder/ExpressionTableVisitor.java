package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.visitor.TableVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * create time 2024/12/7 09:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionTableVisitor implements TableVisitor {
    private final Set<TableAvailable> tables=new HashSet<>();
    @Override
    public void visit(TableAvailable table) {
        if(table!=null){
            tables.add(table);
        }
    }
    public boolean containsTable(TableAvailable table){
        return tables.contains(table);
    }
}
