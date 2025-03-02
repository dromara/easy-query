package com.easy.query.core.common;

import com.easy.query.core.util.EasyArrayUtil;
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
    private final int beforeStep;

    public DirectMappingIterator(String[] directMapping) {
        this(directMapping, 0);
    }

    /**
     * @param directMapping
     * @param beforeStep    提前多少步结束
     */
    public DirectMappingIterator(String[] directMapping, int beforeStep) {
        this.beforeStep = beforeStep;
        if (directMapping.length <= beforeStep) {
            throw new IllegalArgumentException("beforeStep must be less than the length of directMapping.");
        }
        this.propFullName = EasyStringUtil.EMPTY;
        this.directMapping = directMapping;
//        if (beforeStep > 0) {
//            this.directMapping = new String[directMapping.length - beforeStep];
//            System.arraycopy(directMapping, 0, this.directMapping, 0, directMapping.length - beforeStep);
//
//        } else {
//            this.directMapping = directMapping;
//        }
    }

    public boolean hasNext() {
        return index < (directMapping.length - beforeStep);
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

    public String tryGetNextIgnoreBeforeStep() {
        if (index < directMapping.length) {
            return directMapping[index];
        }
        return null;
    }

    public String tryGetNext() {
        if (hasNext()) {
            return directMapping[index + 1];
        }
        return null;
    }

    public String getProperty() {
        return directMapping[index];
    }

    public String getFullName() {
        return this.propFullName;
    }

}
