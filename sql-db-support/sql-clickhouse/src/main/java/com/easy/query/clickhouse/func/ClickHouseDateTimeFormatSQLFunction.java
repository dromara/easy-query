package com.easy.query.clickhouse.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

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
public class ClickHouseDateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final String javaFormat;

    public ClickHouseDateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
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

    /**
     * 代码参考 <a href="https://github.com/dotnetcore/FreeSql">FreeSQL</a>
     *
     * @return
     */
    public String getSQLSegment() {
        if (this.javaFormat != null) {

            String format = this.javaFormat;
            switch (format) {
                case "'yyyy-MM-dd HH:mm:ss'":
                    return "formatDateTime(toDateTime({0}),'%Y-%m-%d %H:%M:%S')";
                case "'yyyy-MM-dd HH:mm'":
                    return "formatDateTime(toDateTime({0}),'%Y-%m-%d %H:%M')";
                case "'yyyy-MM-dd HH'":
                    return "formatDateTime(toDateTime({0}),'%Y-%m-%d %H')";
                case "'yyyy-MM-dd'":
                    return "formatDateTime(toDateTime({0}),'%Y-%m-%d')";
                case "'yyyy-MM'":
                    return "formatDateTime(toDateTime({0}),'%Y-%m')";
                case "'yyyyMMddHHmmss'":
                    return "formatDateTime(toDateTime({0}),'%Y%m%d%H%M%S')";
                case "'yyyyMMddHHmm'":
                    return "formatDateTime(toDateTime({0}),'%Y%m%d%H%M')";
                case "'yyyyMMddHH'":
                    return "formatDateTime(toDateTime({0}),'%Y%m%d%H')";
                case "'yyyyMMdd'":
                    return "formatDateTime(toDateTime({0}),'%Y%m%d')";
                case "'yyyyMM'":
                    return "formatDateTime(toDateTime({0}),'%Y%m')";
                case "'yyyy'":
                    return "formatDateTime(toDateTime({0}),'%Y')";
                case "'HH:mm:ss'":
                    return "formatDateTime(toDateTime({0}),'%H:%M:%S')";
            }

            format = replaceFormat(format);

            String[] argsFinds = {"%Y", "%y", "%_a1", "%c", "%d", "%e", "%H", "%k", "%h", "%l", "%M", "%_a2", "%p"};
            String[] argsSpts = format.split("(m|s|t)");

            for (int a = 0; a < argsSpts.length; a++) {
                switch (argsSpts[a]) {
                    case "m":
                        argsSpts[a] = "CASE WHEN SUBSTR(formatDateTime(toDateTime({0}),'%M'),1,1) = '0' THEN SUBSTR(formatDateTime(toDateTime({0}),'%M'),2,1) ELSE formatDateTime(toDateTime({0}),'%M') END";
                        break;
                    case "s":
                        argsSpts[a] = "CASE WHEN SUBSTR(formatDateTime(toDateTime({0}),'%S'),1,1) = '0' THEN SUBSTRsubstr(formatDateTime(toDateTime({0}),'%S'),2,1) ELSE formatDateTime(toDateTime({0}),'%S') END";
                        break;
                    case "t":
                        argsSpts[a] = "TRIM(TRAILING 'M' FROM formatDateTime(toDateTime({left}),'%p'))";
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
                            argsSpts[a] = "formatDateTime(toDateTime({0}),'" + argsSptsA + "')";
                        } else {
                            argsSpts[a] = "'" + argsSptsA + "'";
                        }
                        break;
                }
            }

            if (argsSpts.length == 1) {
                format = argsSpts[0];
            } else if (argsSpts.length > 1) {
                format = "CONCAT(" + String.join(", ", Arrays.stream(argsSpts).filter(a -> !a.equals("''")).toArray(String[]::new)) + ")";
            }

            return format.replace("%_a1", "%m").replace("%_a2", "%S");
        }
        return "DATE_FORMAT({0},'%Y-%m-%d %H:%M:%S.%f')";
    }

    protected String replaceFormat(String format) {
        String pattern = "(yyyy|yy|MM|M|dd|d|HH|H|hh|h|mm|ss|tt)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(format);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    matcher.appendReplacement(result, "%Y");
                    break;
                case "yy":
                    matcher.appendReplacement(result, "%y");
                    break;
                case "MM":
                    matcher.appendReplacement(result, "%_a1");
                    break;
                case "M":
                    matcher.appendReplacement(result, "%c");
                    break;
                case "dd":
                    matcher.appendReplacement(result, "%d");
                    break;
                case "d":
                    matcher.appendReplacement(result, "%e");
                    break;
                case "HH":
                    matcher.appendReplacement(result, "%H");
                    break;
                case "H":
                    matcher.appendReplacement(result, "%k");
                    break;
                case "hh":
                    matcher.appendReplacement(result, "%h");
                    break;
                case "h":
                    matcher.appendReplacement(result, "%l");
                    break;
                case "mm":
                    matcher.appendReplacement(result, "%M");
                    break;
                case "ss":
                    matcher.appendReplacement(result, "%_a2");
                    break;
                case "tt":
                    matcher.appendReplacement(result, "%p");
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
