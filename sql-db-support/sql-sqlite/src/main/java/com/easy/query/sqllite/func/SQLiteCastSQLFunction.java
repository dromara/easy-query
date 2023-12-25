package com.easy.query.sqllite.func;

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
public class SQLiteCastSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final Class<?> targetClass;

    public SQLiteCastSQLFunction(List<ColumnExpression> columnExpressions, Class<?> targetClass) {

        this.columnExpressions = columnExpressions;
        this.targetClass = targetClass;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        String targetClassName = EasyClassUtil.getFullName(targetClass);
        switch (targetClassName){
            case "boolean":
            case "java.lang.Boolean": return "({0} NOT IN ('0','false'))";
            case "char": return "substr(cast({0} as character), 1, 1)";
            case "java.sql.Time":
            case "java.time.Time": return "time({0})";
            case "java.sql.Date":
            case "java.time.LocalDate": return "date({0})";
            case "java.sql.Timestamp":
            case "java.util.Date":
            case "java.time.LocalDateTime": return "datetime({0})";
            case "java.math.BigDecimal": return "cast({0} as decimal(36,18))";
            case "double":
            case "float":
            case "java.lang.Float":
            case "java.lang.Double": return "cast({0} as double)";
            case "byte":
            case "java.lang.Byte":
                return "cast({0} as int2)";
            case "short":
            case "java.lang.Short":return "CAST({0} AS SMALLINT)";
            case "int":
            case "long":
            case "java.lang.Integer":
            case "java.lang.Long": return "CAST({0} AS SIGNED)";
            case "java.util.UUID": return "substr(cast({0} as character), 1, 36)";
            case "java.lang.String": return "cast({0} as character)";
        }
        throw new UnsupportedOperationException("不支持当前转换函数:"+targetClassName);
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
