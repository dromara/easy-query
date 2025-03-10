package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/12/21 12:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLJsonFunc {
    SQLFunction jsonField(SQLExpression1<ColumnFuncSelector> sqlExpression);
    SQLFunction containsField(SQLExpression1<ColumnFuncSelector> sqlExpression);
}
