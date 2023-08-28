package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.basic.jdbc.parameter.SQLLikeParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
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
    private boolean mapToBeanStrict = true;
    private final ExecuteMethodEnum executeMethod;
    private final boolean tracking;
    private final EasyQueryOption easyQueryOption;

    public ExecutorContext(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod) {
        this(runtimeContext, isQuery, executeMethod, false);
    }

    public ExecutorContext(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod, boolean tracking) {
        this.runtimeContext = runtimeContext;
        this.easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        this.isQuery = isQuery;
        this.executeMethod = executeMethod;
        this.tracking = tracking;
    }

    public static ExecutorContext create(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod) {
        return new ExecutorContext(runtimeContext, isQuery, executeMethod);
    }

    public static ExecutorContext create(QueryRuntimeContext runtimeContext, boolean isQuery, ExecuteMethodEnum executeMethod, boolean tracking) {
        return new ExecutorContext(runtimeContext, isQuery, executeMethod, tracking);
    }

    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public boolean isTracking() {
        return tracking;
    }



    public boolean isQuery() {
        return isQuery;
    }


    public ExecuteMethodEnum getExecuteMethod() {
        return executeMethod;
    }

    public EasyQueryOption getEasyQueryOption() {
        return easyQueryOption;
    }

    public boolean isMapToBeanStrict() {
        return mapToBeanStrict;
    }

    public void setMapToBeanStrict(boolean mapToBeanStrict) {
        this.mapToBeanStrict = mapToBeanStrict;
    }
}
