package com.easy.query.oracle.func;

import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2023/10/14 23:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleNowSQLFunction extends AbstractSQLFunction {
    public static final SQLFunction INSTANCE=new OracleNowSQLFunction();
    @Override
    public String sqlSegment() {
        return "SYSTIMESTAMP";
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {

    }
}
