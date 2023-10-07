package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class DateTimeSQLFormatSQLFunction implements SQLFunction {
    private final TableAvailable table;
    private final String property;
    private final String format;

    public DateTimeSQLFormatSQLFunction(TableAvailable table, String property, String format){
        this.table = table;
        this.property = property;
        this.format = format;
    }
    @Override
    public String sqlSegment() {
        return "DATE_FORMAT({0}, '{1}')";
    }

    @Override
    public void consume(SQLNativePropertyExpressionContext context) {
        context.keepStyle();
        if(table==null){
            context.expression(property);
        }else{
            context.expression(table,property);
        }
        context.format(format);
    }

    @Override
    public void consume(SQLAliasNativePropertyExpressionContext context) {
        context.keepStyle();
        if(table==null){
            context.expression(property);
        }else{
            context.expression(table,property);
        }
        context.format(format);
    }
}
