package com.easy.query.mssql.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLDateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final String javaFormat;

    public MsSQLDateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
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
                case "yyyy-MM-dd HH:mm:ss":
                    return "CONVERT(CHAR(19), {0}, 120)";
                case "yyyy-MM-dd HH:mm":
                    return "SUBSTRING(CONVERT(CHAR(19), {0}, 120), 1, 16)";
                case "yyyy-MM-dd HH":
                    return "SUBSTRING(CONVERT(CHAR(19), {0}, 120), 1, 13)";
                case "yyyy-MM-dd":
                    return "CONVERT(CHAR(10), {0}, 23)";
                case "yyyy-MM":
                    return "SUBSTRING(CONVERT(CHAR(10), {0}, 23), 1, 7)";
                case "yyyyMMdd":
                    return "CONVERT(CHAR(8), {0}, 112)";
                case "yyyyMM":
                    return "SUBSTRING(CONVERT(CHAR(8), {0}, 112), 1, 6)";
                case "yyyy":
                    return "SUBSTRING(CONVERT(CHAR(8), {0}, 112), 1, 4)";
                case "HH:mm:ss":
                    return "CONVERT(CHAR(8), {0}, 24)";
            }

            return getReplacedFormats(format);
        }
        return "CONVERT(VARCHAR, {0}, 121)";
    }

    private static final Pattern FORMAT_PATTERN = Pattern.compile("(yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]|(?:(?!yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]).)+)");

    protected String getReplacedFormats(String format) {
        Matcher matcher = FORMAT_PATTERN.matcher(format);
//        StringBuffer result = new StringBuffer();
        List<String> results = new ArrayList<>();
        int i=1;
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    results.add("SUBSTRING(CONVERT(CHAR(8), {0}, 112), 1, 4)");
//                    matcher.appendReplacement(result, "YYYY");
                    break;
                case "yy":
                    results.add("SUBSTRING(CONVERT(CHAR(6), {0}, 12), 1, 2)");
//                    matcher.appendReplacement(result, "YY");
                    break;
                case "MM":
                    results.add("SUBSTRING(CONVERT(CHAR(6), {0}, 12), 3, 2)");
//                    matcher.appendReplacement(result, "%_a1");
                    break;
                case "M":
                    results.add("CASE WHEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 3, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 4, 1) else SUBSTRING(CONVERT(CHAR(6), {0}, 12), 3, 2) END");
                    break;
                case "dd":
                    results.add("SUBSTRING(CONVERT(CHAR(6), {0}, 12), 5, 2)");
//                    matcher.appendReplacement(result, "%_a2");
                    break;
                case "d":
                    results.add("CASE WHEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 5, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 6, 1) ELSE SUBSTRING(CONVERT(CHAR(6), {0}, 12), 5, 2) END");
                    break;
                case "HH":
                    results.add("SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2)");
//                    matcher.appendReplacement(result, "%_a3");
                    break;
                case "H":
                    results.add("CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) ELSE SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END')");
                    break;
                case "hh":
                    results.add("CASE CAST(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) ELSE SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) % 12 " +
                            "WHEN 0 THEN '12' WHEN 1 THEN '01' WHEN 2 THEN '02' WHEN 3 THEN '03' WHEN 4 THEN '04' WHEN 5 THEN '05' WHEN 6 THEN '06' WHEN 7 THEN '07' WHEN 8 THEN '08' WHEN 9 THEN '09' WHEN 10 THEN '10' WHEN 11 THEN '11' END");
                    break;
                case "h":
                    results.add("case cast(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) ELSE SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) % 12" +
                            "WHEN 0 THEN '12' WHEN 1 THEN '1' WHEN 2 THEN '2' WHEN 3 THEN '3' WHEN 4 THEN '4' WHEN 5 THEN '5' WHEN 6 THEN '6' WHEN 7 THEN '7' WHEN 8 THEN '8' WHEN 9 THEN '9' WHEN 10 THEN '10' WHEN 11 THEN '11' END");
                    break;
                case "mm":
                    results.add("SUBSTRING(CONVERT(CHAR(8), {0}, 24), 4, 2)");
//                    matcher.appendReplacement(result, "%_a4");
                    break;
                case "m":
                    results.add("CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 4, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 5, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 4, 2) END");
                    break;
                case "ss":
                    results.add("SUBSTRING(CONVERT(CHAR(8), {0}, 24), 7, 2)");
                    break;
                case "s":
                    results.add("CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 7, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 8, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 7, 2) END");
//                    matcher.appendReplacement(result, "SS");
                    break;
                case "tt":
                    results.add("CASE WHEN cast(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) >= 12 THEN 'PM' else 'AM' END");
//                    matcher.appendReplacement(result, "%_a5");
                    break;
                case "t":
                    results.add("CASE WHEN cast(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) >= 12 THEN 'P' else 'A' END");
                    break;
                default:
                    columnExpressions.add(new ColumnFuncValueExpressionImpl(match));
                    results.add("{" + i++ + "}");
                    break;
            }
        }
        return "("+String.join(" + ", results)+")";
//        matcher.appendTail(result);
//        return result.toString();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
