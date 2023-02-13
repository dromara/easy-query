package org.easy.query.core.abstraction;

import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: ColumnSelectIgnore.java
 * @Description: 文件说明
 * @Date: 2023/2/13 13:51
 * @Created by xuejiaming
 */
public class ColumnSelectIgnore {
    public Set<String> getColumns() {
        return columns;
    }

    public int getIndex() {
        return index;
    }

    private final Set<String> columns;
    private final int index;

    public ColumnSelectIgnore(int index){
        this.columns = new HashSet<>();

        this.index = index;
    }

}
