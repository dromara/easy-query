package com.easy.query.mssql.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

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

            return replaceFormat(format);
        }
        return "CONVERT(VARCHAR, {0}, 121)";
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
                    matcher.appendReplacement(result, "' + SUBSTRING(CONVERT(CHAR(8), {0}, 112), 1, 4) + '");
                    break;
                case "yy":
                    matcher.appendReplacement(result, "' + SUBSTRING(CONVERT(CHAR(6), {0}, 12), 1, 2) + '");
                    break;
                case "MM":
                    matcher.appendReplacement(result, "' + SUBSTRING(CONVERT(CHAR(6), {0}, 12), 3, 2) + '");
                    break;
                case "M":
                    matcher.appendReplacement(result, "' + CASE WHEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 3, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 4, 1) else SUBSTRING(CONVERT(CHAR(6), {0}, 12), 3, 2) END + '");
                    break;
                case "dd":
                    matcher.appendReplacement(result, "' + SUBSTRING(CONVERT(CHAR(6), {0}, 12), 5, 2) + '");
                    break;
                case "d":
                    matcher.appendReplacement(result, "' + CASE WHEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 5, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(6), {0}, 12), 6, 1) ELSE SUBSTRING(CONVERT(CHAR(6), {0}, 12), 5, 2) END + '");
                    break;
                case "HH":
                    matcher.appendReplacement(result, "' + SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) + '");
                    break;
                case "H":
                    matcher.appendReplacement(result, "' + CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) ELSE SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END + '");
                    break;
                case "hh":
                    matcher.appendReplacement(result, "' + CASE CAST(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) ELSE SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) % 12 " +
                            "WHEN 0 THEN '12' WHEN 1 THEN '01' WHEN 2 THEN '02' WHEN 3 THEN '03' WHEN 4 THEN '04' WHEN 5 THEN '05' WHEN 6 THEN '06' WHEN 7 THEN '07' WHEN 8 THEN '08' WHEN 9 THEN '09' WHEN 10 THEN '10' WHEN 11 THEN '11' END + '");
                    break;
                case "h":
                    matcher.appendReplacement(result, "' + case cast(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) ELSE SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) % 12" +
                            "WHEN 0 THEN '12' WHEN 1 THEN '1' WHEN 2 THEN '2' WHEN 3 THEN '3' WHEN 4 THEN '4' WHEN 5 THEN '5' WHEN 6 THEN '6' WHEN 7 THEN '7' WHEN 8 THEN '8' WHEN 9 THEN '9' WHEN 10 THEN '10' WHEN 11 THEN '11' END + '");
                    break;
                case "mm":
                    matcher.appendReplacement(result, "' + SUBSTRING(CONVERT(CHAR(8), {0}, 24), 4, 2) + '");
                    break;
                case "m":
                    matcher.appendReplacement(result, "' + CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 4, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 5, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 4, 2) END + '");
                    break;
                case "ss":
                    matcher.appendReplacement(result, "' + SUBSTRING(CONVERT(CHAR(8), {0}, 24), 7, 2) + '");
                    break;
                case "s":
                    matcher.appendReplacement(result, "' + CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 7, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 8, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 7, 2) END + '");
                    break;
                case "tt":
                    matcher.appendReplacement(result, "' + CASE WHEN cast(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) >= 12 THEN 'PM' else 'AM' END + '");
                    break;
                case "t":
                    matcher.appendReplacement(result, "' + CASE WHEN cast(CASE WHEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 1) = '0' THEN SUBSTRING(CONVERT(CHAR(8), {0}, 24), 2, 1) else SUBSTRING(CONVERT(CHAR(8), {0}, 24), 1, 2) END as int) >= 12 THEN 'P' else 'A' END + '");
                    break;
            }
        }

        matcher.appendTail(result);
        if(!isMatched){
            return result.toString();
        }
        return "( '" + result + "')";
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
