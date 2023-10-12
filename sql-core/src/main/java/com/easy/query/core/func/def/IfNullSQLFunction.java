package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;

/**
 * create time 2023/10/5 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class IfNullSQLFunction extends AbstractSQLFunction {
    private final TableAvailable table;
    private final String property;
    private final Object def;
    public IfNullSQLFunction(TableAvailable table, String property, Object def) {

        this.table = table;
        this.property = property;
        this.def = def;
    }

    @Override
    public String sqlSegment() {
        return "IFNULL({0},{1})";
    }

    @Override
    public int paramMarks() {
        return 2;
    }

    @Override
    public void consume0(SQLNativeChainExpressionContext context) {
        if (this.table == null) {
            context.expression(this.property);
        } else {
            context.expression(this.table, this.property);
        }
        context.value(this.def);
    }
}
