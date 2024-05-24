package com.easy.query.processor.helper;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * create time 2023/11/8 16:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptFileCompiler {
    private final String entityClassName;
    private final String entityClassProxyName;
    private final AptSelectorInfo selectorInfo;
    private final String packageName;
    private Set<String> imports;

    public AptFileCompiler(String packageName,String entityClassName,String entityClassProxyName,AptSelectorInfo selectorInfo) {
        this.packageName = packageName;
        this.entityClassName = entityClassName;
        this.entityClassProxyName = entityClassProxyName;
        this.selectorInfo = selectorInfo;
        this.imports = new LinkedHashSet<>();
    }

    public String getEntityClassProxyName() {
        return entityClassProxyName;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void addImports(String fullClassPackageName) {
        if(fullClassPackageName!=null){
            imports.add("import " + fullClassPackageName + ";");
        }
    }

    public AptSelectorInfo getSelectorInfo() {
        return selectorInfo;
    }
}
