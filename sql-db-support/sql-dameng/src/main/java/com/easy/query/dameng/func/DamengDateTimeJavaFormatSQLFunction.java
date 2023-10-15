package com.easy.query.dameng.func;

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
public class DamengDateTimeJavaFormatSQLFunction extends AbstractSQLFunction {
    private final TableAvailable table;
    private final String property;
    private final String javaFormat;

    public DamengDateTimeJavaFormatSQLFunction(TableAvailable table, String property, String javaFormat) {
        this.table = table;
        this.property = property;
        this.javaFormat = javaFormat;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
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
                    return "to_char({0},'YYYY-MM-DD HH24:MI:SS')";
                case "yyyy-MM-dd HH:mm":
                    return "to_char({0},'YYYY-MM-DD HH24:MI')";
                case "yyyy-MM-dd HH":
                    return "to_char({0},'YYYY-MM-DD HH24')";
                case "yyyy-MM-dd":
                    return "to_char({0},'YYYY-MM-DD')";
                case "yyyy-MM":
                    return "to_char({0},'YYYY-MM')";
                case "yyyyMMddHHmmss":
                    return "to_char({0},'YYYYMMDDHH24MISS')";
                case "yyyyMMddHHmm":
                    return "to_char({0},'YYYYMMDDHH24MI')";
                case "yyyyMMddHH":
                    return "to_char({0},'YYYYMMDDHH24')";
                case "yyyyMMdd":
                    return "to_char({0},'YYYYMMDD')";
                case "yyyyMM":
                    return "to_char({0},'YYYYMM')";
                case "yyyy":
                    return "to_char({0},'YYYY')";
                case "HH:mm:ss":
                    return "to_char({0},'HH24:MI:SS')";
            }
            format = replaceFormat(format);

            String[] argsFinds = {"YYYY", "YY", "%_a1", "%_a2", "%_a3", "%_a4", "SS", "%_a5"};
            String[] argsSpts = format.split("(M|d|H|hh|h|m|s|t)");

            for (int a = 0; a < argsSpts.length; a++) {
                switch (argsSpts[a]) {
                    case "M":
                        argsSpts[a] = "ltrim(to_char({0},'MM'),'0')";
                        break;
                    case "d":
                        argsSpts[a] = "CASE WHEN substr(to_char({0},'DD'),1,1) = '0' THEN substr(to_char({0},'DD'),2,1) ELSE to_char({0},'DD') END";
                        break;
                    case "H":
                        argsSpts[a] = "CASE WHEN substr(to_char({0},'HH24'),1,1) = '0' THEN substr(to_char({0},'HH24'),2,1) ELSE to_char({0},'HH24') END";
                        break;
                    case "hh":
                        argsSpts[a] = "CASE mod(cast(CASE WHEN substr(to_char({0},'HH24'),1,1) = '0' THEN substr(to_char({0},'HH24'),2,1) ELSE to_char({0},'HH24') END as number),12) " +
                                "WHEN 0 THEN '12' WHEN 1 THEN '01' WHEN 2 THEN '02' WHEN 3 THEN '03' WHEN 4 THEN '04' WHEN 5 THEN '05' WHEN 6 THEN '06' " +
                                "WHEN 7 THEN '07' WHEN 8 THEN '08' WHEN 9 THEN '09' WHEN 10 THEN '10' WHEN 11 THEN '11' END";
                        break;
                    case "h":
                        argsSpts[a] = "CASE mod(cast(CASE WHEN substr(to_char({0},'HH24'),1,1) = '0' THEN substr(to_char({0},'HH24'),2,1) ELSE to_char({0},'HH24') END as number),12) " +
                                "WHEN 0 THEN '12' WHEN 1 THEN '1' WHEN 2 THEN '2' WHEN 3 THEN '3' WHEN 4 THEN '4' WHEN 5 THEN '5' WHEN 6 THEN '6' " +
                                "WHEN 7 THEN '7' WHEN 8 THEN '8' WHEN 9 THEN '9' WHEN 10 THEN '10' WHEN 11 THEN '11' END";
                        break;
                    case "m":
                        argsSpts[a] = "CASE WHEN substr(to_char({0},'MI'),1,1) = '0' THEN substr(to_char({0},'MI'),2,1) ELSE to_char({0},'MI') END";
                        break;
                    case "s":
                        argsSpts[a] = "CASE WHEN substr(to_char({0},'SS'),1,1) = '0' THEN substr(to_char({0},'SS'),2,1) ELSE to_char({0},'SS') END";
                        break;
                    case "t":
                        argsSpts[a] = "rtrim(to_char({0},'AM'),'M')";
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
                            argsSpts[a] = "to_char({0},'" + argsSptsA + "')";
                        } else {
                            argsSpts[a] = "'" + argsSptsA + "'";
                        }
                        break;
                }
            }

            if (argsSpts.length == 1) {
                format = argsSpts[0];
            } else if (argsSpts.length > 1) {
                format = "(" + String.join(" || ", Arrays.stream(argsSpts).filter(a -> !a.equals("''")).toArray(String[]::new)) + ")";
            }

            return format.replace("%_a1", "MM").replace("%_a2", "DD").replace("%_a3", "HH24").replace("%_a4", "MI").replace("%_a5", "AM");
        }
        return "to_char({0},'YYYY-MM-DD HH24:MI:SS.FF6')";
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
}
