package com.easy.query.core.common;

import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2025/2/26 23:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DirectMappingIterator {
    private final String[] directMapping;
    private int index = 0;
    private String propFullName;

    public DirectMappingIterator(String[] directMapping) {
        this.directMapping = directMapping;
        this.propFullName = EasyStringUtil.EMPTY;
    }

    public boolean hasNext() {
        return index < directMapping.length;
    }

    public String next() {
        boolean first = index == 0;
        String next = directMapping[index++];
        if (first) {
            this.propFullName = next;
        } else {
            this.propFullName += "." + next;
        }
        return next;
    }

    public String getProperty() {
        return directMapping[index];
    }

    public String getFullName() {
        return this.propFullName;
    }

}
