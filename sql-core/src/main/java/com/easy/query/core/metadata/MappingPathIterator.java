package com.easy.query.core.metadata;

/**
 * create time 2024/5/14 10:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class MappingPathIterator {
    private final String[] mappingPath;
    private int index;

    public MappingPathIterator(String[] mappingPath){

        this.mappingPath = mappingPath;
        this.index=0;
    }
    public boolean hasNext(){
        return index<mappingPath.length;
    }
    public String next(){
        return mappingPath[index++];
    }
    public boolean hasOneNext(){
        return index==mappingPath.length-1;
    }
}
