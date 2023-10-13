package com.easy.query.core.func;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbsSQLFunction;
import com.easy.query.core.func.def.ConcatJoinSQLFunction;
import com.easy.query.core.func.def.ConcatSQLFunction;
import com.easy.query.core.func.def.DateTimeJavaFormatSQLFunction;
import com.easy.query.core.func.def.DateTimeSQLFormatSQLFunction;
import com.easy.query.core.func.def.IfNullSQLFunction;
import com.easy.query.core.func.def.RoundSQLFunction;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.List;

/**
 * create time 2023/10/5 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLFunc implements SQLFunc {
    private TableAvailable getTable(SQLTableOwner tableOwner) {
        return EasyObjectUtil.getValueOrNull(tableOwner, SQLTableOwner::getTable);
    }

    @Override
    public SQLFunction ifNull(List<ColumnExpression> columnExpressions) {
        return new IfNullSQLFunction(columnExpressions);
    }

    @Override
    public SQLFunction abs(SQLTableOwner tableOwner, String property) {
        return new AbsSQLFunction(getTable(tableOwner), property);
    }

    @Override
    public SQLFunction round(SQLTableOwner tableOwner, String property, int scale) {
        return new RoundSQLFunction(getTable(tableOwner), property, scale);
    }

    @Override
    public SQLFunction dateTimeJavaFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new DateTimeJavaFormatSQLFunction(getTable(tableOwner), property, javaFormat);
    }

    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new DateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new ConcatSQLFunction(concatExpressions);
    }

    @Override
    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
        return new ConcatJoinSQLFunction(separator, concatExpressions);
    }

}
