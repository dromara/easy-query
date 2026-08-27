package com.easy.query.duckdb.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLDateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final String javaFormat;

    public DuckDBSQLDateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
        this.columnExpressions = columnExpressions;
        this.javaFormat = javaFormat;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return getSQLSegment();
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    public String getSQLSegment() {
        if (this.javaFormat != null) {

            String format = this.javaFormat;
            switch (format) {
                case "yyyy-MM-dd HH:mm:ss":
                    return "strftime({0}, '%Y-%m-%d %H:%M:%S')";
                case "yyyy-MM-dd HH:mm":
                    return "strftime({0}, '%Y-%m-%d %H:%M')";
                case "yyyy-MM-dd HH":
                    return "strftime({0}, '%Y-%m-%d %H')";
                case "yyyy-MM-dd":
                    return "strftime({0}, '%Y-%m-%d')";
                case "yyyy-MM":
                    return "strftime({0}, '%Y-%m')";
                case "yyyyMMddHHmmss":
                    return "strftime({0}, '%Y%m%d%H%M%S')";
                case "yyyyMMddHHmm":
                    return "strftime({0}, '%Y%m%d%H%M')";
                case "yyyyMMddHH":
                    return "strftime({0}, '%Y%m%d%H')";
                case "yyyyMMdd":
                    return "strftime({0}, '%Y%m%d')";
                case "yyyyMM":
                    return "strftime({0}, '%Y%m')";
                case "yyyy":
                    return "strftime({0}, '%Y')";
                case "HH:mm:ss":
                    return "strftime({0}, '%H:%M:%S')";
            }
            format = replaceFormat(format);

            String[] argsFinds = {"%Y", "%m", "%d", "%H", "%M", "%S"};
            String[] argsSpts = format.split("(yy|M|d|H|hh|h|m|s|tt|t)");
            for (int a = 0; a < argsSpts.length; a++) {
                switch (argsSpts[a]) {
                    case "yy":
                        argsSpts[a] = "substr(strftime({0}, '%Y'), 3, 2)";
                        break;
                    case "M":
                        argsSpts[a] = "ltrim(strftime({0}, '%m'), '0')";
                        break;
                    case "d":
                        argsSpts[a] = "ltrim(strftime({0}, '%d'), '0')";
                        break;
                    case "H":
                        argsSpts[a] = "case when substr(strftime({0}, '%H'), 1, 1) = '0' then substr(strftime({0}, '%H'), 2, 1) else strftime({0}, '%H') end";
                        break;
                    case "hh":
                        argsSpts[a] = "case cast(case when substr(strftime({0}, '%H'), 1, 1) = '0' then substr(strftime({0}, '%H'), 2, 1) else strftime({0}, '%H') end as smallint) % 12 " +
                                "when 0 then '12' when 1 then '01' when 2 then '02' when 3 then '03' when 4 then '04' when 5 then '05' when 6 then '06' when 7 then '07' when 8 then '08' " +
                                "when 9 then '09' when 10 then '10' when 11 then '11' end";
                        break;
                    case "h":
                        argsSpts[a] = "case cast(case when substr(strftime({0}, '%H'), 1, 1) = '0' then substr(strftime({0}, '%H'), 2, 1) else strftime({0}, '%H') end as smallint) % 12 " +
                                "when 0 then '12' when 1 then '1' when 2 then '2' when 3 then '3' when 4 then '4' when 5 then '5' when 6 then '6' when 7 then '7' when 8 then '8' " +
                                "when 9 then '9' when 10 then '10' when 11 then '11' end";
                        break;
                    case "m":
                        argsSpts[a] = "case when substr(strftime({0}, '%M'), 1, 1) = '0' then substr(strftime({0}, '%M'), 2, 1) else strftime({0}, '%M') end";
                        break;
                    case "s":
                        argsSpts[a] = "case when substr(strftime({0}, '%S'), 1, 1) = '0' then substr(strftime({0}, '%S'), 2, 1) else strftime({0}, '%S') end";
                        break;
                    case "tt":
                        argsSpts[a] = "case when cast(case when substr(strftime({0}, '%H'), 1, 1) = '0' then substr(strftime({0}, '%H'), 2, 1) else strftime({0}, '%H') end as smallint) >= 12 " +
                                "then 'PM' else 'AM' end";
                        break;
                    case "t":
                        argsSpts[a] = "case when cast(case when substr(strftime({0}, '%H'), 1, 1) = '0' then substr(strftime({0}, '%H'), 2, 1) else strftime({0}, '%H') end as smallint) >= 12 " +
                                "then 'P' else 'A' end";
                        break;
                    default:
                        String argsSptsA = argsSpts[a];
                        if (argsSptsA.startsWith("'")) {
                            argsSptsA = argsSptsA.substring(1);
                        }
                        if (argsSptsA.endsWith("'")) {
                            argsSptsA = argsSptsA.substring(0, argsSptsA.length() - 1);
                        }
                        if (Arrays.stream(argsFinds).anyMatch(argsSptsA::contains)) {
                            argsSpts[a] = "strftime({0}, '" + argsSptsA + "')";
                        } else {
                            argsSpts[a] = "'" + argsSptsA + "'";
                        }
                        break;
                }
            }

            if (argsSpts.length == 1) {
                format = argsSpts[0];
            }else if (argsSpts.length > 1) {
                format = "(" + String.join(" || ", Arrays.stream(argsSpts).filter(a -> !a.equals("''")).toArray(String[]::new)) + ")";
            }

            return format.replace("%_a1","%m").replace("%_a2","%d").replace("%_a3","%H").replace("%_a4","%M");
        }
        return "strftime({0}, '%Y-%m-%d %H:%M:%f')";
    }

    protected String replaceFormat(String format) {
        String pattern = "(yyyy|MM|dd|HH|mm|ss)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(format);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    matcher.appendReplacement(result, "%Y");
                    break;
                case "MM":
                    matcher.appendReplacement(result, "%_a1");
                    break;
                case "dd":
                    matcher.appendReplacement(result, "%_a2");
                    break;
                case "HH":
                    matcher.appendReplacement(result, "%_a3");
                    break;
                case "mm":
                    matcher.appendReplacement(result, "%_a4");
                    break;
                case "ss":
                    matcher.appendReplacement(result, "%S");
                    break;
            }
        }

        matcher.appendTail(result);
        return result.toString();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
