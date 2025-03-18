package com.easy.query.oracle.func;

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
public class OracleDateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private static final Pattern FORMAT_PATTERN = Pattern.compile("(yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa:/\\-_ ])");
    private final String javaFormat;
    private final List<ColumnExpression> columnExpressions;

    public OracleDateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
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

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }

    public String getSQLSegment() {
        if(this.javaFormat!=null){
            String format = this.javaFormat;
            switch (format) {
                case "yyyy-MM-dd HH:mm:ss":
                    return "TO_CHAR({0},'YYYY-MM-DD HH24:MI:SS')";
                case "yyyy-MM-dd HH:mm":
                    return "TO_CHAR({0},'YYYY-MM-DD HH24:MI')";
                case "yyyy-MM-dd HH":
                    return "TO_CHAR({0},'YYYY-MM-DD HH24')";
                case "yyyy-MM-dd":
                    return "TO_CHAR({0},'YYYY-MM-DD')";
                case "yyyy-MM":
                    return "TO_CHAR({0},'YYYY-MM')";
                case "yyyyMMddHHmmss":
                    return "TO_CHAR({0},'YYYYMMDDHH24MISS')";
                case "yyyyMMddHHmm":
                    return "TO_CHAR({0},'YYYYMMDDHH24MI')";
                case "yyyyMMddHH":
                    return "TO_CHAR({0},'YYYYMMDDHH24')";
                case "yyyyMMdd":
                    return "TO_CHAR({0},'YYYYMMDD')";
                case "yyyyMM":
                    return "TO_CHAR({0},'YYYYMM')";
                case "yyyy":
                    return "TO_CHAR({0},'YYYY')";
                case "HH:mm:ss":
                    return "TO_CHAR({0},'HH24:MI:SS')";
            }
            return getReplacedFormats(format);
        }
        return "TO_CHAR({0},'YYYY-MM-DD HH24:MI:SS.FF6')";
    }

    protected String getReplacedFormats(String format) {
        Matcher matcher = FORMAT_PATTERN.matcher(format);
        StringBuilder sb = new StringBuilder();

        int cursor = 0;
        while (matcher.find()) {
            if (cursor != matcher.start()) {
                sb.append('"').append(format, cursor, matcher.start()).append('"');
            }
            String match = matcher.group(1);
            switch (match) {
                case "M":
                    sb.append("FMMM");
                    break;
                case "d":
                    sb.append("FMDD");
                    break;
                case "HH":
                    sb.append("HH24");
                    break;
                case "H":
                    sb.append("FMHH24");
                    break;
                case "h":
                    sb.append("FMHH");
                    break;
                case "mm":
                    sb.append("MI");
                    break;
                case "m":
                    sb.append("FMMI");
                    break;
                case "s":
                    sb.append("FMSS");
                    break;
                case "a":
                    sb.append("AM");
                    break;
                default:
                    sb.append(match.toUpperCase());
                    break;
            }
            cursor = matcher.end();
        }
        if (cursor != format.length()) {
            sb.append('"').append(format, cursor, format.length()).append('"');
        }
        return String.format("TO_CHAR({0},'%s')", sb);
    }


}
