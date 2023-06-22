package com.easy.query.test.entity.base;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.core.base.impl.SQLColumnImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.test.entity.Topic;

import java.util.function.Function;

/**
 * create time 2023/6/21 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicProxy implements ProxyQuery<TopicProxy, Topic> {

    public static final TopicProxy TOPIC_PROXY = new TopicProxy();
    private static final Class<Topic> entityClass = Topic.class;

    public final SQLColumn<String> id;
    public final SQLColumn<String> name;
    private final TableAvailable table;

    private TopicProxy() {
        this.table = null;
        this.id = null;
        this.name = null;
    }

    public TopicProxy(TableAvailable table) {
        this.table = table;
        this.id = new SQLColumnImpl<>(table, "id");
        this.name = new SQLColumnImpl<>(table, "name");
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Topic> getEntityClass() {
        return entityClass;
    }

    @Override
    public TopicProxy create(Function<Class<Topic>, TopicProxy> creator) {
        return creator.apply(entityClass);
    }
}