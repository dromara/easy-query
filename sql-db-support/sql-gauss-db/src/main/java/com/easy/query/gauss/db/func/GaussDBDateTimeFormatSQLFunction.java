package com.easy.query.gauss.db.func;

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
public class GaussDBDateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final String javaFormat;

    public GaussDBDateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
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
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYY-MM-DD HH24:MI:SS')";
                case "yyyy-MM-dd HH:mm":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYY-MM-DD HH24:MI')";
                case "yyyy-MM-dd HH":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYY-MM-DD HH24')";
                case "yyyy-MM-dd":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYY-MM-DD')";
                case "yyyy-MM":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYY-MM')";
                case "yyyyMMddHHmmss":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYYMMDDHH24MISS')";
                case "yyyyMMddHHmm":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYYMMDDHH24MI')";
                case "yyyyMMddHH":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYYMMDDHH24')";
                case "yyyyMMdd":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYYMMDD')";
                case "yyyyMM":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYYMM')";
                case "yyyy":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYY')";
                case "HH:mm:ss":
                    return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24:MI:SS')";
            }
            format = replaceFormat(format);

            String[] argsFinds = {"YYYY", "YY", "%_a1", "%_a2", "%_a3", "%_a4", "SS", "%_a5"};
            String[] argsSpts = format.split("(M|d|H|hh|h|m|s|t)");

            for (int a = 0; a < argsSpts.length; a++) {
                switch (argsSpts[a]) {
                    case "M":
                        argsSpts[a] = "LTRIM(TO_CHAR((({0})::TIMESTAMP)::timestamp,'MM'),'0')";
                        break;
                    case "d":
                        argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'DD'),1,1) = '0' THEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'DD'),2,1) ELSE TO_CHAR((({0})::TIMESTAMP)::timestamp,'DD') END";
                        break;
                    case "H":
                        argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24'),1,1) = '0' THEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24'),2,1) ELSE TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24') END";
                        break;
                    case "hh":
                        argsSpts[a] = "CASE mod(cast(CASE WHEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24'),1,1) = '0' THEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24'),2,1) ELSE TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24') END as number),12) " +
                                "WHEN 0 THEN '12' WHEN 1 THEN '01' WHEN 2 THEN '02' WHEN 3 THEN '03' WHEN 4 THEN '04' WHEN 5 THEN '05' WHEN 6 THEN '06' " +
                                "WHEN 7 THEN '07' WHEN 8 THEN '08' WHEN 9 THEN '09' WHEN 10 THEN '10' WHEN 11 THEN '11' END";
                        break;
                    case "h":
                        argsSpts[a] = "CASE mod(cast(CASE WHEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24'),1,1) = '0' THEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24'),2,1) ELSE TO_CHAR((({0})::TIMESTAMP)::timestamp,'HH24') END as number),12) " +
                                "WHEN 0 THEN '12' WHEN 1 THEN '1' WHEN 2 THEN '2' WHEN 3 THEN '3' WHEN 4 THEN '4' WHEN 5 THEN '5' WHEN 6 THEN '6' " +
                                "WHEN 7 THEN '7' WHEN 8 THEN '8' WHEN 9 THEN '9' WHEN 10 THEN '10' WHEN 11 THEN '11' END";
                        break;
                    case "m":
                        argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'MI'),1,1) = '0' THEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'MI'),2,1) ELSE TO_CHAR((({0})::TIMESTAMP)::timestamp,'MI') END";
                        break;
                    case "s":
                        argsSpts[a] = "CASE WHEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'SS'),1,1) = '0' THEN SUBSTR(TO_CHAR((({0})::TIMESTAMP)::timestamp,'SS'),2,1) ELSE TO_CHAR((({0})::TIMESTAMP)::timestamp,'SS') END";
                        break;
                    case "t":
                        argsSpts[a] = "rtrim(TO_CHAR((({0})::TIMESTAMP)::timestamp,'AM'),'M')";
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
                            argsSpts[a] = "TO_CHAR((({0})::TIMESTAMP)::timestamp,'" + argsSptsA + "')";
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

            return format.replace("%_a1", "MM").replace("%_a2", "DD").replace("%_a3", "HH24").replace("%_a4", "MI").replace("%_a5", "AM");
        }
        return "TO_CHAR((({0})::TIMESTAMP)::timestamp,'YYYY-MM-DD HH24:MI:SS.US')";
    }

    protected String replaceFormat(String format) {
        String pattern = "(yyyy|yy|MM|dd|HH|hh|mm|ss|tt)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(format);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    matcher.appendReplacement(result, "YYYY");
                    break;
                case "yy":
                    matcher.appendReplacement(result, "YY");
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
                    matcher.appendReplacement(result, "SS");
                    break;
                case "tt":
                    matcher.appendReplacement(result, "%_a5");
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
