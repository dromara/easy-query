package com.easy.query.search;

/**
 * 排序方式
 *
 * @author bkbits
 */
public enum EasySortType {
    None,
    Asc,
    Desc;


    /**
     * 从字符串创建EasySortType
     *
     * @param str 字符串，支持 "asc","desc","ascend","descend"
     * @return EasySortType
     */
    public static EasySortType of(String str) {
        if ("asc".equalsIgnoreCase(str) || "ascend".equalsIgnoreCase(str)) {
            return Asc;
        } else if ("desc".equalsIgnoreCase(str) || "descend".equalsIgnoreCase(str)) {
            return Desc;
        }
        return None;
    }
}
