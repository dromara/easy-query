package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2023/10/28 15:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptySQLFunction extends AbstractSQLFunction {
    private final String property;

    public EmptySQLFunction(String property) {

        this.property = property;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return "({0} IS NULL OR {0} = '')";
    }

    @Override
    public int paramMarks() {
        return 1;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        context.expression(this.property);
    }
}
