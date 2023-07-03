//package com.easy.query.test.func;
//
//import com.easy.query.api4j.sql.SQLColumnAsSelector;
//import com.easy.query.api4j.sql.SQLWherePredicate;
//import com.easy.query.core.expression.lambda.SQLExpression1;
//import com.easy.query.core.expression.segment.SQLColumnSegment;
//import com.easy.query.core.util.EasyObjectUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * create time 2023/7/1 22:39
// * 文件说明
// *
// * @author xuejiaming
// */
//public class CaseWhenBuilder<T1,TR> {
//    private final SQLColumnAsSelector<T1, TR> sqlColumnAsSelector;
//    private List<Tuple2<SQLExpression1<SQLWherePredicate<T1>>,Object>> whens;
//
//    public CaseWhenBuilder(SQLColumnAsSelector<T1, TR> sqlColumnAsSelector){
//        this.sqlColumnAsSelector = sqlColumnAsSelector;
//        whens=new ArrayList<>();
//    }
//    public CaseWhenBuilder<T1,TR> caseWhen(SQLExpression1<SQLWherePredicate<T1>> predicate,Object then){
//        whens.add(new Tuple2<>(predicate,then));
//        return this;
//    }
//    public SQLColumnSegment elseEnd(Object elseValue){
//        return new CaseWhenSQLColumnSegment(sqlColumnAsSelector.getRuntimeContext(),sqlColumnAsSelector.getExpressionContext(), sqlColumnAsSelector.getTable(), EasyObjectUtil.typeCastNullable(whens),elseValue);
//    }
//}
