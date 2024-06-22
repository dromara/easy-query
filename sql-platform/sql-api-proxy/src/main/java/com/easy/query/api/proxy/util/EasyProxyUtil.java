package com.easy.query.api.proxy.util;

import com.easy.query.core.proxy.ProxyNavValueAvailable;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2024/6/8 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyUtil {

    public static String getNavValue(ProxyNavValueAvailable proxyNavValueAvailable) {
        String navValue = proxyNavValueAvailable.getNavValue();
        if(proxyNavValueAvailable instanceof TablePropColumn){
            String value = proxyNavValueAvailable.getValue();
            String endProp = "." + value;
            return EasyStringUtil.endWithRemove(navValue,endProp);
        }
        return navValue;
    }

    public static  String getFullNavValue(ProxyNavValueAvailable proxyNavValueAvailable) {
        return proxyNavValueAvailable.getNavValue();
    }
}
