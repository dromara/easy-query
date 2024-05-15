package com.easy.query.core.metadata;

/**
 * create time 2024/5/15 09:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateFlatProperty {
    private final NavigateFlatMetadata navigateFlatMetadata;
    private final String property;
    private final int nextCount;

    public NavigateFlatProperty(NavigateFlatMetadata navigateFlatMetadata, String property, int nextCount){

        this.navigateFlatMetadata = navigateFlatMetadata;
        this.property = property;
        this.nextCount = nextCount;
    }

    public NavigateFlatMetadata getNavigateFlatMetadata() {
        return navigateFlatMetadata;
    }

    public String getProperty() {
        return property;
    }

    public int getNextCount() {
        return nextCount;
    }
}
