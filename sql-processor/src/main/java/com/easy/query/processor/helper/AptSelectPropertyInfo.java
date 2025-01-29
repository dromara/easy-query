package com.easy.query.processor.helper;

import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.processor.FieldComment;

/**
 * create time 2023/12/5 17:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptSelectPropertyInfo {
    private final String propertyName;
    private final FieldComment fieldComment;
    private final String proxyPropertyName;

    public AptSelectPropertyInfo(String propertyName, FieldComment fieldComment, String proxyPropertyName){

        this.propertyName = propertyName;
        this.fieldComment = fieldComment;
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
        return fieldComment.proxyComment;
    }

    public String getEntityComment() {
        return fieldComment.entityComment;
    }

}
