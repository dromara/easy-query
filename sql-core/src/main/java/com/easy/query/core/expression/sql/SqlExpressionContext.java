package com.easy.query.core.expression.sql;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.UpdateStrategyEnum;

import java.util.List;

/**
 * @FileName: QueryExpressionContext.java
 * @Description: 文件说明
 * @Date: 2023/3/3 23:05
 * @author xuejiaming
 */
public interface SqlExpressionContext {
    EasyQueryRuntimeContext getRuntimeContext();

     List<SQLParameter> getParameters();
     void addParameter(SQLParameter parameter);

     String getAlias();
     String createTableAlias();
    String getQuoteName(String value);
    void extractParameters(SqlExpressionContext sqlExpressionContext);
    void clearParameters();
    void deleteThrow(boolean ifDeleteThrowException);
    boolean isDeleteThrow();

    /**
     * 禁用逻辑删除
     */
    void disableLogicDelete();

    /**
     * 启用逻辑删除
     */
    void enableLogicDelete();

    /**
     * 是否使用逻辑删除
     * @return
     */
    boolean isUseLogicDelete();


    void useInterceptor();
    void noInterceptor();
    boolean isUseInterceptor();
    void useUpdateStrategy(UpdateStrategyEnum updateStrategy);
    UpdateStrategyEnum getUpdateStrategy();

    void queryTracking(boolean tracking);
    boolean isTracking();
    void setVersion(Object version);
    Object getVersion();
}
