package com.easy.query.clickhouse.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2025/7/16 21:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseRandomSQLFunction extends AbstractSQLFunction {
    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return "random()";
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {

    }
}
