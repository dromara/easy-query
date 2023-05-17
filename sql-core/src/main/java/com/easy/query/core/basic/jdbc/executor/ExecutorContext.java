package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.jdbc.parameter.SQLLikeParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.basic.plugin.encryption.EasyEncryptionStrategy;
import com.easy.query.core.util.StringUtil;

/**
 * @author xuejiaming
 * @FileName: ExecutorContext.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:51
 */
public class ExecutorContext {

    private final QueryRuntimeContext runtimeContext;
    private final EasyQueryConfiguration easyQueryConfiguration;
    private final boolean isQuery;
    private final ExecuteMethodEnum executeMethod;
    private final boolean tracking;

    public ExecutorContext(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod) {
        this(runtimeContext,isQuery,executeMethod, false);
    }

    public ExecutorContext(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod, boolean tracking) {
        this.runtimeContext = runtimeContext;
        this.easyQueryConfiguration = runtimeContext.getEasyQueryConfiguration();
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
     *
     * @param columnMetadata
     * @param value
     * @return
     */
    public Object getDecryptValue(Class<?> entityClass,ColumnMetadata columnMetadata, Object value) {
        if (value != null) {
            if (columnMetadata.isEncryption()) {
                Class<? extends EasyEncryptionStrategy> encryptionStrategy = columnMetadata.getEncryptionStrategy();
                EasyEncryptionStrategy easyEncryptionStrategy = easyQueryConfiguration.getEasyEncryptionStrategyNotNull(encryptionStrategy);
                return easyEncryptionStrategy.decrypt(entityClass,columnMetadata.getProperty().getName(),value);
            }
        }
        return value;
    }

    public Object getEncryptValue(SQLParameter sqlParameter, Object value) {
        if (value != null&&sqlParameter.getTable()!=null) {
            Class<?> entityClass = sqlParameter.getTable().getEntityClass();
            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
            String propertyName = sqlParameter.getPropertyName();
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            if (columnMetadata.isEncryption()) {
                if (sqlParameter instanceof SQLLikeParameter) {
                    if (columnMetadata.isSupportQueryLike()) {
                        EasyEncryptionStrategy easyEncryptionStrategy = getEncryptionStrategy(columnMetadata);
                        String likeValue = value.toString();
                        String encryptValue = StringUtil.endWithRemove(StringUtil.startWithRemove(likeValue, "%"), "%");
                        return StringUtil.startWithDefault(likeValue, "%", StringUtil.EMPTY) + easyEncryptionStrategy.encrypt(entityClass,propertyName,encryptValue) + StringUtil.endWithDefault(likeValue, "%", StringUtil.EMPTY);
                    }
                } else {
                    EasyEncryptionStrategy easyEncryptionStrategy = getEncryptionStrategy(columnMetadata);
                    return easyEncryptionStrategy.encrypt(entityClass,propertyName,value);
                }
            }
        }
        return value;
    }

    private EasyEncryptionStrategy getEncryptionStrategy(ColumnMetadata columnMetadata) {
        Class<? extends EasyEncryptionStrategy> encryptionStrategy = columnMetadata.getEncryptionStrategy();
        return easyQueryConfiguration.getEasyEncryptionStrategyNotNull(encryptionStrategy);
    }
    public boolean isQuery(){
        return isQuery;
    }

    public ExecuteMethodEnum getExecuteMethod() {
        return executeMethod;
    }
}
