package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/14 23:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class NowSQLFunction extends AbstractSQLFunction{
    public static final SQLFunction INSTANCE=new NowSQLFunction();
    @Override
    public String sqlSegment() {
        return "NOW()";
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {

    }
}
