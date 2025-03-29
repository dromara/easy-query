package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.grouping.Grouping1;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/12/28 14:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class Grouping1Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TSourceProxy> extends AbstractGroupingProxy<Grouping1Proxy<TKey1Proxy, TKey1, TSourceProxy>, Grouping1<TKey1>,TSourceProxy> {

    private static final Class<Grouping1> entityClass = Grouping1.class;
    private final TKey1Proxy k1;

    public Grouping1Proxy(TKey1Proxy k1, TSourceProxy tSourceProxy) {
        super(tSourceProxy);
        this.k1 = k1;
    }


    @Override
    public Class<Grouping1<TKey1>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }

    public TKey1Proxy key1() {
        return k1;
    }

    @Override
    public List<PropTypeColumn<?>> getKeys() {
        return Collections.singletonList(k1);
    }

    @Override
    public void accept(GroupSelector s) {
        acceptGroupSelector(k1, s);
    }

//    public TSourceProxy element(int index) {
//        return new DefaultSQLSingleGroupQueryable<>(tSourceProxy, this.entitySQLContext, null).element(index);
//    }
//
//    @Override
//    public SQLSingleGroupQueryable<TSourceProxy> where(SQLExpression1<TSourceProxy> where) {
//        return new DefaultSQLSingleGroupQueryable<>(tSourceProxy, this.entitySQLContext, where);
//    }
}