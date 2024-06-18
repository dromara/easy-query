package com.easy.query.gauss.db.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class GaussDBDateTimeSQLFormatSQLFunction extends AbstractSQLFunction {
    private final TableAvailable table;
    private final String property;
    private final String format;

    public GaussDBDateTimeSQLFormatSQLFunction(TableAvailable table, String property, String format){
        this.table = table;
        this.property = property;
        this.format = format;
    }
    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return "TO_CHAR({0}, '{1}')";
    }

    @Override
    public int paramMarks() {
        return 2;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        if (table == null) {
            context.expression(property);
        } else {
            context.expression(table, property);
        }
        context.format(format);
    }
}
