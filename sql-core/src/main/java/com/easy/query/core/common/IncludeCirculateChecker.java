package com.easy.query.core.common;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2024/2/26 21:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeCirculateChecker {
    private Map<IncludePath,IncludePath> includePaths = new HashMap<>();
    public IncludeCirculateChecker(){

    }

    public boolean includePathRepeat(IncludePath includePath) {
        IncludePath oldIncludePath = includePaths.get(includePath);
        if(oldIncludePath==null){
            includePaths.put(includePath,includePath);
            return false;
        }
        if(oldIncludePath.getDeep()<includePath.getDeep()){
            return true;
        }
        oldIncludePath.setDeep(includePath.getDeep());
        return false;
    }
}
