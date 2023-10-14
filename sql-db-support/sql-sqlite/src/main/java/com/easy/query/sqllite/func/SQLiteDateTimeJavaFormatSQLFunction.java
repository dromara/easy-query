package com.easy.query.sqllite.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.def.AbstractSQLFunction;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteDateTimeJavaFormatSQLFunction extends AbstractSQLFunction {
    private final TableAvailable table;
    private final String property;
    private final String javaFormat;

    public SQLiteDateTimeJavaFormatSQLFunction(TableAvailable table, String property, String javaFormat) {
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
                    return "strftime('%%Y-%%m-%%d %%H:%%M:%%S', {0})";
                case "yyyy-MM-dd HH:mm":
                    return "strftime('%%Y-%%m-%%d %%H:%%M', {0})";
                case "yyyy-MM-dd HH":
                    return "strftime('%%Y-%%m-%%d %%H',{0})";
                case "yyyy-MM-dd":
                    return "strftime('%%Y-%%m-%%d', {0})";
                case "yyyy-MM":
                    return "strftime('%%Y-%%m', {0})";
                case "yyyyMMddHHmmss":
                    return "strftime('%%Y%%m%%d%%H%%M%%S', {0})";
                case "yyyyMMddHHmm":
                    return "strftime('%%Y%%m%%d%%H%%M', {0})";
                case "yyyyMMddHH":
                    return "strftime('%%Y%%m%%d%%H', {0})";
                case "yyyyMMdd":
                    return "strftime('%%Y%%m%%d', {0})";
                case "yyyyMM":
                    return "strftime('%%Y%%m', {0})";
                case "yyyy":
                    return "strftime('%%Y', {0})";
                case "HH:mm:ss":
                    return "strftime('%%H:%%M:%%S', {0})";
            }
            format = replaceFormat(format);

            String[] argsFinds = {"%Y", "%m", "%d", "%H", "%M", "%S"};
            String[] argsSpts = format.split("(yy|M|d|H|hh|h|m|s|tt|t)");
            for (int a = 0; a < argsSpts.length; a++) {
                switch (argsSpts[a]) {
                    case "yy":
                        argsSpts[a] = "substr(strftime('%%Y', {0}), 3, 2)";
                        break;
                    case "M":
                        argsSpts[a] = "ltrim(strftime('%%m', {0}), '0')";
                        break;
                    case "d":
                        argsSpts[a] = "ltrim(strftime('%%d', {0}), '0')";
                        break;
                    case "H":
                        argsSpts[a] = "case when substr(strftime('%%H', {0}), 1, 1) = '0' then substr(strftime('%%H', {0}), 2, 1) else strftime('%%H', {0}) end";
                        break;
                    case "hh":
                        argsSpts[a] = "case cast(case when substr(strftime('%%H', {0}), 1, 1) = '0' then substr(strftime('%%H', {0}), 2, 1) else strftime('%%H', {0}) end as smallint) %% 12 " +
                                "when 0 then '12' when 1 then '01' when 2 then '02' when 3 then '03' when 4 then '04' when 5 then '05' when 6 then '06' when 7 then '07' when 8 then '08' " +
                                "when 9 then '09' when 10 then '10' when 11 then '11' end";
                        break;
                    case "h":
                        argsSpts[a] = "case cast(case when substr(strftime('%%H', {0}), 1, 1) = '0' then substr(strftime('%%H', {0}), 2, 1) else strftime('%%H', {0}) end as smallint) %% 12 " +
                                "when 0 then '12' when 1 then '1' when 2 then '2' when 3 then '3' when 4 then '4' when 5 then '5' when 6 then '6' when 7 then '7' when 8 then '8' " +
                                "when 9 then '9' when 10 then '10' when 11 then '11' end";
                        break;
                    case "m":
                        argsSpts[a] = "case when substr(strftime('%%M', {0}), 1, 1) = '0' then substr(strftime('%%M', {0}), 2, 1) else strftime('%%M', {0}) end";
                        break;
                    case "s":
                        argsSpts[a] = "case when substr(strftime('%%S', {0}), 1, 1) = '0' then substr(strftime('%%S', {0}), 2, 1) else strftime('%%S', {0}) end";
                        break;
                    case "tt":
                        argsSpts[a] = "case when cast(case when substr(strftime('%%H', {0}), 1, 1) = '0' then substr(strftime('%%H', {0}), 2, 1) else strftime('%%H', {0}) end as smallint) >= 12 " +
                                "then 'PM' else 'AM' end";
                        break;
                    case "t":
                        argsSpts[a] = "case when cast(case when substr(strftime('%%H', {0}), 1, 1) = '0' then substr(strftime('%%H', {0}), 2, 1) else strftime('%%H', {0}) end as smallint) >= 12 " +
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
                            argsSpts[a] = "strftime('" + argsSptsA + "', {0})";
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

            return format.replace("%m", "%_a1").replace("%d", "%_a2").replace("%H", "%_a3").replace("%M", "%_a4");
        }
        return "strftime('%Y-%m-%d %H:%M:%f',{0})";
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

}
