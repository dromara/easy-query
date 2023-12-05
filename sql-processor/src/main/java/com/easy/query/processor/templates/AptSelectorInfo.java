package com.easy.query.processor.templates;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/12/5 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptSelectorInfo {
    private final String name;
    private final List<AptSelectPropertyInfo> properties;

    public AptSelectorInfo(String name){

        this.name = name;
        this.properties=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<AptSelectPropertyInfo> getProperties() {
        return properties;
    }
}
