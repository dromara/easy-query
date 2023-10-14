package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/14 23:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class UtcNowSQLFunction extends AbstractSQLFunction{
    public static final SQLFunction INSTANCE=new UtcNowSQLFunction();
    @Override
    public String sqlSegment() {
        return "UTC_TIMESTAMP()";
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {

    }
}
