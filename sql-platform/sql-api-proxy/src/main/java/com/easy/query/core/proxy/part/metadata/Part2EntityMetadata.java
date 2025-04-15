//package com.easy.query.core.proxy.part.metadata;
//
//import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.proxy.part.Part2;
//
///**
// * create time 2024/8/6 11:25
// * 文件说明
// *
// * @author xuejiaming
// */
//public class Part2EntityMetadata extends PartEntityMetadata {
//
//    private final JdbcTypeHandler jdbcTypeHandler2;
//
//    public Part2EntityMetadata(Class<?> entityClass, EntityMetadata entityMetadata, JdbcTypeHandler jdbcTypeHandler1, JdbcTypeHandler jdbcTypeHandler2) {
//        super(entityClass, entityMetadata, jdbcTypeHandler1);
//        this.jdbcTypeHandler2 = jdbcTypeHandler2;
//    }
//
//    @Override
//    protected boolean isPartByColumn(String propertyName) {
//        if(Part2.PART_COLUMN2.equals(propertyName)){
//            return true;
//        }
//        return super.isPartByColumn(propertyName);
//    }
//
//    @Override
//    protected JdbcTypeHandler getPartJdbcTypeHandler(String propertyName) {
//        if(Part2.PART_COLUMN2.equals(propertyName)){
//            return jdbcTypeHandler2;
//        }
//        return super.getPartJdbcTypeHandler(propertyName);
//    }
//}
