//package com.easy.query.core.basic.jdbc.executor.impl.proxy;
//
//import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
//import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultMetadata;
//import com.easy.query.core.metadata.ColumnMetadata;
//import com.easy.query.core.metadata.EntityMetadata;
//import com.easy.query.core.proxy.ProxyEntity;
//
///**
// * create time 2023/6/30 22:48
// * 文件说明
// *
// * @author xuejiaming
// */
//public class ProxyEntityResultMetadata<TRProxy extends ProxyEntity<TRProxy,TR>,TR> extends EntityResultMetadata<TR> {
//
//    private final TRProxy proxy;
//
//    public ProxyEntityResultMetadata(TRProxy proxy, EntityMetadata entityMetadata){
//        super(entityMetadata);
//
//        this.proxy = proxy;
//    }
//
//    @Override
//    public TR newBean() {
//        return proxy.createEntity();
//    }
//
//    @Override
//    public ResultColumnMetadata getResultColumnOrNullByColumnName(String columnName) {
//        ColumnMetadata columnMetadata = entityMetadata.getColumnMetadataOrNull(columnName);
//        if(columnMetadata!=null){
//            return new ProxyEntityResultColumnMetadata(proxy,columnMetadata);
//        }
//        return null;
//    }
//
//    @Override
//    public ResultColumnMetadata getResultColumnOrNullByPropertyName(String propertyName) {
//        ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(propertyName);
//        if(columnMetadata!=null){
//            return new ProxyEntityResultColumnMetadata(proxy,columnMetadata);
//        }
//        return null;
//    }
//}
