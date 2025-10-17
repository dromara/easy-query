package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.basic.api.save.Savable;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/9/5 16:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySavable<TProxy extends ProxyEntity<TProxy, T>, T> extends Savable, SQLBatchExecute<EntitySavable<TProxy, T>> {
    List<T> getEntities();

    EntitySavable<TProxy, T> configure(Consumer<SaveConfigurer> behaviorConfigure);

    EntitySavable<TProxy, T> savePath(SQLFuncExpression1<TProxy, List<MappingPath>> navigateIncludeSQLExpression);

    /**
     * 忽略根节点的保存
     * @return
     */
    default EntitySavable<TProxy, T> ignoreRoot() {
        return configure(s -> s.getSaveBehavior().add(SaveBehaviorEnum.ROOT_IGNORE));
    }

    default EntitySavable<TProxy, T> removeRoot() {
        return removeRoot(true);
    }

    /**
     * 删除聚合根并且删除所有子对象清空当前导航全属性为null
     *
     * @param remove
     * @return
     */
    EntitySavable<TProxy, T> removeRoot(boolean remove);

}
