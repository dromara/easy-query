package com.easy.query.core.basic.extension.navigate;

/**
 * create time 2024/4/29 11:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class NamedGuessResult {
    private String navigatePropertyName;
    private String propertyName;
    public NamedGuessResult(String navigatePropertyName,String propertyName){
        this.navigatePropertyName=navigatePropertyName;
        this.propertyName=propertyName;
    }

    public String getNavigatePropertyName() {
        return navigatePropertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
