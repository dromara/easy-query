package com.easy.query.kingbase.es.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyClassUtil;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class KingbaseESCastSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final Class<?> targetClass;

    public KingbaseESCastSQLFunction(List<ColumnExpression> columnExpressions, Class<?> targetClass) {

        this.columnExpressions = columnExpressions;
        this.targetClass = targetClass;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        String targetClassName = EasyClassUtil.getFullName(targetClass);
        switch (targetClassName) {
            case "boolean":
            case "java.lang.Boolean":
                return "(({0})::varchar not in ('0','false','f','no'))";
            case "byte":
            case "java.lang.Byte":
                return "({0})::int2";
            case "char":
                return "substr(({0})::char, 1, 1)";
            case "java.sql.Time":
            case "java.time.Time":
                return "({0})::time";
            case "java.sql.Date":
            case "java.time.LocalDate":
                return "({0})::date";
            case "java.sql.Timestamp":
            case "java.util.Date":
            case "java.time.LocalDateTime":
                return "({0})::timestamp";
            case "java.math.BigDecimal":
                return "({0})::numeric";
            case "double":
            case "java.lang.Double":
            case "float":
            case "java.lang.Float":
                return "({0})::float8";
            case "short":
            case "java.lang.Short":
                return "({0})::int2";
            case "int":
            case "java.lang.Integer":
                return "({0})::int4";
            case "long":
            case "java.lang.Long":
                return "({0})::int8";
            case "java.util.UUID":
                return "({0})::uuid";
            case "java.lang.String":
                return "({0})::text";
        }
        throw new UnsupportedOperationException("不支持当前转换函数:" + targetClassName);
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }

}
