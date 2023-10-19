package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2023/10/12 15:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class AbsSQLFunction extends AbstractSQLFunction {
    private final TableAvailable table;
    private final String property;

    public AbsSQLFunction(TableAvailable table, String property) {

        this.table = table;
        this.property = property;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return "ABS({0})";
    }

    @Override
    public int paramMarks() {
        return 1;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        if (this.table == null) {
            context.expression(this.property);
        } else {
            context.expression(this.table, this.property);
        }
    }
}
