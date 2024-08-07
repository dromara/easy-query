package com.easy.query.db2.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.AbstractSQLFunction;

/**
 * create time 2023/10/14 23:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class DB2UtcNowSQLFunction extends AbstractSQLFunction {
    public static final SQLFunction INSTANCE=new DB2UtcNowSQLFunction();
    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return "(CURRENT_TIMESTAMP AT TIME ZONE 'UTC')";
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {

    }
}
