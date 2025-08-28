package com.easy.query.oracle.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.enums.MathMethodEnum;

import java.util.List;

/**
 * create time 2023/10/12 15:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class OracleMathSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final MathMethodEnum mathMethodEnum;

    public OracleMathSQLFunction(List<ColumnExpression> columnExpressions, MathMethodEnum mathMethodEnum) {

        this.columnExpressions = columnExpressions;
        this.mathMethodEnum = mathMethodEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        switch (mathMethodEnum){
            case Abs:
                return "ABS({0})";
            case Signum:
                return "SIGN({0})";
            case Floor:
                return "FLOOR({0})";
            case Ceiling:
                return "CEIL({0})";
            case Round:{
                if(columnExpressions.size()>1){
                    return "ROUND({0},{1})";
                }
                return "ROUND({0})";
            }
            case Exp:
                return "EXP({0})";
            case Log:{
                if(columnExpressions.size()>1){
                    return "LOG({1},{0})";
                }
                return "LOG(2.7182818284590451,{0})";
            }
            case Log10:
                return "LOG(10,{0})";
            case Pow:{
                if(columnExpressions.size()<2){
                    throw new IllegalArgumentException("pow方法至少需要两个参数");
                }
                return "POWER({0},{1})";
            }
            case Sqrt:
                return "SQRT({0})";
            case Cos:
                return "COS({0})";
            case Sin:
                return "SIN({0})";
            case Tan:
                return "TAN({0})";
            case Acos:
                return "ACOS({0})";
            case Asin:
                return "ASIN({0})";
            case Atan:
                return "ATAN({0})";
//            case Atan2:{
//                if(columnExpressions.size()<2){
//                    throw new IllegalArgumentException("Atan2方法至少需要两个参数");
//                }
//                return "ATAN2({0},{1})";
//            }
            case Truncate:
                return "TRUNC({0},0)";
        }
        throw new UnsupportedOperationException("不支持当前函数OracleMathSQLFunction:"+ mathMethodEnum);
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
