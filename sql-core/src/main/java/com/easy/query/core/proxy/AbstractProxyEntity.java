package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy,TEntity> {

    protected TableAvailable table;
    public <TProperty> SQLColumn<TProxy,TProperty> get(String property){
        return new SQLColumnImpl<>(getTable(),property);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
    @Override
    public TProxy create(TableAvailable table) {
        if(this.table==table){
            return EasyObjectUtil.typeCastNullable(this);
        }else{
            if(this.table!=null){
                throw new IllegalArgumentException("proxy repeat create table");
            }
            this.table=table;
        }
        return EasyObjectUtil.typeCastNullable(this);
    }
}
