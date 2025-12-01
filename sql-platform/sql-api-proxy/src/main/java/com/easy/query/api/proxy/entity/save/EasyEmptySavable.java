package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/9/5 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptySavable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntitySavable<TProxy,T>{
    @Override
    public List<T> getEntities() {
        return Collections.emptyList();
    }

    @Override
    public EntitySavable<TProxy, T> configure(Consumer<SaveConfigurer> behaviorConfigure) {
        return this;
    }

    @Override
    public EntitySavable<TProxy, T> savePath(SQLFuncExpression1<TProxy, List<MappingPath>> navigateIncludeSQLExpression) {
        return this;
    }

    @Override
    public EntitySavable<TProxy, T> removeRoot(boolean removeRoot) {
        return this;
    }

    @Override
    public void executeCommand() {

    }

    @Override
    public void executeCommand2() {

    }

    @Override
    public EntitySavable<TProxy, T> batch(boolean use) {
        return this;
    }
}
