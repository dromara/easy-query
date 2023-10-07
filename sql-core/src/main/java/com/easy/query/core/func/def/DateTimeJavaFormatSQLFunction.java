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
public class DateTimeJavaFormatSQLFunction implements SQLFunction {
    private final TableAvailable table;
    private final String property;
    private final String javaFormat;

    public DateTimeJavaFormatSQLFunction(TableAvailable table, String property, String javaFormat){
        this.table = table;
        this.property = property;
        this.javaFormat = javaFormat;
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
        //%Y-%m-%d %H:%i:%S
        String format = this.javaFormat
                .replace("yyyy", "%Y")
                .replace("MM", "%m")
                .replace("dd", "%d")
                .replace("HH", "%H")
                .replace("mm", "%i")
                .replace("ss", "%S");
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
        //%Y-%m-%d %H:%i:%S
        String format = this.javaFormat
                .replace("yyyy", "%Y")
                .replace("MM", "%m")
                .replace("dd", "%d")
                .replace("HH", "%H")
                .replace("mm", "%i")
                .replace("ss", "%S");
        context.format(format);
    }
}
