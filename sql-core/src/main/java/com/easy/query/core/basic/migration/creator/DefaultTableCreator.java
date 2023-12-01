//package com.easy.query.core.basic.migration.creator;
//
//import com.easy.query.core.basic.migration.AbstractDbMappingEntry;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.metadata.ColumnMetadata;
//
//import java.math.BigDecimal;
//import java.sql.Blob;
//import java.sql.Clob;
//import java.sql.SQLXML;
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * create time 2023/11/29 21:58
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DefaultTableCreator<TEntity> extends AbstractTableCreator<TEntity> {
//    private static final Map<Class<?>, AbstractDbMappingEntry> typeMappingMap =new HashMap<>();
//    static {
//        typeMappingMap.put(BigDecimal.class, new DefaultDbMappingEntry(BigDecimal.class,"decimal","decimal(10,2)",false,false,0));
//        typeMappingMap.put(Boolean.class, new DefaultDbMappingEntry(Boolean.class,"bit","bit(1)",null,false,null));
//        typeMappingMap.put(boolean.class, new DefaultDbMappingEntry(boolean.class,"bit","bit(1)",null,false,false));
//        typeMappingMap.put(byte[].class, new DefaultDbMappingEntry(byte[].class,"varbinary","varbinary(255)",false,null,new byte[0]));
//        typeMappingMap.put(byte.class, new DefaultDbMappingEntry(byte.class,"tinyint","tinyint(3)",true,false,0));
//        typeMappingMap.put(Byte.class, byteTypeHandler);
//        typeMappingMap.put(char[].class, charArrayTypeHandler);
//        typeMappingMap.put(Double.class, doubleTypeHandler);
//        typeMappingMap.put(double.class, doubleTypeHandler);
//        typeMappingMap.put(Float.class, floatTypeHandler);
//        typeMappingMap.put(float.class, floatTypeHandler);
//        typeMappingMap.put(Integer.class, integerTypeHandler);
//        typeMappingMap.put(int.class, integerTypeHandler);
//        typeMappingMap.put(Long.class, longTypeHandler);
//        typeMappingMap.put(long.class, longTypeHandler);
//        typeMappingMap.put(Short.class, shortTypeHandler);
//        typeMappingMap.put(short.class, shortTypeHandler);
//        typeMappingMap.put(java.sql.Date.class, sqlDateTypeHandler);
//        typeMappingMap.put(java.util.Date.class, utilDateTypeHandler);
//        typeMappingMap.put(SQLXML.class, sqlXMLTypeHandler);
//        typeMappingMap.put(String.class, stringTypeHandler);
//        typeMappingMap.put(Timestamp.class, timestampTypeHandler);
//        typeMappingMap.put(Time.class, timeTypeHandler);
//        typeMappingMap.put(Clob.class, clobTypeHandler);
//        typeMappingMap.put(Blob.class, blobTypeHandler);
//        typeMappingMap.put(LocalDateTime.class,localDateTimeHandler);
//        typeMappingMap.put(LocalDate.class, localDateHandler);
//        typeMappingMap.put(LocalTime.class, localTimeTypeHandler);
//    }
//
//    public DefaultTableCreator(Class<TEntity> entityClass, QueryRuntimeContext runtimeContext) {
//        super(entityClass, runtimeContext);
//    }
//
//    @Override
//    protected String generateCreateScript() {
//        //获取表名
//        String tableName = getTableName();
//        StringBuilder sql = new StringBuilder();
//        sql.append("CREATE TABLE IF NOT EXISTS ").append(dialect.getQuoteName(tableName)).append(" ( ");
//        for (ColumnMetadata column : entityMetadata.getColumns()) {
//
//            sql.append(" \r\n  ").append(dialect.getQuoteName(column.getName())).append(" ").append(tbcol.Attribute.DbType);
//            if (tbcol.Attribute.IsIdentity == true && tbcol.Attribute.DbType.IndexOf("AUTO_INCREMENT", StringComparison.CurrentCultureIgnoreCase) == -1) sb.Append(" AUTO_INCREMENT");
//            if (string.IsNullOrEmpty(tbcol.Comment) == false) sb.Append(" COMMENT ").Append(_commonUtils.FormatSql("{0}", tbcol.Comment));
//            sb.Append(",");
//        }
//        return sql.toString();
//    }
//
//}
