package com.easy.query.core.func;

import com.easy.query.core.expression.parser.core.SQLColumnOwner;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.def.ConcatSQLFunction;
import com.easy.query.core.func.def.ConcatWsSQLFunction;
import com.easy.query.core.func.def.DateTimeJavaFormatSQLFunction;
import com.easy.query.core.func.def.DateTimeSQLFormatSQLFunction;
import com.easy.query.core.func.def.IfNullSQLFunction;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/10/5 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLFunc implements SQLFunc{
    private TableAvailable getTable(SQLTableOwner tableOwner){
        return EasyObjectUtil.getValueOrNull(tableOwner, SQLTableOwner::getTable);
    }

    @Override
    public SQLFunction ifNull(SQLTableOwner tableOwner, String property, Object def) {
        return new IfNullSQLFunction(getTable(tableOwner),property,def);
    }

    @Override
    public SQLFunction dateTimeJavaFormat(SQLTableOwner tableOwner, String property, String javaFormat) {
        return new DateTimeJavaFormatSQLFunction(getTable(tableOwner),property,javaFormat);
    }

    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new DateTimeSQLFormatSQLFunction(getTable(tableOwner),property,format);
    }

    @Override
    public SQLFunction concat(SQLColumnOwner[] sqlColumns) {
        return new ConcatSQLFunction(sqlColumns);
    }

    @Override
    public SQLFunction concatWs(String separator, SQLColumnOwner[] sqlColumns) {
        return new ConcatWsSQLFunction(separator,sqlColumns);
    }
}
