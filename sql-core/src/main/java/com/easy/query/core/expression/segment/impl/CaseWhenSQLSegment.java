package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.core.TableSQLSegment;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasyStringUtil;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * create time 2024/5/19 20:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenSQLSegment implements TableSQLSegment {
    private final Function<ToSQLContext, String> toSQLFunction;
    private final Consumer<TableVisitor> tableVisitorConsumer;

    public CaseWhenSQLSegment(Function<ToSQLContext, String> toSQLFunction, Consumer<TableVisitor> tableVisitorConsumer) {

        this.toSQLFunction = toSQLFunction;
        this.tableVisitorConsumer = tableVisitorConsumer;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String where = toSQLFunction.apply(toSQLContext);
        if (EasyStringUtil.isBlank(where)) {
            return "1 = 1";
        }
        return where;
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public CloneableSQLSegment cloneSQLColumnSegment() {
        return new CaseWhenSQLSegment(toSQLFunction, tableVisitorConsumer);
    }

    @Override
    public void accept(TableVisitor visitor) {
        tableVisitorConsumer.accept(visitor);
    }
}
