package com.easy.query.processor.templates;

import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2023/12/5 17:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptSelectPropertyInfo {
    private final String propertyName;
    private final String comment;
    private final String proxyPropertyName;

    public AptSelectPropertyInfo(String propertyName, String comment,String proxyPropertyName){

        this.propertyName = propertyName;
        this.comment = comment;
        this.proxyPropertyName = proxyPropertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getProxyPropertyName() {
        if(EasyStringUtil.isNotBlank(proxyPropertyName)){
            return proxyPropertyName;
        }
        return propertyName;
    }

    public String getComment() {
        return comment;
    }

}
