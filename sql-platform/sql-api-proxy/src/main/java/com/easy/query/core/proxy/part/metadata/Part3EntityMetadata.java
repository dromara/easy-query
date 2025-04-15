//package com.easy.query.core.proxy.part.metadata;
//
//import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.proxy.part.Part3;
//
///**
// * create time 2024/8/6 11:25
// * 文件说明
// *
// * @author xuejiaming
// */
//public class Part3EntityMetadata extends Part2EntityMetadata {
//
//    private final JdbcTypeHandler jdbcTypeHandler3;
//
//    public Part3EntityMetadata(Class<?> entityClass, EntityMetadata entityMetadata, JdbcTypeHandler jdbcTypeHandler1, JdbcTypeHandler jdbcTypeHandler2, JdbcTypeHandler jdbcTypeHandler3) {
//        super(entityClass, entityMetadata, jdbcTypeHandler1,jdbcTypeHandler2);
//        this.jdbcTypeHandler3 = jdbcTypeHandler3;
//    }
//
//    @Override
//    protected boolean isPartByColumn(String propertyName) {
//        if(Part3.PART_COLUMN3.equals(propertyName)){
//            return true;
//        }
//        return super.isPartByColumn(propertyName);
//    }
//
//    @Override
//    protected JdbcTypeHandler getPartJdbcTypeHandler(String propertyName) {
//        if(Part3.PART_COLUMN3.equals(propertyName)){
//            return jdbcTypeHandler3;
//        }
//        return super.getPartJdbcTypeHandler(propertyName);
//    }
//}
