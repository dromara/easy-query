package org.easy.query.core.abstraction;

/**
 * @FileName: EasyQuerySqlSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/12 16:23
 * @Created by xuejiaming
 */
public interface SqlSegment {
    default String toSql(){
        return getSql().toString();
    }
    StringBuilder getSql();
    void append(String sql);
    void append(SqlSegment sqlSegment);
    boolean isEmpty();
}
