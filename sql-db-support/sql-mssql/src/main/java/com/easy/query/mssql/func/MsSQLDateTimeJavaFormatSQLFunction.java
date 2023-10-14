package com.easy.query.mssql.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.def.AbstractSQLFunction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLDateTimeJavaFormatSQLFunction extends AbstractSQLFunction {
    private final TableAvailable table;
    private final String property;
    private final String javaFormat;

    public MsSQLDateTimeJavaFormatSQLFunction(TableAvailable table, String property, String javaFormat) {
        this.table = table;
        this.property = property;
        this.javaFormat = javaFormat;
    }

    @Override
    public String sqlSegment() {
        return getSQLSegment();
    }

    @Override
    public int paramMarks() {
        return 1;
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        if (table == null) {
            context.expression(property);
        } else {
            context.expression(table, property);
        }
    }

    /**
     * 代码参考 <a href="https://github.com/dotnetcore/FreeSql">FreeSQL</a>
     *
     * @return
     */
    public String getSQLSegment() {
        if (this.javaFormat != null) {

            String format = this.javaFormat;
            switch (format) {
                case "yyyy-MM-dd HH:mm:ss":
                    return "convert(char(19), {0}, 120)";
                case "yyyy-MM-dd HH:mm":
                    return "substring(convert(char(19), {0}, 120), 1, 16)";
                case "yyyy-MM-dd HH":
                    return "substring(convert(char(19), {0}, 120), 1, 13)";
                case "yyyy-MM-dd":
                    return "convert(char(10), {0}, 23)";
                case "yyyy-MM":
                    return "substring(convert(char(10), {0}, 23), 1, 7)";
                case "yyyyMMdd":
                    return "convert(char(8), {0}, 112)";
                case "yyyyMM":
                    return "substring(convert(char(8), {0}, 112), 1, 6)";
                case "yyyy":
                    return "substring(convert(char(8), {0}, 112), 1, 4)";
                case "HH:mm:ss":
                    return "convert(char(8), {0}, 24)";
            }

            return replaceFormat(format);
        }
        return "convert(varchar, {0}, 121)";
    }

    protected String replaceFormat(String format) {
        String pattern = "(yyyy|yy|MM|M|dd|d|HH|H|hh|h|mm|m|ss|s|tt|t)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(format);

        boolean isMatched = false;
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            isMatched=true;
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    matcher.appendReplacement(result, "' + substring(convert(char(8), {left}, 112), 1, 4)'");
                    break;
                case "yy":
                    matcher.appendReplacement(result, "' + substring(convert(char(6), {left}, 12), 1, 2)'");
                    break;
                case "MM":
                    matcher.appendReplacement(result, "' + substring(convert(char(6), {left}, 12), 3, 2)'");
                    break;
                case "M":
                    matcher.appendReplacement(result, "' + case when substring(convert(char(6), {left}, 12), 3, 1) = '0' then substring(convert(char(6), {left}, 12), 4, 1) else substring(convert(char(6), {left}, 12), 3, 2) end'");
                    break;
                case "dd":
                    matcher.appendReplacement(result, "' + substring(convert(char(6), {left}, 12), 5, 2)'");
                    break;
                case "d":
                    matcher.appendReplacement(result, "' + case when substring(convert(char(6), {left}, 12), 5, 1) = '0' then substring(convert(char(6), {left}, 12), 6, 1) else substring(convert(char(6), {left}, 12), 5, 2) end'");
                    break;
                case "HH":
                    matcher.appendReplacement(result, "' + substring(convert(char(8), {left}, 24), 1, 2)'");
                    break;
                case "H":
                    matcher.appendReplacement(result, "' + case when substring(convert(char(8), {left}, 24), 1, 1) = '0' then substring(convert(char(8), {left}, 24), 2, 1) else substring(convert(char(8), {left}, 24), 1, 2) end'");
                    break;
                case "hh":
                    matcher.appendReplacement(result, "' + case cast(case when substring(convert(char(8), {left}, 24), 1, 1) = '0' then substring(convert(char(8), {left}, 24), 2, 1) else substring(convert(char(8), {left}, 24), 1, 2) end as int) % 12 " +
                            "when 0 then '12' when 1 then '01' when 2 then '02' when 3 then '03' when 4 then '04' when 5 then '05' when 6 then '06' when 7 then '07' when 8 then '08' when 9 then '09' when 10 then '10' when 11 then '11' end'");
                    break;
                case "h":
                    matcher.appendReplacement(result, "' + case cast(case when substring(convert(char(8), {left}, 24), 1, 1) = '0' then substring(convert(char(8), {left}, 24), 2, 1) else substring(convert(char(8), {left}, 24), 1, 2) end as int) % 12" +
                            "when 0 then '12' when 1 then '1' when 2 then '2' when 3 then '3' when 4 then '4' when 5 then '5' when 6 then '6' when 7 then '7' when 8 then '8' when 9 then '9' when 10 then '10' when 11 then '11' end'");
                    break;
                case "mm":
                    matcher.appendReplacement(result, "' + substring(convert(char(8), {left}, 24), 4, 2)'");
                    break;
                case "m":
                    matcher.appendReplacement(result, "' + case when substring(convert(char(8), {left}, 24), 4, 1) = '0' then substring(convert(char(8), {left}, 24), 5, 1) else substring(convert(char(8), {left}, 24), 4, 2) end'");
                    break;
                case "ss":
                    matcher.appendReplacement(result, "' + substring(convert(char(8), {left}, 24), 7, 2)'");
                    break;
                case "s":
                    matcher.appendReplacement(result, "' + case when substring(convert(char(8), {left}, 24), 7, 1) = '0' then substring(convert(char(8), {left}, 24), 8, 1) else substring(convert(char(8), {left}, 24), 7, 2) end'");
                    break;
                case "tt":
                    matcher.appendReplacement(result, "' + case when cast(case when substring(convert(char(8), {left}, 24), 1, 1) = '0' then substring(convert(char(8), {left}, 24), 2, 1) else substring(convert(char(8), {left}, 24), 1, 2) end as int) >= 12 then 'PM' else 'AM' end'");
                    break;
                case "t":
                    matcher.appendReplacement(result, "' + case when cast(case when substring(convert(char(8), {left}, 24), 1, 1) = '0' then substring(convert(char(8), {left}, 24), 2, 1) else substring(convert(char(8), {left}, 24), 1, 2) end as int) >= 12 then 'P' else 'A' end'");
                    break;
            }
        }

        matcher.appendTail(result);
        if(!isMatched){
            return result.toString();
        }
        return "(" + result + ")";
    }

}
