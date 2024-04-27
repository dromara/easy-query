package com.easy.query.clickhouse.func;

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
public class ClickHouseCastSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final Class<?> targetClass;

    public ClickHouseCastSQLFunction(List<ColumnExpression> columnExpressions, Class<?> targetClass) {

        this.columnExpressions = columnExpressions;
        this.targetClass = targetClass;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        String targetClassName = EasyClassUtil.getFullName(targetClass);
        switch (targetClassName) {
            case "boolean":
            case "java.lang.Boolean": return "({0} NOT IN ('0','false'))";
            case "java.lang.Byte":
            case "byte":
                return "CAST({0} AS INT8)";
            case "char":
                return "SUBSTR(CAST({0} AS STRING), 1, 1)";
//            case "java.sql.Time":
//            case "java.time.Time":
//                return "TO_TIMESTAMP({0},'HH24:MI:SS.FF6')";
//            case "java.sql.Date":
//            case "java.time.LocalDate":
//                return "TO_TIMESTAMP({0},'YYYY-MM-DD')";
//            case "java.sql.Timestamp":
            case "java.util.Date":
            case "java.time.LocalDateTime":
                return "CAST({0} AS DATETIME)";
            case "java.math.BigDecimal":
                return "CAST({0} AS DECIMAL128(19))";
            case "double":
            case "float":
            case "java.lang.Float":
            case "java.lang.Double":
                return "CAST({0} AS FLOAT64)";
            case "short":
            case "java.lang.Short":
                return "CAST({0} AS INT16)";
            case "int":
            case "java.lang.Integer":
                return "CAST({0} AS INT32)";
            case "long":
            case "java.lang.Long":
                return "CAST({0} AS INT64)";
            case "java.util.UUID":
                return "SUBSTR(CAST({0} AS STRING), 1, 36)";
            case "java.lang.String":
                return "CAST({0} AS STRING)";
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
