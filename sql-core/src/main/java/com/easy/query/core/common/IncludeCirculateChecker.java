package com.easy.query.core.common;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2024/2/26 21:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeCirculateChecker {
    private final Class<?> rootClass;
    private Set<IncludePath> includePaths = new HashSet<>();
    public IncludeCirculateChecker(Class<?> rootClass){

        this.rootClass = rootClass;
    }

    public boolean includePathRepeat(IncludePath includePath) {
        return Objects.equals(rootClass,includePath.getTo()) || !includePaths.add(includePath);
    }
}
