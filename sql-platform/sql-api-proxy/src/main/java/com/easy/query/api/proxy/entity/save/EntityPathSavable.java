package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;

/**
 * create time 2025/9/10 10:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityPathSavable<TProxy extends ProxyEntity<TProxy, T>, T> extends SQLBatchExecute<EntityPathSavable<TProxy,T>> {
    List<T> getEntities();


    /**
     * 执行命令
     * @return
     */
    void executeCommand(SQLFuncExpression1<TProxy, List<MappingPath>> navigateIncludeSQLExpression);
}