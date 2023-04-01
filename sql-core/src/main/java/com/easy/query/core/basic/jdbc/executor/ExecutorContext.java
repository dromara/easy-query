package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
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

    private final EasyQueryRuntimeContext runtimeContext;
    private final EasyQueryConfiguration easyQueryConfiguration;
    private final boolean tracking;

    public ExecutorContext(EasyQueryRuntimeContext runtimeContext) {
        this(runtimeContext, false);
    }

    public ExecutorContext(EasyQueryRuntimeContext runtimeContext, boolean tracking) {
        this.runtimeContext = runtimeContext;
        this.easyQueryConfiguration = runtimeContext.getEasyQueryConfiguration();
        this.tracking = tracking;
    }

    public static ExecutorContext create(EasyQueryRuntimeContext runtimeContext) {
        return new ExecutorContext(runtimeContext);
    }

    public static ExecutorContext create(EasyQueryRuntimeContext runtimeContext, boolean tracking) {
        return new ExecutorContext(runtimeContext, tracking);
    }

    public EasyQueryRuntimeContext getRuntimeContext() {
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
    public Object getDecryptValue(ColumnMetadata columnMetadata, Object value) {
        if (value != null) {
            if (columnMetadata.isEncryption()) {
                Class<? extends EasyEncryptionStrategy> encryptionStrategy = columnMetadata.getEncryptionStrategy();
                EasyEncryptionStrategy easyEncryptionStrategy = easyQueryConfiguration.getEasyEncryptionStrategyNotNull(encryptionStrategy);
                return easyEncryptionStrategy.decrypt(value);
            }
        }
        return value;
    }

    public Object getEncryptValue(SQLParameter sqlParameter, Object value) {
        if (value != null) {
            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(sqlParameter.getTable().entityClass());
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(sqlParameter.getPropertyName());
            if (columnMetadata.isEncryption()) {
                if (sqlParameter instanceof SQLLikeParameter) {
                    if (columnMetadata.isSupportQuery()) {
                        EasyEncryptionStrategy easyEncryptionStrategy = getEncryptionStrategy(columnMetadata);
                        String likeValue = value.toString();
                        String encryptValue = StringUtil.endWithRemove(StringUtil.startWithRemove(likeValue, "%"), "%");
                        return StringUtil.startWithDefault(likeValue, "%", StringUtil.EMPTY) + easyEncryptionStrategy.encrypt(encryptValue) + StringUtil.endWithDefault(likeValue, "%", StringUtil.EMPTY);

                    }
                } else {
                    EasyEncryptionStrategy easyEncryptionStrategy = getEncryptionStrategy(columnMetadata);
                    return easyEncryptionStrategy.encrypt(value);
                }
            }
        }
        return value;
    }

    private EasyEncryptionStrategy getEncryptionStrategy(ColumnMetadata columnMetadata) {
        Class<? extends EasyEncryptionStrategy> encryptionStrategy = columnMetadata.getEncryptionStrategy();
        return easyQueryConfiguration.getEasyEncryptionStrategyNotNull(encryptionStrategy);
    }
}
