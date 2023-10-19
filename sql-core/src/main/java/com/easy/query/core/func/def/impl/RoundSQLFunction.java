package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2023/10/12 16:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class RoundSQLFunction extends AbstractSQLFunction {
    private final TableAvailable table;
    private final String property;
    private final int scale;

    public RoundSQLFunction(TableAvailable table, String property,int scale) {

        this.table = table;
        this.property = property;
        this.scale = scale;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return "ROUND({0},"+scale+")";
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
