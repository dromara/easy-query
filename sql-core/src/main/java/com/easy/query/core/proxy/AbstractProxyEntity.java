package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy,TEntity> {

    protected TableAvailable table;
    public <TProperty> SQLColumn<TProxy,TProperty> get(PropColumn propColumn){
        return new SQLColumnImpl<>(table,propColumn);
    }

    @Override
    public TableAvailable getTable() {
        Objects.requireNonNull(table,"cant found table in sql context");
        return table;
    }
    @Override
    public TProxy create(TableAvailable table) {
        if(this.table==null){
            this.table=table;
        }
        return EasyObjectUtil.typeCastNullable(this);
    }
}
