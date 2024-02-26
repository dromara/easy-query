package com.easy.query.core.common;

import java.util.HashSet;
import java.util.Set;

/**
 * create time 2024/2/26 21:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeCirculateChecker {
    private Set<IncludePath> includePaths = new HashSet<>();

    public boolean includePathRepeat(IncludePath includePath) {
        return !includePaths.add(includePath);
    }
}
