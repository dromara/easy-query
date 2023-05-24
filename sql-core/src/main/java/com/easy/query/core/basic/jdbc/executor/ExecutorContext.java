package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.plugin.conversion.ValueConverter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.jdbc.parameter.SQLLikeParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.plugin.encryption.EncryptionStrategy;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;

/**
 * @author xuejiaming
 * @FileName: ExecutorContext.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:51
 */
public class ExecutorContext {

    private final QueryRuntimeContext runtimeContext;
    private final boolean isQuery;
    private final ExecuteMethodEnum executeMethod;
    private final boolean tracking;

    public ExecutorContext(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod) {
        this(runtimeContext,isQuery,executeMethod, false);
    }

    public ExecutorContext(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod, boolean tracking) {
        this.runtimeContext = runtimeContext;
        this.isQuery = isQuery;
        this.executeMethod = executeMethod;
        this.tracking = tracking;
    }

    public static ExecutorContext create(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod) {
        return new ExecutorContext(runtimeContext,isQuery,executeMethod);
    }

    public static ExecutorContext create(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod, boolean tracking) {
        return new ExecutorContext(runtimeContext,isQuery,executeMethod, tracking);
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public boolean isTracking() {
        return tracking;
    }

    /**
     * 如果当前value存在加密字段那么会自动解密
     * 数据库转属性
     * @param columnMetadata
     * @param value
     * @return
     */
    public Object fromValue(Class<?> entityClass, ColumnMetadata columnMetadata, Object value) {
        Object fromValue = fromValue0(entityClass, columnMetadata, value);
        Class<?> propertyType = columnMetadata.getPropertyType();
        return columnMetadata.getValueConverter().deserialize(EasyObjectUtil.typeCast(propertyType),EasyObjectUtil.typeCast(fromValue));
    }
    private Object fromValue0(Class<?> entityClass, ColumnMetadata columnMetadata, Object value){
        if (value != null) {
            if (columnMetadata.isEncryption()) {
                EncryptionStrategy easyEncryptionStrategy = columnMetadata.getEncryptionStrategy();
                return easyEncryptionStrategy.decrypt(entityClass,columnMetadata.getProperty().getName(),value);
            }
        }
        return value;
    }

    public Object toValue(SQLParameter sqlParameter, Object value) {
        if(sqlParameter.getTableOrNull()!=null){
            EntityMetadata entityMetadata = sqlParameter.getTableOrNull().getEntityMetadata();
            String propertyName = sqlParameter.getPropertyNameOrNull();
            if(propertyName!=null){
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                ValueConverter<?, ?> valueConverter = columnMetadata.getValueConverter();
                if(value!=null){
                    Object toValue = toValue(columnMetadata, sqlParameter, value, entityMetadata.getEntityClass(), propertyName);
                    return valueConverter.serialize(EasyObjectUtil.typeCast(toValue));
                }
                return valueConverter.serialize(null);
            }
        }
        return value;
    }
    private Object toValue(ColumnMetadata columnMetadata, SQLParameter sqlParameter, Object value, Class<?> entityClass, String propertyName){

        if (columnMetadata.isEncryption()) {
            if (sqlParameter instanceof SQLLikeParameter) {
                if (columnMetadata.isSupportQueryLike()) {
                    EncryptionStrategy easyEncryptionStrategy = columnMetadata.getEncryptionStrategy();
                    String likeValue = value.toString();
                    String encryptValue = EasyStringUtil.endWithRemove(EasyStringUtil.startWithRemove(likeValue, "%"), "%");
                    return EasyStringUtil.startWithDefault(likeValue, "%", EasyStringUtil.EMPTY) + easyEncryptionStrategy.encrypt(entityClass,propertyName,encryptValue) + EasyStringUtil.endWithDefault(likeValue, "%", EasyStringUtil.EMPTY);
                }
            } else {
                EncryptionStrategy easyEncryptionStrategy = columnMetadata.getEncryptionStrategy();
                return easyEncryptionStrategy.encrypt(entityClass,propertyName,value);
            }
        }
        return value;
    }
    public boolean isQuery(){
        return isQuery;
    }

    public ExecuteMethodEnum getExecuteMethod() {
        return executeMethod;
    }
}
