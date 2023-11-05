//package com.easy.query.core.metadata;
//
//import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
//import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
//import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
//import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
//import com.easy.query.core.enums.EntityMetadataTypeEnum;
//
//import java.util.Collection;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Set;
//import java.util.function.Supplier;
//
///**
// * create time 2023/10/30 10:54
// * 文件说明
// *
// * @author xuejiaming
// */
//public class ScriptEntityMetadata extends EntityMetadata{
//    public ScriptEntityMetadata(Class<?> entityClass) {
//        super(entityClass);
//    }
//
//    @Override
//    public String getTableName() {
//        return null;
//    }
//
//    @Override
//    public String getSchemaOrNull() {
//        return null;
//    }
//
//    @Override
//    public String getColumnName(String propertyName) {
//        return propertyName;
//    }
//
//    @Override
//    public String getPropertyNameOrNull(String columnName) {
//        return columnName;
//    }
//
//    @Override
//    public String getPropertyNameNotNull(String columnName) {
//        return columnName;
//    }
//
//    @Override
//    public String getPropertyNameOrNull(String columnName, String def) {
//        return columnName;
//    }
//
//    @Override
//    public ColumnMetadata getColumnMetadataOrNull(String columnName) {
//        return super.getColumnMetadataOrNull(columnName);
//    }
//
//    @Override
//    public Collection<ColumnMetadata> getColumns() {
//        return super.getColumns();
//    }
//
//    @Override
//    public Collection<String> getProperties() {
//        return super.getProperties();
//    }
//
//    @Override
//    public LinkedHashMap<String, ColumnMetadata> getProperty2ColumnMap() {
//        return super.getProperty2ColumnMap();
//    }
//
//    @Override
//    public Collection<String> getKeyProperties() {
//        return super.getKeyProperties();
//    }
//
//    @Override
//    public boolean isKeyProperty(String propertyName) {
//        return super.isKeyProperty(propertyName);
//    }
//
//    @Override
//    public NavigateMetadata getNavigateNotNull(String propertyName) {
//        return super.getNavigateNotNull(propertyName);
//    }
//
//    @Override
//    public NavigateMetadata getNavigateOrNull(String propertyName) {
//        return super.getNavigateOrNull(propertyName);
//    }
//
//    @Override
//    public ColumnMetadata getColumnNotNull(String propertyName) {
//        return super.getColumnNotNull(propertyName);
//    }
//
//    @Override
//    public ColumnMetadata getColumnOrNull(String propertyName) {
//        return super.getColumnOrNull(propertyName);
//    }
//
//    @Override
//    public void checkTable() {
//        super.checkTable();
//    }
//
//    @Override
//    public void setLogicDeleteMetadata(LogicDeleteMetadata logicDeleteMetadata) {
//        super.setLogicDeleteMetadata(logicDeleteMetadata);
//    }
//
//    @Override
//    public LogicDeleteMetadata getLogicDeleteMetadata() {
//        return super.getLogicDeleteMetadata();
//    }
//
//    @Override
//    public boolean enableLogicDelete() {
//        return super.enableLogicDelete();
//    }
//
//    @Override
//    public List<PredicateFilterInterceptor> getPredicateFilterInterceptors() {
//        return super.getPredicateFilterInterceptors();
//    }
//
//    @Override
//    public List<EntityInterceptor> getEntityInterceptors() {
//        return super.getEntityInterceptors();
//    }
//
//    @Override
//    public List<UpdateSetInterceptor> getUpdateSetInterceptors() {
//        return super.getUpdateSetInterceptors();
//    }
//
//    @Override
//    public List<String> getGeneratedKeyColumns() {
//        return super.getGeneratedKeyColumns();
//    }
//
//    @Override
//    public boolean hasVersionColumn() {
//        return super.hasVersionColumn();
//    }
//
//    @Override
//    public VersionMetadata getVersionMetadata() {
//        return super.getVersionMetadata();
//    }
//
//    @Override
//    public boolean isSharding() {
//        return super.isSharding();
//    }
//
//    @Override
//    public String getShardingDataSourcePropertyName() {
//        return super.getShardingDataSourcePropertyName();
//    }
//
//    @Override
//    public void setShardingDataSourcePropertyName(String shardingDataSourcePropertyName) {
//        super.setShardingDataSourcePropertyName(shardingDataSourcePropertyName);
//    }
//
//    @Override
//    public void addExtraShardingDataSourcePropertyName(String shardingExtraDataSourcePropertyName) {
//        super.addExtraShardingDataSourcePropertyName(shardingExtraDataSourcePropertyName);
//    }
//
//    @Override
//    public String getShardingTablePropertyName() {
//        return super.getShardingTablePropertyName();
//    }
//
//    @Override
//    public void setShardingTablePropertyName(String shardingTablePropertyName) {
//        super.setShardingTablePropertyName(shardingTablePropertyName);
//    }
//
//    @Override
//    public void addExtraShardingTablePropertyName(String shardingExtraTablePropertyName) {
//        super.addExtraShardingTablePropertyName(shardingExtraTablePropertyName);
//    }
//
//    @Override
//    public Collection<ActualTable> getActualTables() {
//        return super.getActualTables();
//    }
//
//    @Override
//    public void addActualTableWithDataSource(String dataSource, String actualTableName) {
//        super.addActualTableWithDataSource(dataSource, actualTableName);
//    }
//
//    @Override
//    public Collection<String> getDataSources() {
//        return super.getDataSources();
//    }
//
//    @Override
//    public Set<String> getShardingDataSourcePropertyNames() {
//        return super.getShardingDataSourcePropertyNames();
//    }
//
//    @Override
//    public Set<String> getShardingTablePropertyNames() {
//        return super.getShardingTablePropertyNames();
//    }
//
//    @Override
//    public ShardingInitConfig getShardingInitConfig() {
//        return super.getShardingInitConfig();
//    }
//
//    @Override
//    public boolean isColumnValueUpdateAtomicTrack() {
//        return super.isColumnValueUpdateAtomicTrack();
//    }
//
//    @Override
//    public Supplier<Object> getBeanConstructorCreator() {
//        return super.getBeanConstructorCreator();
//    }
//
//    @Override
//    public EntityMetadataTypeEnum getEntityMetadataType() {
//        return super.getEntityMetadataType();
//    }
//
//    @Override
//    public String getSingleKeyProperty() {
//        return super.getSingleKeyProperty();
//    }
//
//    @Override
//    public DataReader getDataReader() {
//        return super.getDataReader();
//    }
//}
