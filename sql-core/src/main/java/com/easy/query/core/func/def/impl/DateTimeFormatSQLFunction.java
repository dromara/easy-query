package com.easy.query.core.func.def.impl;

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
public class DateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final String javaFormat;

    public DateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
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
                    return "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%s')";
                case "yyyy-MM-dd HH:mm":
                    return "DATE_FORMAT({0},'%Y-%m-%d %H:%i')";
                case "yyyy-MM-dd HH":
                    return "DATE_FORMAT({0},'%Y-%m-%d %H')";
                case "yyyy-MM-dd":
                    return "DATE_FORMAT({0},'%Y-%m-%d')";
                case "yyyy-MM":
                    return "DATE_FORMAT({0},'%Y-%m')";
                case "yyyyMMddHHmmss":
                    return "DATE_FORMAT({0},'%Y%m%d%H%i%s')";
                case "yyyyMMddHHmm":
                    return "DATE_FORMAT({0},'%Y%m%d%H%i')";
                case "yyyyMMddHH":
                    return "DATE_FORMAT({0},'%Y%m%d%H')";
                case "yyyyMMdd":
                    return "DATE_FORMAT({0},'%Y%m%d')";
                case "yyyyMM":
                    return "DATE_FORMAT({0},'%Y%m')";
                case "yyyy":
                    return "DATE_FORMAT({0},'%Y')";
                case "HH:mm:ss":
                    return "DATE_FORMAT({0},'%H:%i:%s')";
            }

            if(format.contains("'")){
                return formatSingleQuote(format);
            }
            return formatDefault(format);
        }
        return "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%s.%f')";
    }


    private static final Pattern FORMAT_PATTERN1 = Pattern.compile("(yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]|(?:(?!yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]).)+)");
    private static final Pattern FORMAT_PATTERN2 =  Pattern.compile("(yyyy|yy|MM|M|dd|d|HH|H|hh|h|mm|ss|tt)");

    protected String formatSingleQuote(String format) {
        Matcher matcher = FORMAT_PATTERN1.matcher(format);
//        StringBuffer result = new StringBuffer();
        int i = 1;
        List<String> results = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    results.add("DATE_FORMAT({0},'%Y')");
//                    matcher.appendReplacement(result, "YYYY");
                    break;
                case "yy":
                    results.add("DATE_FORMAT({0},'%y')");
//                    matcher.appendReplacement(result, "YY");
                    break;
                case "MM":
                    results.add("DATE_FORMAT({0},'%m')");
//                    matcher.appendReplacement(result, "%_a1");
                    break;
                case "M":
                    results.add("DATE_FORMAT({0},'%c')");
                    break;
                case "dd":
                    results.add("DATE_FORMAT({0},'%d')");
//                    matcher.appendReplacement(result, "%_a2");
                    break;
                case "d":
                    results.add("DATE_FORMAT({0},'%e')");
                    break;
                case "HH":
                    results.add("DATE_FORMAT({0},'%H')");
//                    matcher.appendReplacement(result, "%_a3");
                    break;
                case "H":
                    results.add("DATE_FORMAT({0},'%k')");
                    break;
                case "hh":
                    results.add("DATE_FORMAT({0},'%h')");
                    break;
                case "h":
                    results.add("DATE_FORMAT({0},'%l')");
                    break;
                case "mm":
                    results.add("DATE_FORMAT({0},'%i')");
                    break;
                case "m":
                    results.add("(CASE WHEN SUBSTR(DATE_FORMAT({0},'%i'),1,1) = '0' THEN SUBSTR(DATE_FORMAT({0},'%i'),2,1) ELSE DATE_FORMAT({0},'%i') END) ");
                    break;
                case "ss":
                    results.add("DATE_FORMAT({0},'%s')");
                    break;
                case "s":
                    results.add("(CASE WHEN SUBSTR(DATE_FORMAT({0},'%s'),1,1) = '0' THEN SUBSTR(DATE_FORMAT({0},'%s'),2,1) ELSE DATE_FORMAT({0},'%s') END)");
                    break;
                case "tt":
                    results.add("DATE_FORMAT({0},'%p')");
                    break;
                case "t":
                    results.add("(TRIM(TRAILING 'M' FROM DATE_FORMAT({0},'%p'))) ");
                    break;
                default:
                    columnExpressions.add(new ColumnFuncValueExpressionImpl(match));
                    results.add("{" + i++ + "}");
                    break;
            }
        }
        return "CONCAT(" + String.join(", ", results) + ")";
    }


    protected String formatDefault(String format) {
        Matcher matcher = FORMAT_PATTERN2.matcher(format);

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
                    matcher.appendReplacement(result, "%i");
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
       String newFormat = result.toString();


        String[] argsFinds = {"%Y", "%y", "%_a1", "%c", "%d", "%e", "%H", "%k", "%h", "%l", "%i", "%_a2", "%p"};
        String[] argsSpts = newFormat.split("(m|s|t)");

        for (int a = 0; a < argsSpts.length; a++) {
            switch (argsSpts[a]) {
                case "m":
                    argsSpts[a] = "CASE WHEN SUBSTR(DATE_FORMAT({0},'%i'),1,1) = '0' THEN SUBSTR(DATE_FORMAT({0},'%i'),2,1) ELSE DATE_FORMAT({0},'%i') END";
                    break;
                case "s":
                    argsSpts[a] = "CASE WHEN SUBSTR(DATE_FORMAT({0},'%s'),1,1) = '0' THEN SUBSTR(DATE_FORMAT({0},'%s'),2,1) ELSE DATE_FORMAT({0},'%s') END";
                    break;
                case "t":
                    argsSpts[a] = "TRIM(TRAILING 'M' FROM DATE_FORMAT({0},'%p'))";
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
                        argsSpts[a] = "DATE_FORMAT({0},'" + argsSptsA + "')";
                    } else {
                        argsSpts[a] = "'" + argsSptsA + "'";
                    }
                    break;
            }
        }

        if (argsSpts.length == 1) {
            newFormat = argsSpts[0];
        } else if (argsSpts.length > 1) {
            newFormat = "CONCAT(" + String.join(", ", Arrays.stream(argsSpts).filter(a -> !a.equals("''")).toArray(String[]::new)) + ")";
        }

        return newFormat.replace("%_a1", "%m").replace("%_a2", "%s");
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}