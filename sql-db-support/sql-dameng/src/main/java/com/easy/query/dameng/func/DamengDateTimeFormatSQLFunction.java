package com.easy.query.dameng.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2023/10/6 21:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDateTimeFormatSQLFunction extends AbstractExpressionSQLFunction {
    private static final Pattern FORMAT_PATTERN = Pattern.compile("(yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]|(?:(?!yyyy|yy|MM|dd|HH|hh|mm|ss|[MdHhmsa]).)+)");

    private final List<ColumnExpression> columnExpressions;
    private final String javaFormat;

    public DamengDateTimeFormatSQLFunction(List<ColumnExpression> columnExpressions, String javaFormat) {
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
        StringJoiner sj = new StringJoiner("||");
        while (matcher.find()) {
            String match = matcher.group(1);
            switch (match) {
                case "yyyy":
                    sj.add("TO_CHAR({0}, 'YYYY')");
                    break;
                case "yy":
                    sj.add("TO_CHAR({0}, 'YY')");
                    break;
                case "MM":
                    sj.add("TO_CHAR({0}, 'MM')");
                    break;
                case "M":
                    sj.add("TO_CHAR({0}, 'FMMM')");
                    break;
                case "dd":
                    sj.add("TO_CHAR({0}, 'DD')");
                    break;
                case "d":
                    sj.add("TO_CHAR({0}, 'FMDD')");
                    break;
                case "HH":
                    sj.add("TO_CHAR({0}, 'HH24')");
                    break;
                case "H":
                    sj.add("TO_CHAR({0}, 'FMHH24')");
                    break;
                case "hh":
                    sj.add("TO_CHAR({0}, 'HH')");
                    break;
                case "h":
                    sj.add("TO_CHAR({0}, 'FMHH')");
                    break;
                case "mm":
                    sj.add("TO_CHAR({0}, 'MI')");
                    break;
                case "m":
                    sj.add("TO_CHAR({0}, 'FMMI')");
                    break;
                case "ss":
                    sj.add("TO_CHAR({0}, 'SS')");
                    break;
                case "s":
                    sj.add("TO_CHAR({0}, 'FMSS')");
                    break;
                case "a":
                    sj.add("TO_CHAR({0}, 'AM')");
                    break;
                default:
                    sj.add("'" + match + "'");
                    break;
            }
        }
        return sj.toString();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
