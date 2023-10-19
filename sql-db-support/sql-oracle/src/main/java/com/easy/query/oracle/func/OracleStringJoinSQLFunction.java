//package com.easy.query.oracle.func;
//
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
//import com.easy.query.core.func.column.ColumnExpression;
//
//import java.util.List;
//import java.util.Objects;
//
///**
// * create time 2023/10/11 22:45
// * 文件说明
// *
// * @author xuejiaming
// */
//public class OracleStringJoinSQLFunction extends ConcatSQLFunction {
//    private final String separator;
//    private final List<ColumnExpression> concatExpressions;
//
//    public OracleStringJoinSQLFunction(String separator, List<ColumnExpression> concatExpressions) {
//        super(concatExpressions);
//        this.concatExpressions = concatExpressions;
//        Objects.requireNonNull(separator, "join separator can not be null");
//        this.separator = separator;
//    }
//
//    @Override
//    public String sqlSegment(TableAvailable defaultTable) {
//        return getSQLSegment();
//    }
//
//    protected String getSQLSegment() {
//        StringBuilder result = new StringBuilder();
//        int j=0;
//        for (int i = 0; i < concatExpressions.size(); i++) {
//            if(i==0){
//                result.append("{").append(j).append("}");
//            }else{
//                j++;
//                result.append(String.format(" || {%s} || ", i++)).append(String.format("{%s}", i));
//            }
//        }
//        return result.toString();
//    }
//
//    @Override
//    public int paramMarks() {
//        return super.paramMarks() * 2 - 1;
//    }
//
//    @Override
//    protected void consume0(SQLNativeChainExpressionContext context) {
//        for (ColumnExpression columnExpression : getColumnExpressions()) {
//            invokeExpression0(context, columnExpression);
//            context.value(separator);
//        }
//    }
//
//}
