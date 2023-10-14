package com.easy.query.h2.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.def.DateTimeJavaFormatSQLFunction;

/**
 * create time 2023/10/14 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2DateTimeJavaFormatSQLFunction extends DateTimeJavaFormatSQLFunction {
    public H2DateTimeJavaFormatSQLFunction(TableAvailable table, String property, String javaFormat) {
        super(table, property, javaFormat);
    }
}
