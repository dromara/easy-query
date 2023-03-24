package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.segment.SqlSegment;

import java.util.List;

/**
 * @FileName: SqlSegment0Builder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 12:49
 * @author xuejiaming
 */
public interface SqlBuilderSegment extends SqlSegment {
    List<SqlSegment> getSqlSegments();
    void append(SqlSegment sqlSegment);
    boolean isEmpty();
   default boolean isNotEmpty(){
       return !isEmpty();
   }
    void copyTo(SqlBuilderSegment predicateSegment);
    SqlBuilderSegment cloneSqlBuilder();
   boolean containsOnce(Class<?> entityClass,String propertyName);
}
