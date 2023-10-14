package com.easy.query.pgsql.func;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2023/10/14 23:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class PgSQLNowSQLFunction extends AbstractSQLFunction {
    public static final SQLFunction INSTANCE=new PgSQLNowSQLFunction();
    @Override
    public String sqlSegment() {
        return "CURRENT_TIMESTAMP";
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {

    }
}
