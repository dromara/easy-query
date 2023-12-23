//package com.easy.query.core.func.def.impl;
//
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.func.column.ColumnExpression;
//import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
//import com.easy.query.core.util.EasyCollectionUtil;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
///**
// * create time 2023/12/21 11:58
// * 文件说明
// *
// * @author xuejiaming
// */
//public class PlusTimeUnitSQLFunction extends AbstractExpressionSQLFunction {
//    private final List<ColumnExpression> columnExpressions;
//    private final TimeUnit timeUnit;
//
//    public PlusTimeUnitSQLFunction(List<ColumnExpression> columnExpressions, TimeUnit timeUnit) {
//
//        this.columnExpressions = columnExpressions;
//        this.timeUnit = timeUnit;
//    }
//
//    @Override
//    public String sqlSegment(TableAvailable defaultTable) {
//        timeUnit.
//        Iterable<String> params = EasyCollectionUtil.select(columnExpressions, (t, i) -> "{" + i + "}");
//        return String.format("TRIM(%s)", String.join(",", params));
//        return "DATE_ADD({LEFT}, INTERVAL ({ARGS1}) MICROSECOND)";
//    }
//
//    @Override
//    public int paramMarks() {
//        return columnExpressions.size();
//    }
//
//    @Override
//    protected List<ColumnExpression> getColumnExpressions() {
//        return columnExpressions;
//    }
//
//}
