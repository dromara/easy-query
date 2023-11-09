package com.easy.query.processor.templates;

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
    private final String packageName;
    private Set<String> imports;

    public AptFileCompiler(String packageName,String entityClassName,String entityClassProxyName) {
        this.packageName = packageName;
        this.entityClassName = entityClassName;
        this.entityClassProxyName = entityClassProxyName;
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
        imports.add("import " + fullClassPackageName + ";");
    }

}
