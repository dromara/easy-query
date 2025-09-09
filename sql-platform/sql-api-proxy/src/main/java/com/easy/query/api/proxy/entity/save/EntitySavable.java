package com.easy.query.api.proxy.entity.save;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.basic.api.save.Savable;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.metadata.IncludePathTreeNode;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyUtil;

import java.util.List;

/**
 * create time 2025/9/5 16:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySavable<TProxy extends ProxyEntity<TProxy, T>, T> extends Savable, SQLBatchExecute<EntitySavable<TProxy,T>> {
    List<T> getEntities();

    EntitySavable<TProxy, T> checkMode(SaveCheckModeEnum saveCheckMode);
    EntitySavable<TProxy, T> savePath(SQLFuncExpression1<TProxy, List<MappingPath>> navigateIncludeSQLExpression);

}
