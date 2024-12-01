//package com.easy.query.core.expression.segment.impl;
//
//import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
//import com.easy.query.core.expression.segment.SQLSegment;
//
//import java.util.function.Supplier;
//
///**
// * create time 2023/10/19 08:19
// * 文件说明
// *
// * @author xuejiaming
// */
//public class LazySQLSegmentImpl implements SQLSegment {
//    private final Supplier<SQLSegment> sqlSegmentSupplier;
//
//    public LazySQLSegmentImpl(Supplier<SQLSegment> sqlSegmentSupplier){
//
//        this.sqlSegmentSupplier = sqlSegmentSupplier;
//    }
//    @Override
//    public String toSQL(ToSQLContext toSQLContext) {
//        SQLSegment sqlSegment = sqlSegmentSupplier.get();
//        return sqlSegment.toSQL(toSQLContext);
//    }
//}
