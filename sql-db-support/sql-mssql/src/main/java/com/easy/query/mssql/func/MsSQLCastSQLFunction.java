package com.easy.query.mssql.func;

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
public class MsSQLCastSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final Class<?> targetClass;

    public MsSQLCastSQLFunction(List<ColumnExpression> columnExpressions, Class<?> targetClass) {

        this.columnExpressions = columnExpressions;
        this.targetClass = targetClass;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        String targetClassName = EasyClassUtil.getFullName(targetClass);
        switch (targetClassName) {
            case "boolean":
            case "java.lang.Boolean":
                return "(cast({0} as varchar) not in ('0','false'))";
            case "byte":
            case "java.lang.Byte":
                return "cast({0} as tinyint)";
            case "char":
                return "substring(cast({0} as nvarchar),1,1)";
            case "java.sql.Time":
            case "java.time.Time":
                return "cast({0} as time)";
            case "java.sql.Date":
            case "java.time.LocalDate":
                return "cast({0} as date)";
            case "java.sql.Timestamp":
            case "java.util.Date":
            case "java.time.LocalDateTime":
                return "cast({0} as datetime)";
            case "java.math.BigDecimal":
                return "cast({0} as decimal(36,18))";
            case "double":
            case "java.lang.Double":
            case "float":
            case "java.lang.Float":
                return "cast({0} as decimal(32,16))";
            case "short":
            case "java.lang.Short":
                return "cast({0} as smallint)";
            case "int":
            case "java.lang.Integer":
                return "cast({0} as int)";
            case "long":
            case "java.lang.Long":
                return "cast({0} as bigint)";
            case "java.util.UUID":
                return "cast({0} as uniqueidentifier)";
            case "java.lang.String":
                return "cast({0} as nvarchar(max))";
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
