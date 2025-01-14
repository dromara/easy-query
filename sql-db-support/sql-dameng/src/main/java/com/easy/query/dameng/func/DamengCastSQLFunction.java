package com.easy.query.dameng.func;

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
public class DamengCastSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final Class<?> targetClass;

    public DamengCastSQLFunction(List<ColumnExpression> columnExpressions, Class<?> targetClass) {

        this.columnExpressions = columnExpressions;
        this.targetClass = targetClass;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        String targetClassName = EasyClassUtil.getFullName(targetClass);
        switch (targetClassName) {
            case "boolean":
            case "java.lang.Boolean":
                return "CAST({0} AS BIT)";
            case "char":
                return "SUBSTR(TO_CHAR({0}), 1, 1)";
            case "java.sql.Time":
            case "java.time.Time":
                return "TO_TIMESTAMP({0},'HH24:MI:SS.FF6')";
            case "java.sql.Date":
            case "java.time.LocalDate":
                return "TO_TIMESTAMP({0},'YYYY-MM-DD')";
            case "java.sql.Timestamp":
            case "java.util.Date":
            case "java.time.LocalDateTime":
                return "TO_TIMESTAMP({0},'YYYY-MM-DD HH24:MI:SS.FF6')";
            case "java.math.BigDecimal":
                return "CAST({0} AS DECIMAL(18,4))";
            case "double":
            case "float":
            case "java.lang.Float":
            case "java.lang.Double":
                return "CAST({0} AS FLOAT)";
            case "int":
            case "java.lang.Integer":
                return "CAST({0} AS INT)";
            case "long":
            case "java.lang.Long":
                return "CAST({0} AS BIGINT)";
            case "byte":
            case "short":
            case "java.lang.Byte":
            case "java.lang.Short":
                return "CAST({0} AS NUMBER)";
            case "java.util.UUID":
            case "java.lang.String":
                return "TO_CHAR({0})";
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
