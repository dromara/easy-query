package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.configuration.LoadIncludeConfiguration;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.trigger.TriggerEvent;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2023/9/19 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyEntityQuery extends BaseEntityClient{
}
