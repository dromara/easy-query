package com.easy.query.core.proxy;

import com.easy.query.api.proxy.util.EasyProxyUtil;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;

/**
 * create time 2024/6/8 20:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyNavValueAvailable extends EntitySQLContextAvailable, PropColumn, MappingPath {

    default String getNavValue(){
        return null;
    }
    default void setNavValue(String val){

    }

    @Override
    default String __getMappingPath(){
        return EasyProxyUtil.getFullNavValue(this);
    }
}
